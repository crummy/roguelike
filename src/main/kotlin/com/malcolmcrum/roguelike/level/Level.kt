package com.malcolmcrum.roguelike.level

import com.malcolmcrum.roguelike.Point
import mu.KotlinLogging
import java.util.*

private fun Random.between(min: Int, max: Int) = this.nextInt(max - min) + min

private val log = KotlinLogging.logger {}

class Level(private val width: Int, private val height: Int) {
    private val tiles = Array(height * width, { Tile(true) })
    private val ROOM_MAX_SIZE = 10
    private val ROOM_MIN_SIZE = 6
    private val MAX_ROOMS = 30
    private val RNG = Random()
    var startingPoint: Point = Point(-1, -1)

    init {
        val rooms = ArrayList<Room>()
        for (r in 0..MAX_ROOMS) {
            val w = RNG.between(ROOM_MIN_SIZE, ROOM_MAX_SIZE)
            val h = RNG.between(ROOM_MIN_SIZE, ROOM_MAX_SIZE)
            val x = RNG.between(0, width - w - 1)
            val y = RNG.between(0, height - h - 1)
            val room = Room(x, y, w, h)
            if (rooms.none { other -> room.intersects(other) }) {
                carveRoom(room)
                if (r == 0) startingPoint = room.center()
                rooms.add(room)
            }
        }
        log.info { "Generated $width x $height level with ${rooms.size} rooms" }
    }

    private fun getTile(x: Int, y: Int): Tile {
        try {
            return tiles[y * height + x]
        } catch (e: ArrayIndexOutOfBoundsException) {
            throw ArrayIndexOutOfBoundsException("No tile at $x, $y")
        }
    }

    fun getTiles(): Sequence<PositionedTile> {
        return tiles.asSequence().mapIndexed { i, tile -> PositionedTile(i / height, i % height, tile) }
    }

    fun isTileFree(x: Int, y: Int) = !getTile(x, y).isBlocked

    private fun carveRoom(room: Room) {
        for (x in room.x1 + 1..room.x2) {
            for (y in room.y1 + 1..room.y2) {
                getTile(x, y).blockSight = false
                getTile(x, y).isBlocked = false
            }
        }
    }

    private fun createHorizontalTunnel(x1: Int, x2: Int, y: Int) {
        for (x in x1..x2) {
            getTile(x, y).blockSight = false
            getTile(x, y).isBlocked = false
        }
    }

    private fun createVerticalTunnel(y1: Int, y2: Int, x: Int) {
        for (y in y1..y2) {
            getTile(x, y).blockSight = false
            getTile(x, y).isBlocked = false
        }
    }

    data class PositionedTile(val x: Int, val y: Int, val tile: Tile)
}