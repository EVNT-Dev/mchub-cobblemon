/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen4

import com.cobblemon.mod.common.client.render.models.blockbench.animation.BipedWalkAnimation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BipedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.entity.PoseType.Companion.MOVING_POSES
import com.cobblemon.mod.common.entity.PoseType.Companion.STATIONARY_POSES
import com.cobblemon.mod.common.entity.PoseType.Companion.UI_POSES
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class BunearyModel(root: ModelPart) : PokemonPoseableModel(), HeadedFrame, BipedFrame {
    override val rootPart = root.registerChildWithAllChildren("buneary")
    override val head = getPart("head")

    override val leftLeg = getPart("foot_left")
    override val rightLeg = getPart("foot_right")

    override val portraitScale = 1.8F
    override val portraitTranslation = Vec3d(-0.1, -0.1, 0.0)

    override val profileScale = 0.7F
    override val profileTranslation = Vec3d(0.0, 0.67, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("buneary", "cry").setPreventsIdle(false) }

    override fun registerPoses() {
        val blink = quirk("blink") { bedrockStateful("buneary", "blink").setPreventsIdle(false) }
        standing = registerPose(
            poseName = "standing",
            poseTypes = STATIONARY_POSES + UI_POSES,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("buneary", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = MOVING_POSES,
            quirks = arrayOf(blink),
            idleAnimations = arrayOf(
                singleBoneLook(),
                bedrock("buneary", "ground_idle"),
                BipedWalkAnimation(this, periodMultiplier = 0.8F, amplitudeMultiplier = 0.7F)
                //bedrock("buneary", "ground_walk")
            )
        )
    }

//    override fun getFaintAnimation(
//        pokemonEntity: PokemonEntity,
//        state: PoseableEntityState<PokemonEntity>
//    ) = if (state.isPosedIn(standing, walk)) bedrockStateful("buneary", "faint") else null
}