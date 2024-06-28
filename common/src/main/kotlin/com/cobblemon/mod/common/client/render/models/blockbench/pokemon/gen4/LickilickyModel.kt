/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen4

import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPosableModel
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BimanualFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.BipedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pose.Pose
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.PoseType.Companion.MOVING_POSES
import com.cobblemon.mod.common.entity.PoseType.Companion.STATIONARY_POSES
import com.cobblemon.mod.common.entity.PoseType.Companion.UI_POSES
import com.cobblemon.mod.common.util.isBattling
import net.minecraft.client.model.ModelPart
import net.minecraft.world.phys.Vec3

class LickilickyModel(root: ModelPart) : PokemonPosableModel(root), HeadedFrame, BipedFrame, BimanualFrame {
    override val rootPart = root.registerChildWithAllChildren("lickilicky")
    override val head = getPart("head")
    override val leftLeg = getPart("left_leg")
    override val rightLeg = getPart("right_leg")
    override val leftArm = getPart("left_upper_arm")
    override val rightArm = getPart("right_upper_arm")


    override var portraitScale = 1.4F
    override var portraitTranslation = Vec3(-0.2, 1.8, 0.0)

    override var profileScale = 0.5F
    override var profileTranslation = Vec3(0.0, 1.0, 0.0)

    lateinit var standing: Pose
    lateinit var walk: Pose
    lateinit var battleidle: Pose
    lateinit var sleep: Pose
    override fun registerPoses() {
        val blink = quirk { bedrockStateful("lickilicky", "blink") }
        sleep = registerPose(
            poseType = PoseType.SLEEP,
            animations = arrayOf(bedrock("lickilicky", "sleep"))
        )

        standing = registerPose(
            poseName = "standing",
            poseTypes = STATIONARY_POSES + UI_POSES,
            quirks = arrayOf(blink),
            condition = { !it.isBattling },
            animations = arrayOf(
                singleBoneLook(),
                bedrock("lickilicky", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = MOVING_POSES,
            condition = { !it.isBattling },
            quirks = arrayOf(blink),
            animations = arrayOf(
                singleBoneLook(),
                bedrock("lickilicky", "ground_walk")
            )
        )

        battleidle = registerPose(
            poseName = "battle_idle",
            poseTypes = PoseType.STATIONARY_POSES,
            transformTicks = 10,
            quirks = arrayOf(blink),
            condition = { it.isBattling },
            animations = arrayOf(
                singleBoneLook(),
                bedrock("lickilicky", "battle_idle")
            )

        )
    }

//    override fun getFaintAnimation(
//        pokemonEntity: PokemonEntity,
//        state: PosableState<PokemonEntity>
//    ) = if (state.isPosedIn(standing, walk, battleidle, sleep)) bedrockStateful("lickilicky", "faint") else null
}