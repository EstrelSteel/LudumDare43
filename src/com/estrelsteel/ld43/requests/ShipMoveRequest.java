package com.estrelsteel.ld43.requests;

import javax.swing.JOptionPane;

import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.ld43.TextWindow;
import com.estrelsteel.ld43.world.SpaceWorld;

public class ShipMoveRequest implements Request {

	private int pid;
	private int destination;
	
	public ShipMoveRequest(int pid, int destination) {
		this.pid = pid;
		this.destination = destination;
	}
	
	@Override
	public int getID() {
		return 200;
	}
	@Override
	public void action(SpaceWorld w, TextWindow text) {
		if(w.isMovable()) {
			if(text.getContent().startsWith("Ship Data")) {
				int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to eject this item?", "", JOptionPane.YES_NO_OPTION);
				if(dialogResult == JOptionPane.YES_OPTION) {
					destination++;
					switch(destination) {
					case 1:
						w.getShip().life = 0;
						break;
					case 2:
						w.getShip().navigator = 0;
						break;
					case 3:
						w.getShip().fueltank = 0;
						w.getShip().setFuel(0);
						break;
					case 4:
						w.getShip().fueltransfer = 0;
						break;
					case 5:
						w.getShip().landing = 0;
						break;
					case 6:
						w.getShip().comms = 0;
						break;
					case 7:
						w.getShip().docker = 0;
						break;
					}
					text.clearContent().addContent(w.getShip().getShipCargo()).updateContent();
				}
			}
			return;
		}
		if(destination >= w.getNavIcons().size()) return;
		int d = w.getNavIcons().get(destination).getDistance();
		System.out.println(d + "\t" + w.getShip().getFuel());
		int dialogResult = JOptionPane.showConfirmDialog (null, "This will cost " + (int) (d / w.getShip().calcTravelRadius() * 100) + "% of your fuel.", "", JOptionPane.YES_NO_OPTION);
		if(dialogResult == JOptionPane.YES_OPTION) {
			w.getShip().setFuel(w.getShip().getFuel() - w.getShip().getFuel() * (d / w.getShip().calcTravelRadius()));
			w.getShip().setLocation(QuickRectangle.location(w.getNavIcons().get(destination).getLocation().getX(), w.getNavIcons().get(destination).getLocation().getY(), 0, 0));
		}
		
	}

}
