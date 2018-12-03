package com.estrelsteel.ld43.requests;

import com.estrelsteel.engine2.grid.PixelGrid;
import com.estrelsteel.engine2.point.AbstractedPoint;
import com.estrelsteel.engine2.point.Point2;
import com.estrelsteel.engine2.point.PointMaths;
import com.estrelsteel.ld43.TextWindow;
import com.estrelsteel.ld43.actor.Player;
import com.estrelsteel.ld43.world.SpaceWorld;

public class UpdateCameraToPlayerRequest implements Request {

	private int pid;
	
	public UpdateCameraToPlayerRequest(int pid) {
		this.pid = pid;
	}
	
	@Override
	public int getID() {
		return 1000;
	}

	@Override
	public void action(SpaceWorld w, TextWindow text) {
		Player p = w.getPlayer(pid);
		AbstractedPoint point = PointMaths.getMidpoint(p.getLocation().getTop(), p.getLocation().getBottom());
		w.getMainCamera().setLocation(new Point2(point.getX() - 320, point.getY() - 320, new PixelGrid()));
	}

}
