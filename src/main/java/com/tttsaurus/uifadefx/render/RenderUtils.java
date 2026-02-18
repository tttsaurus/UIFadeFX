package com.tttsaurus.uifadefx.render;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public final class RenderUtils {
    private RenderUtils() {
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
}
