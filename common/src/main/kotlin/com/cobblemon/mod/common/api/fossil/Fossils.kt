package com.cobblemon.mod.common.api.fossil

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.conditional.RegistryLikeCondition
import com.cobblemon.mod.common.api.data.JsonDataRegistry
import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.api.reactive.SimpleObservable
import com.cobblemon.mod.common.pokemon.evolution.adapters.NbtItemPredicateAdapter
import com.cobblemon.mod.common.pokemon.evolution.predicate.NbtItemPredicate
import com.cobblemon.mod.common.util.adapters.IdentifierAdapter
import com.cobblemon.mod.common.util.adapters.ItemLikeConditionAdapter
import com.cobblemon.mod.common.util.adapters.pokemonPropertiesShortAdapter
import com.cobblemon.mod.common.util.cobblemonResource
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.resource.ResourceType
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier

object Fossils: JsonDataRegistry<Fossil> {

    override val id: Identifier = cobblemonResource("fossils")
    override val type: ResourceType = ResourceType.SERVER_DATA
    override val observable = SimpleObservable<Fossils>()

    override val gson = GsonBuilder()
        .disableHtmlEscaping()
        .setPrettyPrinting()
        .registerTypeAdapter(Identifier::class.java, IdentifierAdapter)
        .registerTypeAdapter(PokemonProperties::class.java, pokemonPropertiesShortAdapter)
        .registerTypeAdapter(TypeToken.getParameterized(RegistryLikeCondition::class.java, Item::class.java).type, ItemLikeConditionAdapter)
        .registerTypeAdapter(NbtItemPredicate::class.java, NbtItemPredicateAdapter)
        .create()

    override val typeToken: TypeToken<Fossil> = TypeToken.get(Fossil::class.java)
    override val resourcePath: String = "fossils"

    private val fossils = hashMapOf<Identifier, Fossil>()

    override fun reload(data: Map<Identifier, Fossil>) {
        this.fossils.clear()
        data.forEach { (identifier, fossil) ->
            try {
                fossil.identifier = identifier
                this.fossils[identifier] = fossil
            } catch (e: Exception) {
                Cobblemon.LOGGER.error("Skipped loading the {} fossil", identifier, e)
            }
        }
        Cobblemon.LOGGER.info("Loaded {} fossils", this.fossils.size)
        this.observable.emit(this)
    }

    override fun sync(player: ServerPlayerEntity) {
        // TODO: Implement this
    }

    /**
     * Gets all loaded [Fossil]s.
     */
    fun all() = this.fossils.values.toList()

    /**
     * Gets a [Fossil] by its [Identifier].
     * @param identifier The identifier of the fossil.
     * @return The [Fossil] if loaded, otherwise null.
     */
    fun getByIdentifier(identifier: Identifier): Fossil? = this.fossils[identifier]

    /**
     * Looks for a [Fossil] that matches a [ItemStack].
     * @param fossilStacks The fossil [ItemStack]'s.
     * @return The [Fossil] if found, otherwise null.
     */
    fun getFossilByItemStacks(fossilStacks: List<ItemStack>): Fossil? {
        return this.all().firstOrNull { it.matchesIngredients(fossilStacks) }
    }

    /**
     * Checks if a [ItemStack] is a fossil ingredient.
     * @param itemStack The ingredient [ItemStack].
     * @return true if it's a fossil ingredient, otherwise false.
     */
    fun isFossilIngredient(itemStack: ItemStack): Boolean {
        return this.all().any { it.isIngredient(itemStack) }
    }

}