/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.advancement.criterion

import com.cobblemon.mod.common.util.asIdentifierDefaultingNamespace
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.advancements.critereon.EntityPredicate
import net.minecraft.server.level.ServerPlayer
import net.minecraft.resources.ResourceLocation
import java.util.Optional

class PokemonInteractContext(val type: ResourceLocation, val item: ResourceLocation)

class PokemonInteractCriterion(
    playerCtx: Optional<ContextAwarePredicate>,
    val type: Optional<String>,
    val item: Optional<String>
): SimpleCriterionCondition<PokemonInteractContext>(playerCtx) {
    companion object {
        val CODEC: Codec<PokemonInteractCriterion> = RecordCodecBuilder.create { it.group(
            EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(PokemonInteractCriterion::playerCtx),
            Codec.STRING.optionalFieldOf("type").forGetter(PokemonInteractCriterion::type),
            Codec.STRING.optionalFieldOf("item").forGetter(PokemonInteractCriterion::item)
        ).apply(it, ::PokemonInteractCriterion) }
    }

    override fun matches(player: ServerPlayer, context: PokemonInteractContext): Boolean {
        val otherType = this.type.orElse("any")
        val otherItem = this.item.orElse("any")
        return (context.type == otherType.asIdentifierDefaultingNamespace() || otherType == "any") && (context.type == otherItem.asIdentifierDefaultingNamespace() || otherItem == "any")
    }
}