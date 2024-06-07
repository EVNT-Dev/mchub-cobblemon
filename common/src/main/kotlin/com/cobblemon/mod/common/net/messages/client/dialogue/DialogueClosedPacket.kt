/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.net.messages.client.dialogue

import com.cobblemon.mod.common.api.net.NetworkPacket
import com.cobblemon.mod.common.util.cobblemonResource
import com.cobblemon.mod.common.util.writeNullable
import com.cobblemon.mod.common.util.writeUuid
import io.netty.buffer.ByteBuf
import net.minecraft.network.PacketByteBuf
import java.util.UUID

/**
 * Packet sent to the client to close the active dialogue.
 *
 * @param dialogueId The ID of the dialogue to close. If null, closes the active dialogue.
 * @author Hiroku
 * @since December 27th, 2023
 */
class DialogueClosedPacket(val dialogueId: UUID? = null) : NetworkPacket<DialogueClosedPacket> {
    companion object {
        val ID = cobblemonResource("dialogue_closed")
        fun decode(buffer: PacketByteBuf) = DialogueClosedPacket(buffer.readNullable { it.readUuid() })
    }

    override val id = ID
    override fun encode(buffer: ByteBuf) {
        buffer.writeNullable(dialogueId) { buff, value -> buff.writeUuid(value) }
    }
}