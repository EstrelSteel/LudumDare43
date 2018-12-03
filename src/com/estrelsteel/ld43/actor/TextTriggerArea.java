package com.estrelsteel.ld43.actor;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.ld43.TextWindow;
import com.estrelsteel.ld43.world.SpaceWorld;

public class TextTriggerArea extends TriggerArea {

	private String path;
	
	public TextTriggerArea(String name, Rectangle loc, String path) {
		super(name, loc);
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public void action(SpaceWorld w, TextWindow text) {
		text.clearContent().loadContent(Engine2.devPath + path).updateContent();
		updateCollision();
	}

	@Override
	public boolean isConsumed() {
		return getConsumed() >= 0;
	}

	@Override
	public void updateCollision() {
		getCollision().setCollide(isConsumed());
	}

}
