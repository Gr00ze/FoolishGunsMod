package com.musketeers.foolish_guns;

import com.musketeers.foolish_guns.entities.EntityList;
import com.musketeers.foolish_guns.items.ItemGroups;
import com.musketeers.foolish_guns.network.Network;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.musketeers.foolish_guns.items.ItemList;

public class FoolishGuns implements ModInitializer {
	public static final String MOD_ID = "foolish-guns";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
        ItemList.initialize();
        ItemGroups.initialize();
        EntityList.initialize();
        Network.initialize();
        GunParticles.initialize();
	}
}