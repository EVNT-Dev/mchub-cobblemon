package com.cobblemon.mod.common.battles.interpreter.instructions

import com.cobblemon.mod.common.api.battles.interpreter.BattleMessage
import com.cobblemon.mod.common.api.battles.model.PokemonBattle
import com.cobblemon.mod.common.api.text.red
import com.cobblemon.mod.common.battles.dispatch.InterpreterInstruction
import com.cobblemon.mod.common.util.battleLang

/**
 * Format: |-immune|POKEMON
 *
 * POKEMON was immune to a move.
 * @author Hiroku
 * @since October 3, 2022
 */
class ImmuneInstruction(val message: BattleMessage): InterpreterInstruction {

    override fun invoke(battle: PokemonBattle) {
        battle.dispatchWaiting {
            val pokemon = message.battlePokemon(0, battle) ?: return@dispatchWaiting
            val name = pokemon.getName()
            battle.broadcastChatMessage(battleLang("immune", name).red())
            battle.minorBattleActions[pokemon.uuid] = message
        }
    }
}