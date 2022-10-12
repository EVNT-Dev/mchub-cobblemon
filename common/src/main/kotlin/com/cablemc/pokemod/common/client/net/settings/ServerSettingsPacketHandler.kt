/*
 * Copyright (C) 2022 Pokemon Cobbled Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cablemc.pokemod.common.client.net.settings

import com.cablemc.pokemod.common.PokemodNetwork
import com.cablemc.pokemod.common.client.net.ClientPacketHandler
import com.cablemc.pokemod.common.client.settings.ServerSettings
import com.cablemc.pokemod.common.net.messages.client.settings.ServerSettingsPacket

object ServerSettingsPacketHandler : ClientPacketHandler<ServerSettingsPacket> {

    override fun invokeOnClient(packet: ServerSettingsPacket, ctx: PokemodNetwork.NetworkContext) {
        ServerSettings.preventCompletePartyDeposit = packet.preventCompletePartyDeposit
    }

}