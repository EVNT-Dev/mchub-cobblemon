package com.cobblemon.mod.common.api.events.pokemon

import com.cobblemon.mod.common.api.events.Cancelable
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.pokemon.Pokemon
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.Vec3d

/**
 * Event fired when a party [Pokemon] is sent out. Cancelling this event prevents a corresponding
 * [PokemonEntity] from being instantiated and spawned into the world.
 *
 * @author Segfault Guy
 * @since March 25th, 2023
 */
data class PokemonSentPreEvent(
    val pokemon: Pokemon,
    val level: ServerWorld,
    var position: Vec3d
) : Cancelable()

/**
 * Event fired after a [PokemonEntity] is spawned from a player's party and after its animations are finished.
 * Only fired for party [Pokemon] sent out with animations.
 *
 * @author Segfault Guy
 * @since March 25th, 2023
 */
data class PokemonSentPostEvent(
    val pokemon: Pokemon,
    val pokemonEntity: PokemonEntity
)