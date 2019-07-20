package projects.lcr.nodes.messages;

import sinalgo.nodes.messages.Message;

public class leaderMessage extends Message {

	final private int id;
	
	public leaderMessage(int i) {
		id = i;
	}
	
	public int getID() {
		return id;
	}
	
	@Override
	public Message clone() {
		return this;
	}

}

