package com.cobblemon.mod.common.client.gui

import com.cobblemon.mod.common.client.gui.pokedex.widgets.EntriesScrollingWidget
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget

abstract class ScrollingWidget<T : AlwaysSelectedEntryListWidget.Entry<T>>(
    top : Int = 0,
    left: Int = 0,
    width : Int = 10,
    height: Int = 10,
    slotHeight : Int = 10,
    val scrollBarWidth : Int = 5
) : AlwaysSelectedEntryListWidget<T>(
    MinecraftClient.getInstance(),
    width, // Width
    height, // Height
    top, // Top
    top + height, // Bottom
    slotHeight // Slot Height
){
    init {
        setRenderHorizontalShadows(false)
        setRenderBackground(false)
        setRenderSelection(false)

        updateSize(width, height, top, top + height)
        setLeft(left)
    }

    override fun updateSize(width: Int, height: Int, top: Int, bottom: Int) {
        this.width = width
        this.height = height
        this.top = top
        this.bottom = bottom
        this.right = left + width
    }

    fun setLeft(left: Int){
        this.left = left
        this.right = left + width
    }

    override fun renderList(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        val i = this.rowLeft
        val j = this.rowWidth
        val k = this.itemHeight
        val l = this.entryCount

        for (m in 0 until l) {
            val n = this.getRowTop(m)
            val o = this.getRowBottom(m)
            if (o >= this.top && n <= this.bottom) {
                this.renderEntry(context!!, mouseX, mouseY, delta, m, i, n, j, k)
            }
        }
    }

    override fun renderEntry(
        context: DrawContext,
        mouseX: Int,
        mouseY: Int,
        delta: Float,
        index: Int,
        x: Int,
        y: Int,
        entryWidth: Int,
        entryHeight: Int
    ) {
        val entry =  this.getEntry(index)
        entry.render(
            context, index, y, x, entryWidth, entryHeight, mouseX, mouseY,
            hoveredEntry == entry, delta
        )
    }

    override fun getRowLeft(): Int {
        return this.left
    }

    override fun getRowRight(): Int {
        return this.rowLeft + this.rowWidth
    }

    override fun getRowWidth(): Int {
        return this.width - scrollBarWidth
    }

    override fun getRowTop(index: Int): Int {
        return this.top - scrollAmount.toInt() + (index * this.itemHeight)
    }

    override fun getRowBottom(index: Int): Int {
        return this.getRowTop(index) + this.itemHeight
    }

    override fun getScrollbarPositionX() = this.left + this.width - this.scrollBarWidth

    abstract class Slot<T : Slot<T>>(): Entry<T>() {
        // Override render to show each individual element
    }
}