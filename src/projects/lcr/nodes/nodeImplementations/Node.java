package projects.lcr.nodes.nodeImplementations;

import java.awt.Color;


import java.awt.Graphics;
import java.util.Random;

import projects.lcr.nodes.messages.LcrMessage;

import projects.lcr.nodes.messages.leaderMessage;
import projects.lcr.nodes.timers.MessageTimer;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.gui.transformation.PositionTransformation;
import sinalgo.nodes.messages.Inbox;
import sinalgo.nodes.messages.Message;

public class Node extends sinalgo.nodes.Node {
	
	
	static final int MAX_UIN = 10000;
	private int myUin;

	@Override
	public void handleMessages(Inbox inbox) {
	
		while(inbox.hasNext()) {
			Message msg = inbox.next();
		

		
			if (msg instanceof LcrMessage) {
				treat((LcrMessage) msg);
			} 
			if (msg instanceof leaderMessage) {
				treat2((leaderMessage) msg);
			} 
			
		}
		
	}
	private void treat2(leaderMessage m) {
		int id = m.getID();	 
		if (id == ID) {
			this.setColor(Color.red);
			System.out.println(this + " the leader");
		} else {
						
			broadcast(new leaderMessage(id));
			System.out.println(this + " receives ID " + id);
			this.setColor(Color.green);
			

		}	
	}

		
	private void treat(LcrMessage m) {    		
	 int uin = m.getUin();
		System.out.println(this + " receives uin " + uin);
	 
		if (uin == myUin/*du noeud*/) {
			this.setColor(Color.BLUE);
			broadcast(new leaderMessage(ID));
			System.out.println(this + " is the leader"); 

		} else if (uin > myUin) {
			System.out.println(this + " forwards " + uin);
			broadcast(new LcrMessage(uin));
		
		}
	
		
	}


	@Override
	public void init() {
		
	    Random rand = sinalgo.tools.Tools.getRandomNumberGenerator();
	    myUin = rand.nextInt(MAX_UIN);   
		System.out.println(this + " is initialized. UIN = " + myUin);
		new MessageTimer(new LcrMessage(myUin)).startRelative(1, this);
		

	}

	@Override
	public void draw(Graphics g, PositionTransformation pt, boolean highlight) {
		drawNodeAsDiskWithText(g, pt, highlight, (new Integer(ID).toString()), 25, Color.white);
	}

	@NodePopupMethod(menuText="broadcast")
	public void broadcast() {
		broadcast(new LcrMessage(myUin));
		//broadcast(new leaderMessage(ID));
	}
	
	@Override
	public void preStep() { }

	@Override
	public void neighborhoodChange() { }

	@Override
	public void postStep() {  }

	@Override
	public void checkRequirements() throws WrongConfigurationException { }
	
}
