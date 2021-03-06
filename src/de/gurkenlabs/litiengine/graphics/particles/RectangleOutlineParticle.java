package de.gurkenlabs.litiengine.graphics.particles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class RectangleOutlineParticle extends Particle {
  private float stroke = 0.5f;

  public RectangleOutlineParticle(final float width, final float height, final Color color, final int ttl) {
    super(width, height, color, ttl);
  }

  public float getStroke() {
    return this.stroke;
  }

  @Override
  public void render(final Graphics2D g, final Point2D emitterOrigin) {
    final Point2D renderLocation = this.getRenderLocation(emitterOrigin);
    final Stroke oldStroke = g.getStroke();
    g.setColor(this.getColor());
    g.setStroke(new BasicStroke(this.stroke));
    g.draw(new Rectangle2D.Double(renderLocation.getX(), renderLocation.getY(), this.getWidth(), this.getHeight()));
    g.setStroke(oldStroke);
  }

  public void setStroke(final float stroke) {
    this.stroke = stroke;
  }
}
