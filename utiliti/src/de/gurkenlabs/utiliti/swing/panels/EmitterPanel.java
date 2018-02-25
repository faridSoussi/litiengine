package de.gurkenlabs.utiliti.swing.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.Resources;
import de.gurkenlabs.litiengine.environment.tilemap.IMapObject;
import de.gurkenlabs.litiengine.graphics.particles.xml.CustomEmitter;
import de.gurkenlabs.litiengine.graphics.particles.xml.EmitterData;
import de.gurkenlabs.utiliti.EditorScreen;
import de.gurkenlabs.utiliti.Icons;

@SuppressWarnings("serial")
public class EmitterPanel extends PropertyPanel<IMapObject> {
  private JToggleButton btnPause;
  private CustomEmitter emitter;

  public EmitterPanel() {
    TitledBorder border = new TitledBorder(new LineBorder(new Color(128, 128, 128)), Resources.get("panel_emitter"), TitledBorder.LEADING, TitledBorder.TOP, null, null);
    border.setTitleFont(border.getTitleFont().deriveFont(Font.BOLD));
    setBorder(border);

    Box horizontalBox = Box.createHorizontalBox();

    JButton btnCustomize = new JButton("Customize");
    btnCustomize.setIcon(new ImageIcon(Resources.getImage("pencil.png")));
    btnCustomize.addActionListener(a -> {
      EmitterPropertyPanel panel = new EmitterPropertyPanel();
      panel.bind(this.getDataSource());

      int option = JOptionPane.showConfirmDialog(Game.getScreenManager().getRenderComponent(), panel, Resources.get("panel_emitterProperties"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
      if (option == JOptionPane.CANCEL_OPTION) {
        panel.discardChanges();
      }
    });

    JButton btnSave = new JButton("Define Asset");
    btnSave.addActionListener(e -> {
      if (emitter == null) {
        return;
      }

      Object name = JOptionPane.showInputDialog(Game.getScreenManager().getRenderComponent(), Resources.get("input_prompt_name"), Resources.get("input_prompt_name_title"), JOptionPane.PLAIN_MESSAGE, null, null, emitter.getName());
      if (name == null) {
        return;
      }

      final EmitterData data = new EmitterData(emitter.getEmitterData());
      data.setName(name.toString());

      EditorScreen.instance().getGameFile().getEmitters().removeIf(x -> x.getName().equals(data.getName()));
      EditorScreen.instance().getGameFile().getEmitters().add(data);
    });

    btnSave.setIcon(new ImageIcon(Resources.getImage("emitter.png")));
    this.btnPause = new JToggleButton();
    this.btnPause.setSelected(true);
    this.btnPause.setPreferredSize(new Dimension(40, 23));
    this.btnPause.setIcon(Icons.PAUSE);
    GroupLayout groupLayout = new GroupLayout(this);
    groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(horizontalBox, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
        .addComponent(btnPause, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE).addGroup(groupLayout.createSequentialGroup().addComponent(btnCustomize).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSave))).addContainerGap()));
    groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(btnPause, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED)
            .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnCustomize).addComponent(btnSave)).addPreferredGap(ComponentPlacement.RELATED).addComponent(horizontalBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addContainerGap(205, Short.MAX_VALUE)));
    setLayout(groupLayout);

    this.setupChangedListeners();
  }

  private void setupChangedListeners() {
    this.btnPause.addActionListener(a -> {
      if (this.emitter != null) {
        this.emitter.togglePaused();
      }

      if (!btnPause.isSelected()) {
        this.btnPause.setIcon(Icons.PLAY);
      } else {
        this.btnPause.setIcon(Icons.PLAY);
      }
    });
  }

  @Override
  protected void clearControls() {
  }

  @Override
  protected void setControlValues(IMapObject mapObject) {
    this.emitter = (CustomEmitter) Game.getEnvironment().getEmitter(mapObject.getId());
    if (emitter == null) {
      this.btnPause.setSelected(false);
      return;
    }
    
    this.btnPause.setSelected(!emitter.isPaused());
  }

  @Override
  protected IMapObject getDataSource() {
    return super.getDataSource();
  }
}
