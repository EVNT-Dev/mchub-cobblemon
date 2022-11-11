/*
 * Copyright (C) 2022 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.net.messages.client.storage.pc

import com.cobblemon.mod.common.api.net.NetworkPacket
import com.cobblemon.mod.common.api.storage.pc.PCBox
import com.cobblemon.mod.common.net.IntSize
import com.cobblemon.mod.common.net.messages.PokemonDTO
import com.cobblemon.mod.common.util.readMapK
import com.cobblemon.mod.common.util.readSizedInt
import com.cobblemon.mod.common.util.writeMapK
import com.cobblemon.mod.common.util.writeSizedInt
import java.util.UUID
import net.minecraft.network.PacketByteBuf

/**
 * Sets an entire box of Pokémon in the client side representation of a PC. This is used
 * during the initial sending of a PC's contents. It's better than sending hundreds of packets
 * for a full PC - this way it's one large-ish packet per box.
 *
 * Handled by [com.cobblemon.mod.common.client.net.storage.pc.SetPCBoxPokemonHandler].
 *
 * @author Hiroku
 * @since June 18th, 2022
 */
class SetPCBoxPokemonPacket() : NetworkPacket {
    lateinit var storeID: UUID
    var boxNumber = 0
    var pokemon = mapOf<Int, PokemonDTO>()

    constructor(box: PCBox): this() {
        this.storeID = box.pc.uuid
        this.boxNumber = box.boxNumber
        this.pokemon = box.getNonEmptySlots().map { it.key to PokemonDTO(it.value, toClient = true) }.toMap()
    }

    override fun encode(buffer: PacketByteBuf) {
        buffer.writeUuid(storeID)
        buffer.writeSizedInt(IntSize.U_BYTE, boxNumber)
        buffer.writeMapK(map = pokemon) { (slot, pokemon) ->
            buffer.writeSizedInt(IntSize.U_BYTE, slot)
            pokemon.encode(buffer)
        }
    }

    override fun decode(buffer: PacketByteBuf) {
        storeID = buffer.readUuid()
        boxNumber = buffer.readSizedInt(IntSize.U_BYTE)
        val pokemonMap = mutableMapOf<Int, PokemonDTO>()
        buffer.readMapK(map = pokemonMap) { buffer.readSizedInt(IntSize.U_BYTE) to PokemonDTO().also { decode(buffer) } }
        pokemon = pokemonMap
    }
}