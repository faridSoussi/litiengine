package de.gurkenlabs.litiengine.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IGameLoop;
import de.gurkenlabs.litiengine.IUpdateable;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.Event;

public class Gamepad implements IGamepad, IUpdateable {
  private static final Map<String, Identifier> components = new HashMap<>();

  private final Map<String, List<Consumer<Float>>> pollConsumer;
  private final Map<String, List<Consumer<Float>>> pressedConsumer;
  private final int index;
  private final Controller controller;

  protected Gamepad(final int index, Controller controller) {
    this.pollConsumer = new ConcurrentHashMap<>();
    this.pressedConsumer = new ConcurrentHashMap<>();
    this.index = index;
    this.controller = controller;
    Input.INPUT_LOOP.registerForUpdate(this);
  }

  public int getIndex() {
    return this.index;
  }

  public String getName() {
    return this.controller.getName();
  }

  @Override
  public void update(IGameLoop loop) {
    boolean couldPoll = this.controller.poll();
    if (!couldPoll) {
      this.dispose();
    }

    Event event = new Event();
    while (this.controller.getEventQueue().getNextEvent(event)) {
      List<Consumer<Float>> consumers = this.pollConsumer.get(event.getComponent().getIdentifier().getName());
      if (consumers != null) {
        for (Consumer<Float> cons : consumers) {
          cons.accept(event.getValue());
        }
      }
    }

    for (String id : this.pressedConsumer.keySet()) {
      Identifier ident = get(id);
      if (ident != null) {
        Component comp = controller.getComponent(ident);
        if (comp != null && comp.getPollData() != 0) {
          for (Consumer<Float> cons : this.pressedConsumer.get(id)) {
            cons.accept(comp.getPollData());
          }
        }
      }
    }
  }

  @Override
  public float getPollData(Identifier identifier) {
    Component comp = this.controller.getComponent(identifier);
    if (comp == null) {
      return 0;
    }

    return comp.getPollData();
  }

  private void dispose() {
    Input.INPUT_LOOP.unregisterFromUpdate(this);
    this.pollConsumer.clear();
    this.pressedConsumer.clear();
    Input.GAMEPADMANAGER.remove(this);
  }

  @Override
  public void onPoll(String identifier, Consumer<Float> consumer) {
    if (!this.pollConsumer.containsKey(identifier)) {
      this.pollConsumer.put(identifier, new ArrayList<>());
    }

    this.pollConsumer.get(identifier).add(consumer);
  }

  @Override
  public void onPressed(String identifier, Consumer<Float> consumer) {
    if (!this.pressedConsumer.containsKey(identifier)) {
      this.pressedConsumer.put(identifier, new ArrayList<>());
    }

    this.pressedConsumer.get(identifier).add(consumer);
  }

  private static final Identifier get(String name) {
    return components.get(name);
  }

  private static final String addComponent(Identifier identifier) {
    components.put(identifier.getName(), identifier);
    return identifier.getName();
  }

  public static class Xbox {
    public static final String A = Buttons._0;
    public static final String B = Buttons._1;
    public static final String X = Buttons._2;
    public static final String Y = Buttons._3;
    public static final String RB = Buttons._5;
    public static final String LB = Buttons._4;
    // range -1 - 0
    public static final String RT = Axis.Z;

    // range 0 - 1
    public static final String LT = Axis.Z;
    public static final String START = Buttons._7;
    public static final String SELECT = Buttons._6;
    public static final String LEFT_STICK_X = Axis.X;
    public static final String LEFT_STICK_Y = Axis.Y;
    public static final String LEFT_STICK_PRESS = Buttons._8;
    public static final String RIGHT_STICK_X = Axis.RX;
    public static final String RIGHT_STICK_Y = Axis.RY;
    public static final String RIGHT_STICK_PRESS = Buttons._9;

    public static final String DPAD = Axis.POV;
  }

  public static class DPad {
    /**
     * Standard value for center HAT position
     */
    public static final float OFF = 0.0f;
    /**
     * Synonmous with OFF
     */
    public static final float CENTER = OFF;
    /**
     * Standard value for up-left HAT position
     */
    public static final float UP_LEFT = 0.125f;
    /**
     * Standard value for up HAT position
     */
    public static final float UP = 0.25f;
    /**
     * Standard value for up-right HAT position
     */
    public static final float UP_RIGHT = 0.375f;
    /**
     * Standard value for right HAT position
     */
    public static final float RIGHT = 0.50f;
    /**
     * Standard value for down-right HAT position
     */
    public static final float DOWN_RIGHT = 0.625f;
    /**
     * Standard value for down HAT position
     */
    public static final float DOWN = 0.75f;
    /**
     * Standard value for down-left HAT position
     */
    public static final float DOWN_LEFT = 0.875f;
    /**
     * Standard value for left HAT position
     */
    public static final float LEFT = 1.0f;
  }

  public static class Buttons {
    public static final String _0 = addComponent(Identifier.Button._0);
    public static final String _1 = addComponent(Identifier.Button._1);
    public static final String _2 = addComponent(Identifier.Button._2);
    public static final String _3 = addComponent(Identifier.Button._3);
    public static final String _4 = addComponent(Identifier.Button._4);
    public static final String _5 = addComponent(Identifier.Button._5);
    public static final String _6 = addComponent(Identifier.Button._6);
    public static final String _7 = addComponent(Identifier.Button._7);
    public static final String _8 = addComponent(Identifier.Button._8);
    public static final String _9 = addComponent(Identifier.Button._9);
    public static final String _10 = addComponent(Identifier.Button._10);
    public static final String _11 = addComponent(Identifier.Button._11);
    public static final String _12 = addComponent(Identifier.Button._12);
    public static final String _13 = addComponent(Identifier.Button._13);
    public static final String _14 = addComponent(Identifier.Button._14);
    public static final String _15 = addComponent(Identifier.Button._15);
    public static final String _16 = addComponent(Identifier.Button._16);
    public static final String _17 = addComponent(Identifier.Button._17);
    public static final String _18 = addComponent(Identifier.Button._18);
    public static final String _19 = addComponent(Identifier.Button._19);
    public static final String _20 = addComponent(Identifier.Button._20);
    public static final String _21 = addComponent(Identifier.Button._21);
    public static final String _22 = addComponent(Identifier.Button._22);
    public static final String _23 = addComponent(Identifier.Button._23);
    public static final String _24 = addComponent(Identifier.Button._24);
    public static final String _25 = addComponent(Identifier.Button._25);
    public static final String _26 = addComponent(Identifier.Button._26);
    public static final String _27 = addComponent(Identifier.Button._27);
    public static final String _28 = addComponent(Identifier.Button._28);
    public static final String _29 = addComponent(Identifier.Button._29);
    public static final String _30 = addComponent(Identifier.Button._30);
    public static final String _31 = addComponent(Identifier.Button._31);

    // gamepad buttons (not for xbox :( )
    public static final String A = addComponent(Identifier.Button.A);
    public static final String B = addComponent(Identifier.Button.B);
    public static final String C = addComponent(Identifier.Button.C);
    public static final String X = addComponent(Identifier.Button.X);
    public static final String Y = addComponent(Identifier.Button.Y);
    public static final String Z = addComponent(Identifier.Button.Z);
    public static final String START = addComponent(Identifier.Button.START);
    public static final String SELECT = addComponent(Identifier.Button.SELECT);
    public static final String MODE = addComponent(Identifier.Button.MODE);
    public static final String LEFT_THUMB = addComponent(Identifier.Button.LEFT_THUMB);
    public static final String LEFT_THUMB2 = addComponent(Identifier.Button.LEFT_THUMB2);
    public static final String LEFT_THUMB3 = addComponent(Identifier.Button.LEFT_THUMB3);
    public static final String RIGHT_THUMB = addComponent(Identifier.Button.RIGHT_THUMB);
    public static final String RIGHT_THUMB2 = addComponent(Identifier.Button.RIGHT_THUMB2);
    public static final String RIGHT_THUMB3 = addComponent(Identifier.Button.RIGHT_THUMB3);

    // joystick buttons
    public static final String TRIGGER = addComponent(Identifier.Button.TRIGGER);
    public static final String THUMB = addComponent(Identifier.Button.THUMB);
    public static final String THUMB2 = addComponent(Identifier.Button.THUMB2);
    public static final String TOP = addComponent(Identifier.Button.TOP);
    public static final String TOP2 = addComponent(Identifier.Button.TOP2);
    public static final String PINKIE = addComponent(Identifier.Button.PINKIE);
    public static final String BASE = addComponent(Identifier.Button.BASE);
    public static final String BASE2 = addComponent(Identifier.Button.BASE2);
    public static final String BASE3 = addComponent(Identifier.Button.BASE3);
    public static final String BASE4 = addComponent(Identifier.Button.BASE4);
    public static final String BASE5 = addComponent(Identifier.Button.BASE5);
    public static final String BASE6 = addComponent(Identifier.Button.BASE6);

    // TODO: incomplete list... right now we don't support stylus / mouse or
    // extra buttons
  }

  public static class Axis {
    public static final String X = addComponent(Identifier.Axis.X);
    public static final String X_ACCELERATION = addComponent(Identifier.Axis.X_ACCELERATION);
    public static final String X_FORCE = addComponent(Identifier.Axis.X_FORCE);
    public static final String X_VELOCITY = addComponent(Identifier.Axis.X_VELOCITY);
    public static final String Y = addComponent(Identifier.Axis.Y);
    public static final String Y_ACCELERATION = addComponent(Identifier.Axis.Y_ACCELERATION);
    public static final String Y_FORCE = addComponent(Identifier.Axis.Y_FORCE);
    public static final String Y_VELOCITY = addComponent(Identifier.Axis.Y_VELOCITY);
    public static final String Z = addComponent(Identifier.Axis.Z);
    public static final String Z_ACCELERATION = addComponent(Identifier.Axis.Z_ACCELERATION);
    public static final String Z_FORCE = addComponent(Identifier.Axis.Z_FORCE);
    public static final String Z_VELOCITY = addComponent(Identifier.Axis.Z_VELOCITY);
    public static final String RX = addComponent(Identifier.Axis.RX);
    public static final String RX_ACCELERATION = addComponent(Identifier.Axis.RX_ACCELERATION);
    public static final String RX_FORCE = addComponent(Identifier.Axis.RX_FORCE);
    public static final String RX_VELOCITY = addComponent(Identifier.Axis.RX_VELOCITY);
    public static final String RY = addComponent(Identifier.Axis.RY);
    public static final String RY_ACCELERATION = addComponent(Identifier.Axis.RY_ACCELERATION);
    public static final String RY_FORCE = addComponent(Identifier.Axis.RY_FORCE);
    public static final String RY_VELOCITY = addComponent(Identifier.Axis.RY_VELOCITY);
    public static final String RZ = addComponent(Identifier.Axis.RZ);
    public static final String RZ_ACCELERATION = addComponent(Identifier.Axis.RZ_ACCELERATION);
    public static final String RZ_FORCE = addComponent(Identifier.Axis.RZ_FORCE);
    public static final String RZ_VELOCITY = addComponent(Identifier.Axis.RZ_VELOCITY);
    public static final String SLIDER = addComponent(Identifier.Axis.SLIDER);
    public static final String SLIDER_ACCELERATION = addComponent(Identifier.Axis.SLIDER_ACCELERATION);
    public static final String SLIDER_FORCE = addComponent(Identifier.Axis.SLIDER_FORCE);
    public static final String SLIDER_VELOCITY = addComponent(Identifier.Axis.SLIDER_VELOCITY);
    public static final String POV = addComponent(Identifier.Axis.POV);
  }
}