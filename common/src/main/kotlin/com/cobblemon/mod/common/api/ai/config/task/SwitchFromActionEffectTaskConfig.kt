/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.api.ai.config.task

import com.cobblemon.mod.common.api.ai.BrainConfigurationContext
import com.cobblemon.mod.common.entity.npc.ai.SwitchFromActionEffectTask
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.schedule.Activity

class SwitchFromActionEffectTaskConfig : SingleTaskConfig {
    val activity = Activity.IDLE
    override fun createTask(
        entity: LivingEntity,
        brainConfigurationContext: BrainConfigurationContext
    ) = SwitchFromActionEffectTask.create(activity)
}