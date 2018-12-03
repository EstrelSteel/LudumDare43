package com.estrelsteel.ld43.world;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.chunk.ImageChunk;
import com.estrelsteel.engine2.grid.PixelGrid;
import com.estrelsteel.engine2.image.Image;
import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.world.World;
import com.estrelsteel.ld43.actor.NavIcon;
import com.estrelsteel.ld43.actor.Player;
import com.estrelsteel.ld43.actor.Ship;
import com.estrelsteel.ld43.actor.TriggerArea;

public class SpaceWorld extends World {

	private Image background;
	private ArrayList<ImageChunk> tilechunks;
	private ArrayList<TriggerArea> triggers;
	private ArrayList<NavIcon> navIcons;
	private HashMap<Integer, Player> players;
	private int levelNum;
	private int last;
	private int warp;
	private boolean movable;
	private Ship ship;
	
	public SpaceWorld(int levelNum, Ship ship) {
		super(new PixelGrid());
		background = (Image) Engine2.GLOBAL_RESOURCE_REFERENCE.getResources().get(1);
		background.loadImage();
		tilechunks = new ArrayList<ImageChunk>();
		triggers = new ArrayList<TriggerArea>();
		navIcons = new ArrayList<NavIcon>();
		players = new HashMap<Integer, Player>();
		this.levelNum = levelNum;
		this.warp = -1;
		this.last = -1;
		this.movable = true;
		this.ship = ship;
	}

	public int getLevelNumber() {
		return levelNum;
	}
	
	public int getLevelWarp() {
		return warp;
	}
	
	public int getLevelLast() {
		return last;
	}
	
	public Ship getShip() {
		return ship;
	}
	
	public boolean isMovable() {
		return movable;
	}
	
	public SpaceWorld setLevelWarp(int warp) {
		this.warp = warp;
		return this;
	}
	
	public SpaceWorld setLevelLast(int last) {
		this.last = last;
		return this;
	}
	
	public SpaceWorld setMovable(boolean movable) {
		this.movable = movable;
		return this;
	}
	
	public ArrayList<ImageChunk> getTileChunks() {
		return tilechunks;
	}
	
	public ArrayList<TriggerArea> getTriggerAreas() {
		return triggers;
	}
	
	public ArrayList<NavIcon> getNavIcons() {
		return navIcons;
	}
	
	public ArrayList<Renderable> getTriggerAreasAsRenderable() {
		ArrayList<Renderable> r = new ArrayList<Renderable>();
		r.addAll(triggers);
		return r;
	}
	
	public SpaceWorld setTileChunks(ArrayList<ImageChunk> tilechunks) {
		this.tilechunks = tilechunks;
		return this;
	}
	
	public SpaceWorld addPlayer(Player p) {
		players.put(p.getID(), p);
		getObjects().add(p);
		return this;
	}
	
	public Player getPlayer(int id) {
		return players.get(id);
	}
	
	@Override
	public Graphics2D renderWorld(Graphics2D ctx) {
		ctx.drawImage(background.getImage(), 0, 0, 640, 640, null);
				
		for(int i = 0; i < tilechunks.size(); i++) {
			tilechunks.get(i).render(ctx, this);
		}
		super.renderWorld(ctx);
		for(int i = 0; i < navIcons.size(); i++) {
			navIcons.get(i).render(ctx, this);
		}
		if(ship.isVisible()) ship.render(ctx, this);
		if(TriggerArea.DEBUG) {
			for(int i = 0; i < triggers.size(); i++) {
				triggers.get(i).render(ctx, this);
			}
		}
		return ctx;
	}
	
	@Override
	public Graphics2D simpleRenderWorld(Graphics2D ctx) {
		return renderWorld(ctx);
	}

}
