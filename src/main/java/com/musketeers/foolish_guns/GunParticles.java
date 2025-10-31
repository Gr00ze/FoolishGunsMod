package com.musketeers.foolish_guns;

import com.musketeers.foolish_guns.utils.RegistrationUtils;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;


import net.minecraft.core.particles.SimpleParticleType;

public class GunParticles {
    public static final SimpleParticleType SPOOKY_SPIRAL = RegistrationUtils.registerParticle("spooky_particle",FabricParticleTypes.simple());

    public static void initialize(){

    }
}
