package projects.lcr.nodes.messages;

import sinalgo.nodes.messages.Message;

public class LcrMessage extends Message {

	final private int uin;
	
	public LcrMessage(int i) {
		uin = i;
	}
	
	public int getUin() {
		return uin;
	}
	
	@Override
	public Message clone() {
		return this;
	}

}
