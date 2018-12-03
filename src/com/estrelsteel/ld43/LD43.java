package com.estrelsteel.ld43;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JFrame;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.Launcher;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.events.listener.RenderListener;
import com.estrelsteel.engine2.events.listener.StartListener;
import com.estrelsteel.engine2.events.listener.StopListener;
import com.estrelsteel.engine2.events.listener.TickListener;
import com.estrelsteel.engine2.file.GameFile;
import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.point.PointMaths;
import com.estrelsteel.engine2.shape.collide.CollideArea;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.tile.TileType;
import com.estrelsteel.engine2.window.WindowSettings;
import com.estrelsteel.ld43.actor.Controller;
import com.estrelsteel.ld43.actor.Monitor;
import com.estrelsteel.ld43.actor.NavIcon;
import com.estrelsteel.ld43.actor.Player;
import com.estrelsteel.ld43.actor.Ship;
import com.estrelsteel.ld43.actor.TextTriggerArea;
import com.estrelsteel.ld43.actor.TriggerArea;
import com.estrelsteel.ld43.requests.Request;
import com.estrelsteel.ld43.world.LevelBuilder;
import com.estrelsteel.ld43.world.SpaceWorld;

public class LD43 implements StartListener, StopListener, TickListener, RenderListener {
	//	SPACE v1.0a
	//	For: Ludum Dare 43 Compo - Sacrifices must be made
	//	By: Estrel Steel
	
	private Launcher l;
	private SpaceWorld w;
	private Ship ship;
	private LevelBuilder lb;
	private HashMap<Integer, SpaceWorld> levels;
	private String gameOver;
	private double unitx = 0;
	private double unity = 0;
	private int loc = 0;
	
	private InputHandler in;
	public Queue<Request> requests;
	private TextWindow text;
	
	public static void main(String[] args) {
		new LD43();
	}
	
	@SuppressWarnings("static-access")
	public LD43() {
		l = new Launcher();
		Rectangle size;
		if(System.getProperty("os.name").startsWith("Windows")) {
			size = QuickRectangle.location(0, 0, 630, 630);
		}
		else {
			size = QuickRectangle.location(0, 0, 640, 640);
		}
		
		l.getEngine().setWindowSettings(new WindowSettings(size, "LD43 - EstrelSteel", "v1.0a", 0));
		
		in = new InputHandler(this);
		
		l.getEngine().START_EVENT.addListener(this);
		l.getEngine().STOP_EVENT.addListener(this);
		l.getEngine().RENDER_EVENT.addListener(this);
		l.getEngine().TICK_EVENT.addListener(this);
		l.getEngine().addKeyListener(in);
		l.getEngine().TICK_EVENT.addListener(in);
		
		l.getEngine().development = true;
//		l.getEngine().devPath = System.getProperty("user.home") + "/Documents/usb/LD43/LD43";
		l.getEngine().devPath = GameFile.getCurrentPath();
		
		
		JFrame frame = l.boot();
		text = new TextWindow(frame);
		text.clearContent().loadContent(Engine2.devPath + "/res/msg/tutor0.txt").updateContent();
	}
	
	public void init() {
		GameFile res = new GameFile(Engine2.devPath + "/res/res.txt");
		try {
			res.updateLines();
			Engine2.GLOBAL_RESOURCE_REFERENCE.load(res, 0);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		TileType tt = new TileType(-1, "null");
		GameFile tiletypes = new GameFile(Engine2.devPath + "/res/tiles.txt");
		try {
			tiletypes.updateLines();
			tt.load(tiletypes, 0);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		lb = new LevelBuilder(0, 0);
		GameFile palette = new GameFile(Engine2.devPath + "/res/lvl/palette.txt");
		try {
			palette.updateLines();
			lb = lb.load(palette, 0);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		levels = new HashMap<Integer, SpaceWorld>();
		requests = new LinkedList<Request>();
	}

	public void loadLevel(int level) {
		int last = -1;
		if(w != null) {
			last = w.getLevelNumber();
			levels.put(w.getLevelNumber(), w.setLevelWarp(-1).setLevelLast(-1));
			w = levels.get(level);
		}
		boolean found = true;
		if(w == null) {
			found = false;
			w = new SpaceWorld(level, ship);
			w.setTileChunks(lb.generateChunkLevel(Engine2.GLOBAL_RESOURCE_REFERENCE.getImage(1000 + level * 10)));
		}
		w.setLevelLast(last);
		switch(level) {
		case 0:
			if(!found) {
				w.addPlayer(new Player(0, QuickRectangle.location(512, 480, 64, 96)));
				w.getObjects().add(0, new Monitor(QuickRectangle.location(16 * 64, 23 * 64, 128, 128)));
				w.getObjects().add(0, new Controller(QuickRectangle.location(33 * 64 + 32, 16 * 64, 128, 128), 1, "/res/msg/space0.txt"));
				w.getTriggerAreas().add(new TextTriggerArea("tutorial1", QuickRectangle.location(32 * 64, 15 * 64, 320, 320), "/res/msg/tutor1.txt"));
				w.getTriggerAreas().add(new TextTriggerArea("tutorial1", QuickRectangle.location(15 * 64, 22 * 64, 320, 320), "/res/msg/tutor1.txt"));
			}
			
			w.setLevelLast(-1);
			ship.setVisible(false);
			if(text != null) text.clearContent().updateContent();
			break;
		case 1:
			if(!found) {
				w.addPlayer((Player) new Player(0, QuickRectangle.location(0, 0, 32, 32)).setRunningAnimationNumber(4));
				w.setMovable(false);
				w.getNavIcons().add(new NavIcon("Station", QuickRectangle.location(100, 160, 192, 32)));
			}
			ship.setVisible(true);
			text.addLineToContent(ship.getShipTravelData(w.getNavIcons(), PointMaths.getMidpoint(w.getPlayer(0).getLocation().getTop(), w.getPlayer(0).getLocation().getBottom()))).updateContent();
			break;
		default:
			w.addPlayer(new Player(0, QuickRectangle.location(512, 480, 64, 96)));
			break;
		}
	}
	
	@Override
	public void start() {
		init();
		
		ship = new Ship("ship0");
		
		loadLevel(0);
	}

	@Override
	public Graphics2D render(Graphics2D ctx) {
		ctx = w.renderWorld(ctx);
//		if(in.loc) {
//			System.out.println(w.getPlayer(0).getLocation().getX() + ", " + w.getPlayer(0).getLocation().getY());
//		}
		
		return ctx;
	}

	@Override
	public void tick() {
		while(!requests.isEmpty()) {
			requests.poll().action(w, text);
		}
		for(int i = 0; i < w.getObjects().size(); i++) {
			if(w.getObjects().get(i) instanceof Actor) {
				((Actor) w.getObjects().get(i)).getRunningAnimation().run();
			}
		}
		Renderable r = SpaceWorld.checkCollide(w.getTriggerAreasAsRenderable(), 
				new RectangleCollideArea(w.getPlayer(0).getLocation()), null);
		if(r != null) ((TriggerArea) r).action(w, text);
		if(!w.isMovable() && w.getShip().getFuel() >= 0) {
			if(w.getPlayer(0).getLocation().getX() != w.getShip().getLocation().getX() || w.getPlayer(0).getLocation().getY() != w.getShip().getLocation().getY()) {
				if(unitx == 0 && unity == 0) {
					double d = PointMaths.getDistanceTo(w.getMainCamera().getLocation(), w.getShip().getLocation().getTop());
					unitx = (w.getShip().getLocation().getX() - w.getPlayer(0).getLocation().getX()) / (d / 2);
					unity = (w.getShip().getLocation().getY() - w.getPlayer(0).getLocation().getY()) / (d / 2);
				}
				QuickRectangle.translate(unitx,	unity, w.getPlayer(0).getLocation());
//				ship.setFuel(ship.getFuel() - 1);
				CollideArea c = new RectangleCollideArea(w.getPlayer(0).getLocation());
				if(c.checkCollision(w.getShip().getLocation())) {
					unitx = 0;
					unity = 0;
					loc = 1;
					w.getPlayer(0).setLocation(QuickRectangle.location(w.getShip().getLocation().getX(), w.getShip().getLocation().getY(), 32, 32));
				}
			}
			else {
				unitx = 0;
				unity = 0;
			}
		}
//		w.sortObjects();
		if(w.getLevelWarp() >= 0) {
			loadLevel(w.getLevelWarp());
		}
		
		if(ship.getFuel() <= 0) {
			gameOver = "You ran out of fuel...";
		}
		else if(ship.life == 0) {
			gameOver = "You ejected your life support...";
		}
		else if(ship.navigator == 0) {
			gameOver = "You ejected your navigation, meaing the ship cannot orient or travel.\nBecause of this you remain stranded and are not found...";
		}
		else if(ship.comms == 0 && loc == 1) {
			gameOver = "You arrive at the foreign ship, but without your communication system, you cannot express your need for help to the other ship. Soon they depart, leaving you stranded in space...";
		}
		else if(ship.docker == 0 && loc == 1) {
			gameOver = "You Survived! After arriving at the target ship and establishing communications you expressed your need for help. Because your docker was ejected, they called for another ship to tow you to safety which took time.\nBut nonetheless, you live another day...";
		}
		else if(ship.docker == 1 && loc == 1) {
			gameOver = "You Survived! After arriving at the target ship and establishing communications you expressed your need for help. The foreign ship allowed you to dock and enter their ship, allowing you to escape.";
		}
		if(gameOver != null) {
			if(gameOver.startsWith("You Survived!")) {
				text.clearContent().addContent("You Survived!\n\nSpace\nBy:EstrelSteel\nFor:Ludum Dare 43 Compo\n").updateContent();
			}
			text.clearContent().addContent("GAME OVER!!!\n\n\n\n" + gameOver +"\n\nRe-open the game to try again").updateContent();
		}
	}

	@Override
	public void stop() {
		text.exit();
	}
}
