package com.malcolmcrum.roguelike.level

import com.malcolmcrum.roguelike.Point

class Room(x: Int, y: Int, w: Int, h: Int) {
    val x1 = x
    val y1 = y
    val x2 = x + w
    val y2 = y + h

    init {
        check(x1 < x2)
        check(y1 < y2)
    }

    fun center(): Point = Point((x1 + x2)/2, (y1 + y2)/2)

    fun intersects(other: Room) = (x1 <= other.x2 && x2 >= other.x1) && (y1 <= other.y2 && y2 >= other.y1)

    override fun toString() = "($x1, $y1 -> $x2, $y2)"
}