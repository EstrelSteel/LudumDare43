package com.estrelsteel.ld43.actor;

import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.shape.collide.Collision;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Monitor extends Actor {
	
	public Monitor(Rectangle loc) {
		super("Monitor", loc);
		
		getAnimations().add(new Animation().load("ANI 0 MONITOR -1 false 2100"));
		Rectangle collideLoc = QuickRectangle.location(loc.getX(), loc.getY(), loc.getWidth(), loc.getHeight() - 40);
		setCollision(new Collision(true, new RectangleCollideArea(collideLoc)));
		setSortable(true);
	}
}
