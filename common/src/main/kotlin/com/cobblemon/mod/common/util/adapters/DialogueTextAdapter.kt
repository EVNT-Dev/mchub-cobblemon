/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.util.adapters

import com.cobblemon.mod.common.api.dialogue.DialogueText
import com.cobblemon.mod.common.api.dialogue.ExpressionLikeDialogueText
import com.cobblemon.mod.common.api.dialogue.WrappedDialogueText
import com.cobblemon.mod.common.api.text.text
import com.cobblemon.mod.common.util.asExpressionLike
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

object DialogueTextAdapter : JsonDeserializer<DialogueText> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): DialogueText {
        return if (json.isJsonPrimitive) {
            WrappedDialogueText(json.asString.text())
        } else if (json.isJsonArray) {
            ExpressionLikeDialogueText(json.asJsonArray.map { it.asString }.asExpressionLike())
        } else {
            val obj = json.asJsonObject
            val typeId = obj.get("type").asString
            val clazz = DialogueText.types[typeId] ?: throw JsonParseException("Unknown dialogue text type $typeId")
            context.deserialize(json, clazz)
        }
    }
}