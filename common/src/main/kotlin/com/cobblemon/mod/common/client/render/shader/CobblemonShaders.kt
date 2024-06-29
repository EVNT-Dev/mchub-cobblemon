/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.shader

import com.cobblemon.mod.common.util.ShaderRegistryData
import com.cobblemon.mod.common.util.cobblemonResource
import net.minecraft.client.renderer.ShaderInstance
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import net.minecraft.resource.ResourceFactory
import net.minecraft.resource.ResourceManager
import java.util.function.Consumer

object CobblemonShaders {
    val SHADERS_TO_REGISTER = mutableListOf<Pair<(ResourceFactory) -> ShaderRegistryData, Consumer<ShaderInstance>>>()
    lateinit var PARTICLE_BLEND: ShaderInstance
    // This is Material.ALPHA. Weird internal name for "alphatest" shader.
    lateinit var PARTICLE_CUTOUT: ShaderInstance

    private fun registerShader(shader: (ResourceFactory) -> ShaderRegistryData, callback: Consumer<ShaderInstance>){
        SHADERS_TO_REGISTER.add(Pair(shader, callback))
    }
    fun init(){
        registerShader({rm: ResourceFactory -> ShaderRegistryData(rm, cobblemonResource("particle_add"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT) }) {
            PARTICLE_BLEND = it
        }
        registerShader({rm: ResourceFactory -> ShaderRegistryData(rm, cobblemonResource("particle_cutout"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT) }) {
            PARTICLE_CUTOUT = it
        }
    }
}