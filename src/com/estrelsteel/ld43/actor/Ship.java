package com.estrelsteel.ld43.actor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.estrelsteel.engine2.actor.UndrawnActor;
import com.estrelsteel.engine2.point.AbstractedPoint;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.world.FrozenWorld;
import com.estrelsteel.ld43.world.SpaceWorld;

public class Ship extends UndrawnActor {

	private double fuel;
	private double fuelCap;
	private boolean visible;
	private static final double DISTANCE_SCALE = 100;
	private static final Color outline = Color.GREEN;
	
	
	public int life = 20;
	public int navigator = 9;
	public int fueltank = 25;
	public int fueltransfer = 1;
	public int landing = 25;
	public int comms = 10;
	public int docker = 10;
	
	public Ship(String name) {
		super(name, QuickRectangle.location(0, 0, 0, 0));
		this.fuel = 150;
		this.fuelCap = 500;
		this.visible = false;
	}
	
	public double getMass() {
		return life + navigator + fueltank + fueltransfer + landing + comms + docker;
	}
	
	public double getFuelPercentage() {
		return fuel / fuelCap;
	}
	
	public double getFuel() {
		return fuel;
	}
	
	public double getFuelCap() {
		return fuelCap;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public double calcTravelRadius() {
		return fuel / getMass() * DISTANCE_SCALE;
	}
	
	public Ship setFuel(double fuel) {
		this.fuel = fuel;
		return this;
	}
	
	public Ship setFuelCap(double fuelCap) {
		this.fuelCap = fuelCap;
		return this;
	}
	
	public Ship setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}
	
	public String getShipTravelData(ArrayList<NavIcon> navIcons, AbstractedPoint p) {
		String msg = "";
		msg = msg + "Mass: " + getMass() + "Mg\n";
		msg = msg + "Fuel: " + (fuel/fuelCap * 100) + "%\n\n";
		msg = msg + "Maximum Distance: " + calcTravelRadius() + "un";
		if(navIcons != null) {
			msg = msg + "\n###\n";
			for(int i = 0; i < navIcons.size(); i++) {
				msg = msg + "[" + (i + 1) + "] " + navIcons.get(i).getName() + " (" + navIcons.get(i).updateDistance(p) + "un)\n";
			}
		}
		return msg;
	}
	
	public String getShipCargo() {
		String msg = "Ship Data:\nPress a number key to eject parts of the ship:\n\n";
		if(life != 0) msg = msg + "[1] Life Support: " + life + "Mg\n";
		if(navigator != 0) msg = msg + "[2] Navigator: " + navigator + "Mg\n";
		if(fueltank != 0) msg = msg + "[3] Fuel/Fuel Tank: " + fueltank + "Mg\n";
		if(fueltransfer != 0) msg = msg + "[4] Fuel Transferer: " + fueltransfer + "Mg\n";
		if(landing != 0) msg = msg + "[5] Landing Systems: " + landing + "Mg\n";
		if(comms != 0) msg = msg + "[6] Communication Systems: " + comms + "Mg\n";
		if(docker != 0) msg = msg + "[7] Docker: " + docker + "Mg\n";
		return msg;
	}

	@Override
	public Graphics2D render(Graphics2D ctx, FrozenWorld world) {
//		int dx = 0;
//		int dy = 0;
//		if(world instanceof World) {
//			dx = (int) world.getGrid().moveToGrid(((World) world).getMainCamera().getLocation()).getX();
//			dy = (int) world.getGrid().moveToGrid(((World) world).getMainCamera().getLocation()).getY();
//		}
		int radius = (int) calcTravelRadius();
		ctx.setColor(outline);
		ctx.setStroke(new BasicStroke(5));
		ctx.drawArc(320 - radius, 320 - radius, 2 * radius, 2 * radius, 0, 360);
		return ctx;
	}

	@Override
	public Graphics2D simpleRender(Graphics2D ctx, FrozenWorld world) {
		return render(ctx, world);
	}
}
