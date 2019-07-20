/*
 Copyright (c) 2007, Distributed Computing Group (DCG)
                    ETH Zurich
                    Switzerland
                    dcg.ethz.ch

 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 - Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.

 - Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the
   distribution.

 - Neither the name 'Sinalgo' nor the names of its contributors may be
   used to endorse or promote products derived from this software
   without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package projects.lcr.models.connectivityModels;

import java.util.Enumeration;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.nodes.Node;
import sinalgo.runtime.nodeCollection.NodeCollectionInterface;
import sinalgo.tools.Tools;

public class UniRing extends sinalgo.models.ConnectivityModel  {
	
	public boolean updateConnections(Node n) throws WrongConfigurationException{
		boolean edgeAdded = false;
		
		NodeCollectionInterface pNE = Tools.getNodeList(); 
		Enumeration<Node> enu = pNE.getNodeEnumeration();
		int size = pNE.size();
		while( enu.hasMoreElements() ){
			Node possibleNeighbor = enu.nextElement();
			if(n.ID != possibleNeighbor.ID){
				// if the possible neighbor is connected with the the node: add the connection to the outgoing connection of n 
				if(n.ID +  1 == possibleNeighbor.ID || (n.ID == size && possibleNeighbor.ID == 1)){
					// add it to the outgoing Edges of n. The EdgeCollection itself checks, if the Edge is already contained
					edgeAdded = !n.outgoingConnections.add(n, possibleNeighbor, true) || edgeAdded; // note: don't write it the other way round, otherwise, the edge is not added if edgeAdded is true.
				} 
			}
		}
		// loop over all edges again and remove edges that have not been marked 'valid' in this round
		boolean dyingLinks = n.outgoingConnections.removeInvalidLinks(); 

		return edgeAdded || dyingLinks; // return whether an edge has been added or removed.
	}	

}

