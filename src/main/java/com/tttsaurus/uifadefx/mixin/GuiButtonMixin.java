package com.tttsaurus.uifadefx.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.tttsaurus.uifadefx.Constants;
import com.tttsaurus.uifadefx.fade.FadeContainer;
import com.tttsaurus.uifadefx.render.FixedFuncRenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.awt.*;

@Mixin(GuiButton.class)
public abstract class GuiButtonMixin {

    @Final
    @Shadow
    protected static ResourceLocation BUTTON_TEXTURES;

    @Shadow
    public int width;

    @Shadow
    public int height;

    @Shadow
    public int x;

    @Shadow
    public int y;

    @Shadow
    public String displayString;

    @Shadow
    public boolean enabled;

    @Shadow
    public boolean visible;

    @Shadow
    protected boolean hovered;

    @Shadow
    public int packedFGColour;

    @Shadow
    protected abstract int getHoverState(boolean mouseOver);

    @Shadow
    protected abstract void mouseDragged(Minecraft mc, int mouseX, int mouseY);

    @Unique
    private FadeContainer uifadefx$fadeContainer = null;

    @WrapMethod(method = "drawButton")
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks, Operation<Void> original) {
        if (!visible) {
            return;
        }

        if (uifadefx$fadeContainer == null) {
            uifadefx$fadeContainer = new FadeContainer();
        }

        GuiButton this0 = (GuiButton) (Object) this;

        FontRenderer fontrenderer = mc.fontRenderer;
        mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
        GlStateManager.color(1f, 1f, 1f, 1f);

        hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;

        int i = getHoverState(hovered);

        uifadefx$fadeContainer.update(hovered);
        float progress = uifadefx$fadeContainer.getProgress();

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        i = i == 2 ? 1 : i; // use the normal texture for the hovered state
        this0.drawTexturedModalRect(x, y, 0, 46 + i * 20, width / 2, height);
        this0.drawTexturedModalRect(x + width / 2, y, 200 - width / 2, 46 + i * 20, width / 2, height);

        // my own rendering logic
        if (enabled) {
            FixedFuncRenderUtils.renderRectBrightnessOverlay(x + 1, y + 1, width - 2, height - 2,
                    Constants.BUTTON_HOVER_STATE_COLOR_DIFF_R * progress,
                    Constants.BUTTON_HOVER_STATE_COLOR_DIFF_G * progress,
                    Constants.BUTTON_HOVER_STATE_COLOR_DIFF_B * progress);

            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

            if (hovered) {
                FixedFuncRenderUtils.renderRectOutline(x - 1, y - 1, width + 1, height + 1, 1, Constants.BUTTON_OUTLINE_COLOR.getRGB());
            }

            GlStateManager.enableTexture2D();
            GlStateManager.enableAlpha();
            GlStateManager.color(1f, 1f, 1f, 1f);
        }

        mouseDragged(mc, mouseX, mouseY);

        int j = 14737632;
        if (packedFGColour != 0) {
            j = packedFGColour;
        } else if (!enabled) {
            j = 10526880;
        } else if (hovered) {
            j = 16777120;
        }

        this0.drawCenteredString(fontrenderer, displayString, x + width / 2, y + (height - 8) / 2, j);
    }
}
