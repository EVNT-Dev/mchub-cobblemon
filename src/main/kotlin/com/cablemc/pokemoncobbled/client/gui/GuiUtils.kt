package com.cablemc.pokemoncobbled.client.gui

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.resources.ResourceLocation

fun blitk(
    poseStack: PoseStack,
    texture: ResourceLocation? = null,
    x: Number,
    y: Number,
    height: Number = 0,
    width: Number = 0,
    uOffset: Number = 0,
    vOffset: Number = 0,
    alpha: Number = 1F
) {
    val previousShader = RenderSystem.getShader()
    RenderSystem.setShader { GameRenderer.getPositionTexShader() }
    texture?.run { RenderSystem.setShaderTexture(0, this) }
    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha.toFloat())
    RenderSystem.enableBlend()
    RenderSystem.defaultBlendFunc()
    RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA)
    Gui.blit(
        poseStack,
        x.toInt(), y.toInt(),
        uOffset.toFloat(), vOffset.toFloat(),
        width.toInt(), height.toInt(),
        width.toInt(), height.toInt()
    )
    RenderSystem.setShader { previousShader }
}