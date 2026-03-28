package com.tttsaurus.uifadefx.render;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public final class FixedFuncRenderUtils {
    private FixedFuncRenderUtils() {
    }

    public static void renderRectBrightnessOverlay(float x, float y, float width, float height, float r, float g, float b) {
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.disableTexture2D();
        GlStateManager.color(r, g, b);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(x, y + height, 0).endVertex();
        bufferbuilder.pos(x + width, y + height, 0).endVertex();
        bufferbuilder.pos(x + width, y, 0).endVertex();
        bufferbuilder.pos(x, y, 0).endVertex();
        tessellator.draw();
    }

    public static void renderRect(float x, float y, float width, float height, int color) {
        float a = (float) (color >> 24 & 255) / 255f;
        float r = (float) (color >> 16 & 255) / 255f;
        float g = (float) (color >> 8 & 255) / 255f;
        float b = (float) (color & 255) / 255f;

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(x, y + height, 0).color(r, g, b, a).endVertex();
        buffer.pos(x + width, y + height, 0).color(r, g, b, a).endVertex();
        buffer.pos(x + width, y, 0).color(r, g, b, a).endVertex();
        buffer.pos(x, y, 0).color(r, g, b, a).endVertex();
        tessellator.draw();
    }

    public static void renderRectOutline(float x, float y, float width, float height, float thickness, int color) {
        renderRect(x + thickness, y, width, thickness, color);
        renderRect(x, y + height, width, thickness, color);
        renderRect(x, y, thickness, height, color);
        renderRect(x + width, y + thickness, thickness, height, color);
    }
}
