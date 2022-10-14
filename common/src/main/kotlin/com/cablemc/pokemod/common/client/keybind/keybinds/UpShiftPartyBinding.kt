/*
 * Copyright (C) 2022 Pokemon Cobbled Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cablemc.pokemod.common.client.keybind.keybinds

import com.cablemc.pokemod.common.client.PokemodClient
import com.cablemc.pokemod.common.client.keybind.KeybindCategories
import com.cablemc.pokemod.common.client.keybind.PokemodKeyBinding
import net.minecraft.client.util.InputUtil

object UpShiftPartyBinding : PokemodKeyBinding(
    "key.pokemod.upshiftparty",
    InputUtil.Type.KEYSYM,
    InputUtil.GLFW_KEY_UP,
    KeybindCategories.POKEMOD_CATEGORY
) {
    override fun onPress() {
        PokemodClient.storage.shiftSelected(false)
    }
}