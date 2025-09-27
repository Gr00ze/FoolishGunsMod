package com.musketeers.foolish_guns;

import com.musketeers.foolish_guns.render.EntityRenderer;
import com.musketeers.foolish_guns.render.ItemRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;


public class FoolishGunsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ItemRenderer.init();
        EntityRenderer.initialize();
	}
}