package com.estrelsteel.ld43.actor;

import java.awt.Graphics2D;

import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.shape.collide.Collision;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.world.FrozenWorld;
import com.estrelsteel.ld43.TextWindow;
import com.estrelsteel.ld43.world.SpaceWorld;

public abstract class TriggerArea extends Actor {

	public static final boolean DEBUG = true;
	private long consumed;
	
	public TriggerArea(String name, Rectangle loc) {
		super(name, loc);
		consumed = -1;
		getAnimations().add(new Animation().load("ANI 0 TRIGGER_AREA -1 false 10"));
		
		setCollision(new Collision(true, new RectangleCollideArea(loc)));
	}
	
	public abstract void action(SpaceWorld w, TextWindow text);
	public abstract boolean isConsumed();
	public abstract void updateCollision();

	public long getConsumed() {
		return consumed;
	}
	
	public void setConsumed(long consumed) {
		this.consumed = consumed;
	}
	
	@Override
	public Graphics2D render(Graphics2D ctx, FrozenWorld world) {
		return super.render(ctx, world);
	}

	@Override
	public Graphics2D simpleRender(Graphics2D ctx, FrozenWorld world) {
		return super.simpleRender(ctx, world);
	}

}
