package com.malcolmcrum.roguelike.level

import mu.KotlinLogging
import java.util.*

private val log = KotlinLogging.logger {}

private fun Random.between(min: Int, max: Int) = this.nextInt(max - min) + min

class Generator(private val level: Level) {
    private val ROOM_MAX_SIZE = 10
    private val ROOM_MIN_SIZE = 6
    private val MAX_ROOMS = 30
    private val RNG = Random()

    init {
        log.info { "Generating ${level.width} x ${level.height} level" }
        val rooms = ArrayList<Room>()
        for (r in 0..MAX_ROOMS) {
            val w = RNG.between(ROOM_MIN_SIZE, ROOM_MAX_SIZE)
            val h = RNG.between(ROOM_MIN_SIZE, ROOM_MAX_SIZE)
            val x = RNG.between(0, level.width - w - 1)
            val y = RNG.between(0, level.height - h - 1)
            log.info { "$w $h $x $y"}
            val room = Room(x, y, w, h)
            if (rooms.none { other -> room.intersects(other) }) {
                carveRoom(room)
                if (r == 0) level.startingPoint = room.center()
                rooms.add(room)
            } else {
                log.info { "Room intercepts other; not adding" }
            }
        }
        log.info { "Generated ${level.width} x ${level.height} level with ${rooms.size} rooms" }
    }


    private fun carveRoom(room: Room) {
        log.info { "Carving room $room" }
        for (x in (room.x1 + 1)..(room.x2 - 1)) {
            for (y in (room.y1 + 1)..(room.y2 - 1)) {
                log.info { "Carving block $x, $y"}
                level.getTile(x, y).blockSight = false
                level.getTile(x, y).isBlocked = false
            }
        }
    }

    private fun createHorizontalTunnel(x1: Int, x2: Int, y: Int) {
        for (x in x1..x2) {
            level.getTile(x, y).blockSight = false
            level.getTile(x, y).isBlocked = false
        }
    }

    private fun createVerticalTunnel(y1: Int, y2: Int, x: Int) {
        for (y in y1..y2) {
            level.getTile(x, y).blockSight = false
            level.getTile(x, y).isBlocked = false
        }
    }
}