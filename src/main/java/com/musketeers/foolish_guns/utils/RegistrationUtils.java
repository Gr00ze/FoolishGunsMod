package com.musketeers.foolish_guns.utils;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

import static com.musketeers.foolish_guns.FoolishGuns.MOD_ID;

public class RegistrationUtils {

    public static ResourceLocation id(String objectId){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID,objectId);
    }

    /**
     * @param itemName the name of the item example IRON_SWORD
     * @param itemFactory the method reference of the constructor of the class Item::new, CustomItem::new
     * @param itemProperties The properties of the item.
     * @return The item itself
     * @implNote registerItem('sword', SwordItem::new, new Item.Properties() )
     * **/
    public static <I extends Item> I registerItem(String itemName, Function<Item.Properties, I> itemFactory, Item.Properties itemProperties){
        ResourceLocation resourceLocation = id(itemName);
        ResourceKey<Item> resourceKey = ResourceKey.create(Registries.ITEM, resourceLocation);

        itemProperties.setId(resourceKey);
        return Registry.register(BuiltInRegistries.ITEM, resourceKey, itemFactory.apply(itemProperties));
    }

    public static <E extends Entity> EntityType<E> registerEntity(String entityName, EntityType.Builder<E> builder){
        ResourceLocation resourceLocation = id(entityName);
        ResourceKey<EntityType<?>> resourceKey = ResourceKey.create(Registries.ENTITY_TYPE, resourceLocation);

        return Registry.register(BuiltInRegistries.ENTITY_TYPE, resourceKey, builder.build(resourceKey));
    }

    public static <B extends BlockEntity> BlockEntityType<B> registerBlockEntity(String blockEntityId, FabricBlockEntityTypeBuilder.Factory<B> factory, Block...blocks){
        ResourceLocation id = id(blockEntityId);
        ResourceKey<BlockEntityType<?>> key = ResourceKey.create(Registries.BLOCK_ENTITY_TYPE, id);
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, key, FabricBlockEntityTypeBuilder.create(factory, blocks).build());
    }
    private static ResourceKey<Block> registerBlockKey(ResourceLocation id,BlockBehaviour.Properties settings){
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, id);
        settings.setId(key);
        return key;
    }
    /**
     * Register for simple blocks and custom blocks
     * @param blockName Unique block name
     * @param factory is the constructor of your block. Example: {@code Block::new CustomBlock::new}
     * **/
    public static Block registerBlock(String blockName, Function<BlockBehaviour.Properties, Block> factory , BlockBehaviour.Properties settings){
        settings = settings == null ? BlockBehaviour.Properties.of() : settings;
        factory = factory == null ? Block::new : factory;

        ResourceKey<Block> key = registerBlockKey(id(blockName), settings);

        Block block = factory.apply(settings);
        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }
    /**
     * Register a block but everything you register with this will be a {@code Block} and can't be a {@code CustomBlock}
     * **/

    public static Block registerBlock(String blockName, BlockBehaviour.Properties settings){
        return registerBlock(blockName, Block::new ,settings);
    }
    /**
     * Register a block but everything you register with this will be a {@code Block} and can't be a {@code CustomBlock}
     * **/

    public static Block registerBlock(String blockName){
        return registerBlock(blockName, null , null);
    }

    public static CreativeModeTab registerCreativeModeTab(ResourceKey<CreativeModeTab> resourceKey, CreativeModeTab creativeModeTab){
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, resourceKey, creativeModeTab);
    }

    public static <P extends ParticleType<?>> P registerParticle(String particleName, P particleType){
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, id(particleName), particleType);
    };

}
