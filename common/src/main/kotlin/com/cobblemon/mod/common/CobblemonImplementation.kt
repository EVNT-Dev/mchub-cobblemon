/*
 * Copyright (C) 2022 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common

import com.cobblemon.mod.common.api.net.NetworkPacket
import net.minecraft.server.network.ServerPlayerEntity

interface CobblemonImplementation {

    fun isModInstalled(id: String): Boolean

    fun registerPermissionValidator()

    fun registerItems()

    fun registerBlocks()

}

interface NetworkDelegate {

    fun sendPacketToPlayer(player: ServerPlayerEntity, packet: NetworkPacket)
    fun sendPacketToServer(packet: NetworkPacket)
    fun <T : NetworkPacket> buildMessage(
        packetClass: Class<T>,
        toServer: Boolean
    ): CobblemonNetwork.PreparedMessage<T>
}