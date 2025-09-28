package com.musketeers.foolish_guns.model;


import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;

import java.util.function.BiConsumer;


// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class BulletModel extends EntityModel<EntityRenderState> {
	private final ModelPart bb_main;
	public BulletModel(ModelPart root) {
        super(root);
        this.bb_main = root.getChild("bb_main");
	}
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition bb_main = modelPartData.addOrReplaceChild("bb_main",
                CubeListBuilder.create()
                        .texOffs(0, 16).addBox(-6.0F, 6.0F, -3.0F, 12.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 28).addBox(-7.0F, 7.0F, -2.0F, 13.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(34, 28).addBox(6.0F, 5.0F, -4.0F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, 5.0F, -4.0F, 10.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0f,0f,0f));
		return LayerDefinition.create(modelData, 64, 64);
	}

}