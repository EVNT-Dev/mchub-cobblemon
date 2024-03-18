package com.cobblemon.mod.common.client.gui.pokedex.widgets

import com.cobblemon.mod.common.api.gui.blitk
import com.cobblemon.mod.common.api.text.text
import com.cobblemon.mod.common.client.gui.ScrollingWidget
import com.cobblemon.mod.common.client.gui.pokedex.PokedexGUIConstants
import com.cobblemon.mod.common.client.render.drawScaledText
import com.cobblemon.mod.common.pokemon.FormData
import com.cobblemon.mod.common.util.cobblemonResource
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text
import kotlin.math.max

class FormsWidget (val pX: Int, val pY: Int, val setFormData : (FormData) -> (Unit)): ScrollingWidget<FormsWidget.FormSlot>(
        left = pX,
        top = pY,
        width = PokedexGUIConstants.POKEMON_FORMS_WIDTH,
        height = PokedexGUIConstants.POKEMON_FORMS_HEIGHT,
        slotHeight = 15
) {

    override fun addEntry(entry: FormSlot): Int {
        return super.addEntry(entry)
    }

    fun setForms(forms: Collection<FormData>){
        clearEntries()
        forms.forEach {
            addEntry(
                FormSlot(it, setFormData)
            )
        }
    }

    override fun getScrollbarPositionX(): Int {
        return left + width - scrollBarWidth
    }

    override fun getMaxScroll() = max(this.height.toDouble(), (this.maxPosition - (this.bottom - this.top - 4)).toDouble()).toInt()

    class FormSlot(val form : FormData, val setFormData: (FormData) -> Unit) : Slot<FormSlot>() {

        companion object {
            private val scrollSlotResource = cobblemonResource("textures/gui/pokedex/scroll_slot_base.png")// Render Scroll Slot Background
        }

        override fun render(
            context: DrawContext,
            index: Int,
            y: Int,
            x: Int,
            entryWidth: Int,
            entryHeight: Int,
            mouseX: Int,
            mouseY: Int,
            hovered: Boolean,
            tickDelta: Float
        ) {

            val textScale = 1F
            blitk(
                matrixStack = context.matrices,
                texture = scrollSlotResource,
                x = x,
                y = y,
                width = entryWidth,
                height = entryHeight
            )

            drawScaledText(
                context = context,
                text = form.name.text(),
                x = x + entryWidth/2,
                y = y + entryHeight/2 - 5,
                scale = textScale,
                centered = true
            )
        }

        override fun getNarration(): Text {
            return Text.of(form.name)
        }

        override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
            setFormData.invoke(form)
            return true
        }
    }
}