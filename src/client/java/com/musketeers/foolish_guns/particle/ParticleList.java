package com.musketeers.foolish_guns.particle;

import com.musketeers.foolish_guns.particles.GunParticles;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;


public class ParticleList {
    public static void initialize(){
        ParticleFactoryRegistry.getInstance().register(GunParticles.SPOOKY_SPIRAL, SpookyParticle.Factory::new);
    }
}
