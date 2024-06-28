/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.block

import com.mojang.serialization.MapCodec
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.ShapeContext
import net.minecraft.entity.EquipmentSlot
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.core.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.level.Level

@Suppress("OVERRIDE_DEPRECATION", "DEPRECATION")
class BigRootBlock(settings: Settings) : RootBlock(settings) {
    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape = AABB

    override fun onUse(
        state: BlockState,
        world: Level,
        pos: BlockPos,
        player: Player,
        hit: BlockHitResult
    ): ActionResult? {
        val stack = player.getStackInHand(Hand.MAIN_HAND)
        if (stack.isOf(Items.SHEARS)) {
            this.attemptShear(world, state, pos) {
                player.sendEquipmentBreakStatus(stack.item, EquipmentSlot.MAINHAND)
                stack.damage(1, player, EquipmentSlot.MAINHAND)
            }
            return ActionResult.success(world.isClient)
        }
        return super.onUse(state, world, pos, player, hit)
    }

    override fun shearedResultingState(): BlockState = Blocks.HANGING_ROOTS.defaultState

    override fun shearedDrop(): ItemStack = Items.STRING.defaultStack

    override fun getCodec(): MapCodec<out Block> {
        return CODEC
    }

    companion object {
        val CODEC = createCodec(::BigRootBlock)

        private val AABB = VoxelShapes.cuboid(0.2, 0.3, 0.2, 0.8, 1.0, 0.8)
    }

}