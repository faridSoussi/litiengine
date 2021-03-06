package de.gurkenlabs.litiengine.entities;

import java.awt.geom.Point2D;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.GameLoop;
import de.gurkenlabs.litiengine.annotation.MovementInfo;
import de.gurkenlabs.litiengine.physics.IMovementController;
import de.gurkenlabs.litiengine.util.geom.GeometricUtilities;

@MovementInfo
public class MobileEntity extends CollisionEntity implements IMobileEntity {
  private int acceleration;
  private int deceleration;
  private Point2D moveDestination;
  private boolean turnOnMove;
  private short velocity;

  public MobileEntity() {
    final MovementInfo info = this.getClass().getAnnotation(MovementInfo.class);
    this.velocity = info.velocity();
    this.acceleration = info.acceleration();
    this.deceleration = info.deceleration();
    this.setTurnOnMove(info.turnOnMove());
  }

  @Override
  public int getAcceleration() {
    return this.acceleration;
  }

  @Override
  public int getDeceleration() {
    return this.deceleration;
  }

  @Override
  public Point2D getMoveDestination() {
    return this.moveDestination;
  }

  @Override
  public float getTickVelocity() {
    return getTickVelocity(this);
  }

  @Override
  public float getVelocity() {
    return this.velocity;
  }

  @Override
  public IMovementController getMovementController() {
    return this.getController(IMovementController.class);
  }

  @Override
  public void setAcceleration(final int acceleration) {
    this.acceleration = acceleration;
  }

  @Override
  public void setDeceleration(final int deceleration) {
    this.deceleration = deceleration;
  }

  @Override
  public void setLocation(final Point2D position) {
    if (position == null || GeometricUtilities.equals(position, this.getLocation(), 0.001)) {
      return;
    }

    super.setLocation(position);
  }

  @Override
  public void setMoveDestination(final Point2D dest) {
    this.moveDestination = dest;
  }

  @Override
  public void setTurnOnMove(final boolean turn) {
    this.turnOnMove = turn;
  }

  @Override
  public void setVelocity(final short velocity) {
    this.velocity = velocity;
  }

  @Override
  public boolean turnOnMove() {
    return this.turnOnMove;
  }

  protected static float getTickVelocity(IMobileEntity entity) {
    // pixels per ms multiplied by the passed ms
    // ensure that entities don't travel too far in case of lag
    return Math.min(Game.getLoop().getDeltaTime(), GameLoop.TICK_DELTATIME_LAG) * 0.001F * entity.getVelocity() * Game.getLoop().getTimeScale();
  }
}
