package com.musketeers.foolish_guns;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class ParticleRendering {

    public static void initialize(){
        ParticleFactoryRegistry.getInstance().register(GunParticles.CUSTOM_SPARK,CustomParticle.Factory::new);
    }
}
