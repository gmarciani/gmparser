/*	The MIT License (MIT)
 *
 *	Copyright (c) 2014 Giacomo Marciani
 *	
 *	Permission is hereby granted, free of charge, to any person obtaining a copy
 *	of this software and associated documentation files (the "Software"), to deal
 *	in the Software without restriction, including without limitation the rights
 *	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *	copies of the Software, and to permit persons to whom the Software is
 *	furnished to do so, subject to the following conditions:
 *	
 *	The above copyright notice and this permission notice shall be included in all
 *	copies or substantial portions of the Software.
 *	
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *	SOFTWARE.
*/

package com.gmarciani.gmparser.views.interaction;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Interactions implements Serializable {

	private static final long serialVersionUID = -5420694064826994644L;
	
	private Map<String, Interaction> interactions = new HashMap<String, Interaction>();
	
	public Interactions addInteraction(Interaction interaction) {
		String interactionIdentifier = getInteractionIdentifier(interaction);
		interactions.put(interactionIdentifier, interaction);
		return this;
	}
	
	private String getInteractionIdentifier(Interaction interaction) {
		String name = interaction.getName();
		String identifier = name.toLowerCase().replaceAll(" ", "-");
		return identifier;
	}

	public String run(String interactionIdentifier) {
		Interaction interaction = interactions.get(interactionIdentifier);
		return interaction.run();
	}

}
