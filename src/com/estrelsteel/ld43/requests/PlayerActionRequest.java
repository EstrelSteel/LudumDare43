package com.estrelsteel.ld43.requests;

import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.shape.collide.CollideArea;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.ld43.TextWindow;
import com.estrelsteel.ld43.actor.Controller;
import com.estrelsteel.ld43.actor.Monitor;
import com.estrelsteel.ld43.actor.Player;
import com.estrelsteel.ld43.world.SpaceWorld;

public class PlayerActionRequest implements Request {

	private int pid;
	private int action;
	
	public PlayerActionRequest(int pid, int action) {
		this.pid = pid;
		this.action = action;
	}
	
	@Override
	public int getID() {
		return 102;
	}

	@Override
	public void action(SpaceWorld w, TextWindow text) {
		Player p = w.getPlayer(pid);
		switch(action) {
		case 0:
			CollideArea c = new RectangleCollideArea(p.getLocation());
			Renderable r = w.checkCollide(c, p);
			if(r != null) {
				if(r instanceof Controller) {
					w.setLevelWarp(((Controller) r).getLevelWarp());
					if(!((Controller) r).hasRead()) {
						text.clearContent().loadContent(((Controller) r).getPath()).updateContent();
						((Controller) r).setRead(true);
					}
				}
				else if(r instanceof Monitor) {
					text.clearContent().addContent(w.getShip().getShipCargo()).updateContent();
				}
			}
			break;
		case 1:
			if(w.getLevelLast() >= 0) w.setLevelWarp(w.getLevelLast());
			break;
		}
		
	}

}
