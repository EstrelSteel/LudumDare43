package com.estrelsteel.ld43.requests;

import com.estrelsteel.ld43.TextWindow;
import com.estrelsteel.ld43.world.SpaceWorld;

public class PlayerAnimationRequest implements Request {

	private int pid;
	private int animation;
	
	public PlayerAnimationRequest(int pid, int animation) {
		this.pid = pid;
		this.animation = animation;
	}
	
	@Override
	public int getID() {
		return 101;
	}

	@Override
	public void action(SpaceWorld w, TextWindow text) {
		if(!w.isMovable()) return;
		w.getPlayer(pid).setRunningAnimationNumber(animation);
	}

}
