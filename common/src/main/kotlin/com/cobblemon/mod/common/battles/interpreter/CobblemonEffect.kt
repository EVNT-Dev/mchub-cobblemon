/*
 * Copyright (C) 2022 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.battles.interpreter

import com.cobblemon.mod.common.api.battles.interpreter.Effect

internal data class CobblemonEffect(
    override val id: String,
    override val type: Effect.Type,
    override val rawData: String
) : Effect