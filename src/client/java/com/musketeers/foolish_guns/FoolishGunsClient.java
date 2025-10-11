package com.musketeers.foolish_guns;

import com.musketeers.foolish_guns.render.EntityRenderList;
import com.musketeers.foolish_guns.render.ItemRenderer;
import com.musketeers.foolish_guns.render.RenderTest;
import net.fabricmc.api.ClientModInitializer;


public class FoolishGunsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ItemRenderer.init();
        EntityRenderList.initialize();
        //RenderTest.test();
        CustomInputsTest.registerEvents();
	}
}