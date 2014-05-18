package com.gmarciani.gmparser.views.interaction;

public class InteractionBuilder {
	
	private static InteractionBuilder instance = new InteractionBuilder();
	
	private static String interactionName;
	private static String interactionDescription;
	
	private InteractionBuilder() {
		
	}
	
	private static void reset() {
		interactionName = null;
		interactionDescription = null;
	}

	public static InteractionBuilder hasName(String name) {
		interactionName = name;
		return instance;
	}
	
	public static InteractionBuilder withDescription(String description) {
		interactionDescription = description;
		return instance;
	}
	
	public static Interaction create() {
		Interaction interaction = new Interaction();
		interaction.setName(interactionName);
		interaction.setDescription(interactionDescription);
		
		InteractionBuilder.reset();
		
		return interaction;
	}
	
}
