/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.net.serverhandling.storage

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.net.ServerNetworkPacketHandler
import com.cobblemon.mod.common.net.messages.server.SendOutPokemonPacket
import com.cobblemon.mod.common.pokemon.activestate.ActivePokemonState
import com.cobblemon.mod.common.pokemon.activestate.ShoulderedState
import com.cobblemon.mod.common.util.raycastSafeSendout
import net.minecraft.client.render.entity.model.RaftEntityModel
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.vehicle.BoatEntity
import net.minecraft.item.BoatItem
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.RaycastContext

object SendOutPokemonHandler : ServerNetworkPacketHandler<SendOutPokemonPacket> {

    const val SEND_OUT_DURATION = 1.5F

    override fun handle(packet: SendOutPokemonPacket, server: MinecraftServer, player: ServerPlayerEntity) {
        val slot = packet.slot.takeIf { it >= 0 } ?: return
        val party = Cobblemon.storage.getParty(player)
        val pokemon = party.get(slot) ?: return
        if (pokemon.isFainted()) {
            return
        }
        val state = pokemon.state
        if (state is ShoulderedState || state !is ActivePokemonState) {
            val position = player.raycastSafeSendout(pokemon, 12.0, 5.0, RaycastContext.FluidHandling.ANY)

            if (position != null) {
                // todo if send out position is over water then add a raft entity to stand on
                if (player.world.isWater(BlockPos(position.x.toInt(), position.y.toInt() - 1, position.z.toInt()))) {
                    val boatType = BoatEntity.Type.getType("bamboo")
                    // Create a new boat entity with the generic EntityType.BOAT
                    val raftEntity = BoatEntity(player.world, position.x, position.y, position.z)

                    raftEntity.variant = BoatEntity.Type.BAMBOO

                    raftEntity.setPosition(position.x, position.y, position.z) // Set the position of the boat

                    // Spawn the boat entity in the world
                    player.world.spawnEntity(raftEntity)
                }

                pokemon.sendOutWithAnimation(player, player.serverWorld, position)
            }
        } else {
            val entity = state.entity
            if (entity != null) {
                entity.recallWithAnimation()
            } else {
                pokemon.recall()
            }
        }
    }
}