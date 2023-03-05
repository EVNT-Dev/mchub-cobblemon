/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.net.messages.client.trade

import com.cobblemon.mod.common.api.net.NetworkPacket
import com.cobblemon.mod.common.util.cobblemonResource
import net.minecraft.network.PacketByteBuf

/**
 * Packet sent to the client when the open trade has been completed.
 *
 * @author Hiroku
 * @since March 5th, 2023
 */
class TradeCompletedPacket : NetworkPacket<TradeCompletedPacket> {
    companion object {
        private val ID = cobblemonResource("trade_completed")
        fun decode(buffer: PacketByteBuf) = TradeCompletedPacket()
    }

    override val id = ID

    override fun encode(buffer: PacketByteBuf) {}
}