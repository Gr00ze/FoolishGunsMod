// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class bullet_id extends EntityModel<Entity> {
	private final ModelPart bb_main;
	public bullet_id(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 16).cuboid(-6.0F, 6.0F, -3.0F, 12.0F, 6.0F, 6.0F, new Dilation(0.0F))
		.uv(0, 28).cuboid(-7.0F, 7.0F, -2.0F, 13.0F, 4.0F, 4.0F, new Dilation(0.0F))
		.uv(34, 28).cuboid(6.0F, 5.0F, -4.0F, 2.0F, 8.0F, 8.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-5.0F, 5.0F, -4.0F, 10.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}