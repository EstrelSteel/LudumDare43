package com.estrelsteel.ld43.actor;

import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.shape.collide.Collision;
import com.estrelsteel.engine2.shape.collide.PerspectiveRectangleArea;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Player extends Actor {

	private int id;
	public static int walkspeed;
	
	static {
		walkspeed = 5;
	}
	
	public Player(int id, Rectangle loc) {
		super("Player" + id, loc);
		
		this.id = id;
		setCollision(new Collision(true, new PerspectiveRectangleArea(loc)));
	
		getAnimations().add(new Animation().load("ANI 0 PLAYER_FRONT 30 true 2000 2001"));
		getAnimations().add(new Animation().load("ANI 1 PLAYER_BACK 30 true 2010 2011"));
		getAnimations().add(new Animation().load("ANI 2 PLAYER_RIGHT 30 true 2020 2021"));
		getAnimations().add(new Animation().load("ANI 3 PLAYER_LEFT 30 true 2030 2031"));
		getAnimations().add(new Animation().load("ANI 4 SHIP_RIGHT -1 true 2200"));
		setSortable(true);
	}
	
	public int getID() {
		return id;
	}

}
