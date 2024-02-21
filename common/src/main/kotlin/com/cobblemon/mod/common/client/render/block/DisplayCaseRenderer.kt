/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.block

import com.cobblemon.mod.common.CobblemonBlocks
import com.cobblemon.mod.common.CobblemonItems
import com.cobblemon.mod.common.api.tags.CobblemonItemTags
import com.cobblemon.mod.common.block.entity.DisplayCaseBlockEntity
import com.cobblemon.mod.common.client.render.models.blockbench.repository.PokemonModelRepository
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.item.PokeBallItem
import com.cobblemon.mod.common.item.PokemonItem
import net.minecraft.block.HorizontalFacingBlock
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.render.model.json.ModelTransformationMode
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.tag.ItemTags
import net.minecraft.util.math.Direction
import net.minecraft.util.math.RotationAxis
import net.minecraft.world.World

class DisplayCaseRenderer(ctx: BlockEntityRendererFactory.Context) : BlockEntityRenderer<DisplayCaseBlockEntity> {
    override fun render(
        entity: DisplayCaseBlockEntity,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        overlay: Int
    ) {
        val stack = entity.getStack()
        val world = entity.world ?: return
        val posType = getPositioningType(stack, world)
        val blockState = if (entity.world != null) entity.cachedState
            else (CobblemonBlocks.DISPLAY_CASE.defaultState.with(HorizontalFacingBlock.FACING, Direction.NORTH))
        val yRot = if (posType == PositioningType.ITEM_MODEL) blockState.get(HorizontalFacingBlock.FACING).opposite.asRotation()
            else blockState.get(HorizontalFacingBlock.FACING).asRotation()

        if (stack.item is PokemonItem) {
            renderPokemon(
                matrices,
                vertexConsumers,
                light,
                stack,
                yRot
            )
            return
        }

        matrices.push()
        matrices.translate(0.5f, 0.4f, 0.5f)

        matrices.scale(posType.scaleX, posType.scaleY, posType.scaleZ)
        matrices.translate(posType.transX, posType.transY, posType.transZ)

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-yRot))
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(posType.rotY))

        MinecraftClient.getInstance().itemRenderer.renderItem(
            stack,
            ModelTransformationMode.GROUND,
            light,
            overlay,
            matrices,
            vertexConsumers,
            entity.world,
            0
        )

        matrices.pop()

    }

    private fun renderPokemon(
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        stack: ItemStack,
        yRot: Float
    ) {
        val item = stack.item as? PokemonItem ?: return
        val pokemon = item.asPokemon(stack) ?: return
        val model = PokemonModelRepository.getPoser(pokemon.species.resourceIdentifier, pokemon.aspects)
        val renderLayer = model.getLayer(PokemonModelRepository.getTexture(pokemon.species.resourceIdentifier, pokemon.aspects, 0F))
        val tint = item.tint(stack)
        val vertexConsumer: VertexConsumer = vertexConsumers.getBuffer(renderLayer)
        val scale = 0.25f

        matrices.push()
        matrices.scale(1f, -1f, -1f)
        matrices.translate(0.5f, -0.69f, -0.5f)
        matrices.scale(scale, scale, scale)
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yRot))

        model.setupAnimStateless(PoseType.PROFILE)

        model.withLayerContext(vertexConsumers, null, PokemonModelRepository.getLayers(pokemon.species.resourceIdentifier, pokemon.aspects)) {
            model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, tint.x, tint.y, tint.z, tint.w)
        }

        matrices.pop()
    }

    companion object {
        private fun getPositioningType(stack: ItemStack, world: World): PositioningType {
            if (stack.item == Items.SHIELD) return PositioningType.SHIELD
            if (stack.item == Items.DECORATED_POT) return PositioningType.MOB_HEAD
            if (stack.isIn(ItemTags.BEDS)) return PositioningType.BED
            if (stack.isIn(ItemTags.BANNERS)) return PositioningType.BANNER
            if (stack.isIn(CobblemonItemTags.MOB_HEADS)) return PositioningType.MOB_HEAD
            if (stack.item == CobblemonItems.PASTURE) return PositioningType.PASTURE
            if (stack.item is PokeBallItem) return PositioningType.POKE_BALL
            if (stack.item == CobblemonItems.POKEMON_MODEL) return PositioningType.ITEM_MODEL
            if (MinecraftClient.getInstance().itemRenderer.getModel(stack, world, null, 0).hasDepth()) return PositioningType.BLOCK_MODEL
            return PositioningType.ITEM_MODEL
        }
    }

    private enum class PositioningType(
        val scaleX: Float, val scaleY: Float, val scaleZ: Float,
        val transX: Float, val transY: Float, val transZ: Float,
        val rotY: Float = 0f
    ) {
        POKE_BALL(1f, 1f, 1f, 0f, 0.04f, 0f),
        BLOCK_MODEL(1f, 1f, 1f, 0f, -0.15f, 0f),
        ITEM_MODEL(1f, 1f, 1f, 0f, 0.04f, 0f),
        BED(1f, 1f, 1f, 0f, -0.02f, 0f),
        BANNER(1f, 1f, 1f, 0f, -0.02f, 0f, 180f),
        MOB_HEAD(1f, 1f, 1f, 0f, -0.025f, 0f, 180f),
        SHIELD(1f, 1f, 1f, 0f, -0.045f, 0f, 180f),
        PASTURE(1f, 1f, 1f, 0f, 0.0375f, 0f),
    }
}