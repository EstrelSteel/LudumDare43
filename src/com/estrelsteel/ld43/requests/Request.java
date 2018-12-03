package com.estrelsteel.ld43.requests;

import com.estrelsteel.ld43.TextWindow;
import com.estrelsteel.ld43.world.SpaceWorld;

public interface Request {
	public int getID();
	public void action(SpaceWorld w, TextWindow text);
}
