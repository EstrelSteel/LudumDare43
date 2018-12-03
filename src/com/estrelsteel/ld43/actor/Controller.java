package com.estrelsteel.ld43.actor;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.shape.collide.Collision;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Controller extends Actor {

	private int warp;
	private String path;
	private boolean read;
	
	public Controller(Rectangle loc, int warp, String path) {
		super("Controller", loc);
		
		getAnimations().add(new Animation().load("ANI 0 CONTROLLER -1 false 2100"));
		Rectangle collideLoc = QuickRectangle.location(loc.getX(), loc.getY(), loc.getWidth(), loc.getHeight() - 40);
		setCollision(new Collision(true, new RectangleCollideArea(collideLoc)));
		setSortable(true);
		
		this.warp = warp;
		this.path = path;
		this.read = false;
	}
	
	public boolean hasRead() {
		return read;
	}
	
	public Controller setRead(boolean read) {
		this.read = read;
		return this;
	}
	
	public String getPath() {
		return Engine2.devPath + path;
	}
	
	public int getLevelWarp() {
		return warp;
	}
}
