package com.musketeers.foolish_guns;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class CustomParticle extends TextureSheetParticle {

    private final double angularSpeed;
    private final double radiusGrowth;
    private double angle;

    public CustomParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);



        // Imposta colore e dimensione iniziale
        this.rCol = 1.0f;
        this.gCol = 0.8f;
        this.bCol = 0.3f;
        this.alpha = 1.0f;
        this.quadSize = 0.2f + this.random.nextFloat() * 0.1f;

        // Parametri spirale
        this.angle = this.random.nextDouble() * 2 * Math.PI; // angolo iniziale casuale
        this.angularSpeed = 0.3 + this.random.nextDouble() * 0.2; // velocità rotazione
        this.radiusGrowth = 0.02 + this.random.nextDouble() * 0.02; // crescita raggio

        // Durata
        this.lifetime = 20 + this.random.nextInt(15);
        this.hasPhysics = false;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();

        // Fading
        this.alpha = Mth.clamp(1.0f - ((float) this.age / (float) this.lifetime), 0.0f, 1.0f);

        // Movimento rotatorio a spirale
        this.angle += this.angularSpeed;
        double radius = this.age * this.radiusGrowth;

        this.x += Math.cos(this.angle) * radius * 0.1;
        this.z += Math.sin(this.angle) * radius * 0.1;

        // Leggero sollevamento o affondamento
        this.y += 0.01;

        // Aggiorna sprite se animato
        //this.setSpriteFromAge(this.sprites);

        // Rimuove particella se finita
        if (this.age >= this.lifetime) {
            this.remove();
        }
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Factory(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            CustomParticle particle = new CustomParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(this.sprites);

            return particle;
        }
    }
}
