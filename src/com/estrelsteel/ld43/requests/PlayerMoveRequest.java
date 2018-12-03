package com.estrelsteel.ld43.requests;

import java.util.ArrayList;

import com.estrelsteel.engine2.actor.UndrawnActor;
import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.shape.collide.Collision;
import com.estrelsteel.engine2.shape.collide.PerspectiveRectangleArea;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.ld43.TextWindow;
import com.estrelsteel.ld43.actor.Player;
import com.estrelsteel.ld43.world.SpaceWorld;

public class PlayerMoveRequest implements Request {

	private int pid;
	private int x;
	private int y;
	private boolean override;
	
	public PlayerMoveRequest(int pid, int x, int y, boolean override) {
		this.pid = pid;
		this.x = x;
		this.y = y;
		this.override = override;
	}
	
	@Override
	public int getID() {
		return 100;
	}

	@SuppressWarnings("static-access")
	@Override
	public void action(SpaceWorld w, TextWindow text) {
		if(!w.isMovable() || override) return;
		Player p = w.getPlayer(pid);
		Rectangle nloc = QuickRectangle.location(x + p.getLocation().getX(), y +  p.getLocation().getY(),  p.getLocation().getWidth(),  p.getLocation().getHeight());
		ArrayList<Renderable> temp = new ArrayList<Renderable>();
		temp.addAll(w.getTileChunks());
		temp.addAll(w.getObjects());
		PerspectiveRectangleArea a = new PerspectiveRectangleArea(nloc);
		Renderable r = w.checkCollide(temp, a, p);
		if(r != null) {
			Rectangle cloc = r.getLocation();
			if(r instanceof UndrawnActor){
				if(((UndrawnActor) r).getCollision().getCollideArea() instanceof RectangleCollideArea) {
					cloc = ((RectangleCollideArea) ((UndrawnActor) r).getCollision().getCollideArea()).getRectangle();
				}
			}
			
			//Snapping to edge
			if(x < 0) {
				QuickRectangle.translate(cloc.getX() + cloc.getWidth() - nloc.getX(), 0, nloc);
				p.setLocation(nloc);
				p.setCollision(new Collision(true, a));
			}
			else if(x > 0) {
				QuickRectangle.translate(cloc.getX() - (nloc.getX() + nloc.getWidth()), 0, nloc);
				p.setLocation(nloc);
				p.setCollision(new Collision(true, a));
			}
			if(y < 0) {
				QuickRectangle.translate(0, cloc.getY() + cloc.getHeight() - (nloc.getY() + nloc.getHeight() * a.getRatio()), nloc);
				p.setLocation(nloc);
				p.setCollision(new Collision(true, a));
			}
			else if(y > 0) {
				QuickRectangle.translate(0, cloc.getY() - (nloc.getY() + nloc.getHeight()), nloc);
				p.setLocation(nloc);
				p.setCollision(new Collision(true, a));
			}
			
		}
		else {
			p.setLocation(nloc);
		}
	}

}
