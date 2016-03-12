package de.gurkenlabs.litiengine.entities.ai;

import de.gurkenlabs.litiengine.entities.Entity;
import de.gurkenlabs.util.states.IState;
import de.gurkenlabs.util.states.Transition;

public abstract class EntityTransition<T extends Entity> extends Transition {
  private final T entity;

  protected EntityTransition(final T entity, int priority) {
    super(priority);
    this.entity = entity;
  }

  public T getEntity() {
    return this.entity;
  }
}
