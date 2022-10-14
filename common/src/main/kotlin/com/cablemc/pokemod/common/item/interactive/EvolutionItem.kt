/*
 * Copyright (C) 2022 Pokemon Cobbled Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cablemc.pokemod.common.item.interactive

import com.cablemc.pokemod.common.item.PokemodItem
import com.cablemc.pokemod.common.item.PokemodItemGroups

open class EvolutionItem(properties: Settings = Settings().group(PokemodItemGroups.EVOLUTION_ITEM_GROUP)) : PokemodItem(properties)