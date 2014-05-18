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
