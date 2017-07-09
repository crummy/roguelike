package com.malcolmcrum.roguelike.level

import com.malcolmcrum.roguelike.Point

class Level(val width: Int, val height: Int) {
    private val tiles = Array(height * width, { Tile(true) })
    var startingPoint: Point = Point(-1, -1)

    fun getTile(x: Int, y: Int): Tile {
        try {
            return tiles[y * height + x]
        } catch (e: ArrayIndexOutOfBoundsException) {
            throw ArrayIndexOutOfBoundsException("No tile at $x, $y")
        }
    }

    fun isTileFree(x: Int, y: Int) = !getTile(x, y).isBlocked
}