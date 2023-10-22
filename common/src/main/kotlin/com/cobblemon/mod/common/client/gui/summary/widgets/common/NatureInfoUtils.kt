package com.cobblemon.mod.common.client.gui.summary.widgets.common

import com.cobblemon.mod.common.CobblemonItems
import com.cobblemon.mod.common.api.text.italicise
import com.cobblemon.mod.common.api.text.onHover
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.util.asTranslated
import net.minecraft.text.MutableText


/**
 * Italicizes the nature text and makes it include the name of the MintItem used to mint the Pokémon's nature
 */
fun reformatNatureTextIfMinted(pokemon: Pokemon): MutableText {
    var natureText = pokemon.nature.displayName.asTranslated()
    if (pokemon.mintedNature != null) {
        CobblemonItems.mints[pokemon.mintedNature!!.displayName]?.let { mint ->
            natureText = natureText.italicise().onHover(mint.name)
        }
    }
    return natureText
}