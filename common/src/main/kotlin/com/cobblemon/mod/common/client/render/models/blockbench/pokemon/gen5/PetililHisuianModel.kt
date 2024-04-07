/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen5

import com.cobblemon.mod.common.client.render.models.blockbench.PoseableEntityState
import com.cobblemon.mod.common.client.render.models.blockbench.createTransformation
import com.cobblemon.mod.common.client.render.models.blockbench.frame.HeadedFrame
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.CryProvider
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPose
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.client.render.models.blockbench.pose.ModelPartTransformation
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.client.model.ModelPart
import net.minecraft.util.math.Vec3d

class PetililHisuianModel (root: ModelPart) : PokemonPoseableModel(), HeadedFrame {
    override val rootPart = root.registerChildWithAllChildren("petilil_hisui")
    override val head = getPart("head")
    val leaf_back = getPart("leaf_back_rotation")
    val leaf_left = getPart("leaf_left_rotation")
    val leaf_right = getPart("leaf_right_rotation")

    override var portraitScale = 1.75F
    override var portraitTranslation = Vec3d(0.01, -0.39, 0.0)

    override var profileScale = 0.8F
    override var profileTranslation = Vec3d(0.0, 0.5, 0.0)

    lateinit var standing: PokemonPose
    lateinit var walk: PokemonPose
    lateinit var sleep: PokemonPose
    lateinit var battleIdle: PokemonPose

    override val cryAnimation = CryProvider { _, _ -> bedrockStateful("petilil", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("petilil", "blink") }
        val quirk = quirk { bedrockStateful("petilil", "idle_quirk") }

        sleep = registerPose(
                poseName = "sleep",
                poseType = PoseType.SLEEP,
                quirks = arrayOf(blink),
                idleAnimations = arrayOf(
                        bedrock("petilil", "sleep")
                ),
                transformedParts = arrayOf(
                        leaf_back.createTransformation().addRotationDegrees(ModelPartTransformation.X_AXIS, 6.5),
                        leaf_left.createTransformation().addRotationDegrees(ModelPartTransformation.X_AXIS, -19.5),
                        leaf_right.createTransformation().addRotationDegrees(ModelPartTransformation.X_AXIS, -19.5)
                ),
        )

        standing = registerPose(
                poseName = "standing",
                poseTypes = PoseType.STATIONARY_POSES + PoseType.UI_POSES,
                condition = { !it.isBattling },
                quirks = arrayOf(blink, quirk),
                idleAnimations = arrayOf(
                        singleBoneLook(pitchMultiplier = 0.9F, yawMultiplier = 0.9F),
                        bedrock("petilil", "ground_idle")
                ),
                transformedParts = arrayOf(
                        leaf_back.createTransformation().addRotationDegrees(ModelPartTransformation.X_AXIS, 6.5),
                        leaf_left.createTransformation().addRotationDegrees(ModelPartTransformation.X_AXIS, -19.5),
                        leaf_right.createTransformation().addRotationDegrees(ModelPartTransformation.X_AXIS, -19.5)
                )
        )

        walk = registerPose(
                poseName = "walk",
                poseTypes = PoseType.MOVING_POSES,
                quirks = arrayOf(blink, quirk),
                idleAnimations = arrayOf(
                        singleBoneLook(pitchMultiplier = 0.9F, yawMultiplier = 0.9F),
                        bedrock("petilil", "ground_walk")
                ),
                transformedParts = arrayOf(
                        leaf_back.createTransformation().addRotationDegrees(ModelPartTransformation.X_AXIS, 6.5),
                        leaf_left.createTransformation().addRotationDegrees(ModelPartTransformation.X_AXIS, -19.5),
                        leaf_right.createTransformation().addRotationDegrees(ModelPartTransformation.X_AXIS, -19.5)
                )
        )

        battleIdle = registerPose(
                poseName = "battle_idle",
                poseTypes = PoseType.STATIONARY_POSES,
                quirks = arrayOf(blink, quirk),
                idleAnimations = arrayOf(
                        singleBoneLook(pitchMultiplier = 0.9F, yawMultiplier = 0.9F),
                        bedrock("petilil", "battle_idle")
                ),
                transformedParts = arrayOf(
                        leaf_back.createTransformation().addRotationDegrees(ModelPartTransformation.X_AXIS, 6.5),
                        leaf_left.createTransformation().addRotationDegrees(ModelPartTransformation.X_AXIS, -19.5),
                        leaf_right.createTransformation().addRotationDegrees(ModelPartTransformation.X_AXIS, -19.5)
                )
        )
    }

    //override fun getFaintAnimation(
    //        pokemonEntity: PokemonEntity,
    //        state: PoseableEntityState<PokemonEntity>
    //) = if (state.isPosedIn(standing, walk, battleIdle)) bedrockStateful("petilil", "faint") else null

}