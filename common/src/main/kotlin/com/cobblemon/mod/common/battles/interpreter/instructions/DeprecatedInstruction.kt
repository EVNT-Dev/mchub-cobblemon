/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.battles.interpreter.instructions

import com.cobblemon.mod.common.api.battles.interpreter.BattleMessage
import com.cobblemon.mod.common.api.battles.model.PokemonBattle
import com.cobblemon.mod.common.battles.dispatch.InterpreterInstruction

class DeprecatedInstruction(val message: BattleMessage, val function: (PokemonBattle, BattleMessage, MutableList<String>) -> Unit) : InterpreterInstruction {
    override val cause: InterpreterInstruction? = null
    override fun invoke(battle: PokemonBattle) {
        function(battle, message, mutableListOf())
    }
}