package com.cobblemon.mod.common.entity.pokemon.effects

import com.cobblemon.mod.common.api.entity.pokemon.MocKEffect
import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.api.pokemon.PokemonPropertyExtractor
import com.cobblemon.mod.common.api.scheduling.afterOnServer
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.util.DataKeys
import net.minecraft.nbt.NbtCompound
import java.util.concurrent.CompletableFuture

/**
 * A [BattleEffect] that alters a [PokemonEntity] to look like a target [Pokemon].
 *
 * @param mimic The [Pokemon] to copy.
 * @author Segfault Guy
 * @since March 5th, 2024
 */
class TransformEffect(
    override var mock: PokemonProperties = PokemonProperties(),
    override var scale: Float = 1.0F
) : BattleEffect(), MocKEffect {

    constructor(mimic: Pokemon) : this(
        mock = mimic.createPokemonProperties(PokemonPropertyExtractor.TRANSFORM),
        scale = mimic.form.baseScale * mimic.scaleModifier
    )

    override fun apply(entity: PokemonEntity, future: CompletableFuture<PokemonEntity>) {
        entity.effects.mockEffect = this
        afterOnServer(seconds = 1.0F) {
            entity.cry()
            future.complete(entity)
        }
    }

    override fun revert(entity: PokemonEntity, future: CompletableFuture<PokemonEntity>) {
        entity.effects.mockEffect = null
        entity.cry()
        future.complete(entity)
    }

    override fun saveToNbt(): NbtCompound {
        val nbt = NbtCompound()
        nbt.putString(DataKeys.ENTITY_EFFECT_MOCK, ID)
        nbt.put(DataKeys.POKEMON_ENTITY_MOCK, mock.saveToNBT())
        nbt.putFloat(DataKeys.POKEMON_ENTITY_SCALE, scale)
        return nbt
    }

    override fun loadFromNBT(nbt: NbtCompound) {
        if (nbt.contains(DataKeys.POKEMON_ENTITY_MOCK)) this.mock = PokemonProperties().loadFromNBT(nbt.getCompound(DataKeys.POKEMON_ENTITY_MOCK))
        if (nbt.contains(DataKeys.POKEMON_ENTITY_SCALE)) this.scale = nbt.getFloat(DataKeys.POKEMON_ENTITY_SCALE)
    }

    companion object {
        val ID = "TRANSFORM "
    }
}