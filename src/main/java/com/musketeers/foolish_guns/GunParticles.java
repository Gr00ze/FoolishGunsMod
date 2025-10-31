package com.musketeers.foolish_guns;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

import static com.musketeers.foolish_guns.utils.RegistrationUtils.id;

public class GunParticles {
    public static final SimpleParticleType CUSTOM_SPARK = FabricParticleTypes.simple();

    public static void initialize(){
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, id("my_particle"), CUSTOM_SPARK);
    }
}
