package com.estrelsteel.ld43.actor;

import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.point.AbstractedPoint;
import com.estrelsteel.engine2.point.PointMaths;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class NavIcon extends Actor {

	private double distance;
	
	public NavIcon(String name, Rectangle loc) {
		super(name, loc);
		
		getAnimations().add(new Animation().load("ANI 0 STATION -1 false 2210"));
		
		distance = -1;
	}
	
	public double getDistanceDouble() {
		return distance;
	}
	
	public int getDistance() {
		return (int) distance;
	}
	
	public int updateDistance(AbstractedPoint p) {
		AbstractedPoint iconMid = new AbstractedPoint(getLocation().getX() + getLocation().getHeight() / 2, getLocation().getY() + getLocation().getHeight() / 2);
		
		distance = Math.abs(PointMaths.getDistanceTo(p, iconMid));
		
		return (int) distance;
	}

}
