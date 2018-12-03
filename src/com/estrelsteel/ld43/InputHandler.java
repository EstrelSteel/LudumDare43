package com.estrelsteel.ld43;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.estrelsteel.engine2.events.listener.TickListener;
import com.estrelsteel.ld43.actor.Player;
import com.estrelsteel.ld43.requests.PlayerActionRequest;
import com.estrelsteel.ld43.requests.PlayerAnimationRequest;
import com.estrelsteel.ld43.requests.PlayerMoveRequest;
import com.estrelsteel.ld43.requests.ShipMoveRequest;
import com.estrelsteel.ld43.requests.UpdateCameraToPlayerRequest;


public class InputHandler implements KeyListener, TickListener {

	private LD43 ld;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean action0;
	private boolean action1;
	
	public InputHandler(LD43 ld) {
		this.ld = ld;
		this.up = false;
		this.down = false;
		this.left = false;
		this.right = false;
		this.action0 = false;
	}
	
	@Override
	public void tick() {
		if(up || down || left || right) {
			for(int i = 0; i < Player.walkspeed; i++) {
				if(up && !down) {
					ld.requests.add(new PlayerMoveRequest(0, 0, -1, false));
					if(i == 0) ld.requests.add(new PlayerAnimationRequest(0, 1));
				}
				else if(!up && down) {
					ld.requests.add(new PlayerMoveRequest(0, 0, 1, false));
					if(i == 0) ld.requests.add(new PlayerAnimationRequest(0, 0));
				}
				
				if(left && !right) {
					ld.requests.add(new PlayerMoveRequest(0, -1, 0, false));
					if(i == 0) ld.requests.add(new PlayerAnimationRequest(0, 3));
				}
				else if(!left && right) {
					ld.requests.add(new PlayerMoveRequest(0, 1, 0, false));
					if(i == 0) ld.requests.add(new PlayerAnimationRequest(0, 2));
				}
			}
		}
		ld.requests.add(new UpdateCameraToPlayerRequest(0));
		if(action0) ld.requests.add(new PlayerActionRequest(0, 0));
		if(action1) ld.requests.add(new PlayerActionRequest(0, 1));
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case 87: /* W */
		case 38: /* UP */
			up = true;
			break;
		case 83: /* W */
		case 40: /* DOWN */
			down = true;
			break;
		case 65: /* A */
		case 37: /* LEFT */
			left = true;
			break;
		case 68: /* D */
		case 39: /* RIGHT */
			right = true;
			break;
		case 32: /* SPACE */
			action0 = true;
			break;
		case 27: /* ESCAPE */
			action1 = true;
			break;
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
			ld.requests.add(new ShipMoveRequest(0, e.getKeyCode() - 49));
			break;
		case 116: /* F5 */
			ld.start();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case 87: /* W */
		case 38: /* UP */
			up = false;
			break;
		case 83: /* W */
		case 40: /* DOWN */
			down = false;
			break;
		case 65: /* A */
		case 37: /* LEFT */
			left = false;
			break;
		case 68: /* D */
		case 39: /* RIGHT */
			right = false;
			break;
		case 32: /* SPACE */
			action0 = false;
			break;
		case 27: /* ESCAPE */
			action1 = false;
			break;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
