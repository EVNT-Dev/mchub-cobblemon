/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen4

import com.cobblemon.mod.common.client.render.models.blockbench.PosableState
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPosableModel
import com.cobblemon.mod.common.client.render.models.blockbench.pose.CobblemonPose
import com.cobblemon.mod.common.entity.PoseType
import net.minecraft.client.model.ModelPart
import net.minecraft.world.phys.Vec3

class DrifblimModel (root: ModelPart) : PokemonPosableModel(root) {
    override val rootPart = root.registerChildWithAllChildren("drifblim")

    override var portraitScale = 0.9F
    override var portraitTranslation = Vec3(-0.35, 1.8, 0.0)

    override var profileScale = 0.4F
    override var profileTranslation = Vec3(0.0, 1.1, 0.0)

    lateinit var standing: CobblemonPose
    lateinit var walk: CobblemonPose
    lateinit var sleep: CobblemonPose
    lateinit var hovering: CobblemonPose
    lateinit var flying: CobblemonPose

    override val cryAnimation = CryProvider { bedrockStateful("drifblim", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("drifblim", "blink") }
        sleep = registerPose(
            poseName = "sleep",
            poseType = PoseType.SLEEP,
            animations = arrayOf(
                bedrock("drifblim", "sleep")
            )
        )

        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.STATIONARY_POSES + PoseType.UI_POSES - PoseType.HOVER,
            transformTicks = 10,
            quirks = arrayOf(blink),
            animations = arrayOf(
                bedrock("drifblim", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = PoseType.MOVING_POSES - PoseType.FLY,
            quirks = arrayOf(blink),
            transformTicks = 10,
            animations = arrayOf(
                bedrock("drifblim", "ground_walk")
            )
        )

        hovering = registerPose(
            poseName = "hovering",
            poseType = PoseType.HOVER,
            transformTicks = 10,
            quirks = arrayOf(blink),
            animations = arrayOf(
                bedrock("drifblim", "air_idle")
            )
        )

        flying = registerPose(
            poseName = "flying",
            poseType = PoseType.FLY,
            transformTicks = 10,
            quirks = arrayOf(blink),
            animations = arrayOf(
                bedrock("drifblim", "air_fly")
            )
        )
    }

    override fun getFaintAnimation(state: PosableState) = if (state.isPosedIn(standing, walk)) bedrockStateful("drifblim", "faint") else null
}