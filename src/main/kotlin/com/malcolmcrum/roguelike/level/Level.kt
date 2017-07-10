package com.malcolmcrum.roguelike.level

import com.malcolmcrum.roguelike.Point

class Level(val width: Int, val height: Int) {
    private val tiles = Array(height * width, { Tile(true) })
    var startingPoint: Point = Point(-1, -1)

    fun getTile(x: Int, y: Int): Tile {
        try {
            return tiles[y * width + x]
        } catch (e: ArrayIndexOutOfBoundsException) {
            throw ArrayIndexOutOfBoundsException("No tile at $x, $y")
        }
    }

    fun carveRoom(room: Room) {
        for (x in (room.x1 + 1)..(room.x2 - 1)) {
            for (y in (room.y1 + 1)..(room.y2 - 1)) {
                getTile(x, y).blockSight = false
                getTile(x, y).isBlocked = false
            }
        }
    }

    fun createHorizontalTunnel(x1: Int, x2: Int, y: Int) {
        for (x in x1..x2) {
            getTile(x, y).blockSight = false
            getTile(x, y).isBlocked = false
        }
    }

    fun createVerticalTunnel(y1: Int, y2: Int, x: Int) {
        for (y in y1..y2) {
            getTile(x, y).blockSight = false
            getTile(x, y).isBlocked = false
        }
    }

    fun isTileFree(x: Int, y: Int) = !getTile(x, y).isBlocked
}