package com.malcolmcrum.roguelike.level

import mu.KotlinLogging
import java.util.*

private fun Random.between(min: Int, max: Int) = this.nextInt(max - min) + min

private val log = KotlinLogging.logger {}
private val ROOM_MAX_SIZE = 10
private val ROOM_MIN_SIZE = 6
private val MAX_ROOMS = 30
private val RNG = Random()

fun generate(level: Level) {
    val rooms = ArrayList<Room>()
    for (r in 0..MAX_ROOMS) {
        val w = RNG.between(ROOM_MIN_SIZE, ROOM_MAX_SIZE)
        val h = RNG.between(ROOM_MIN_SIZE, ROOM_MAX_SIZE)
        val x = RNG.between(0, level.width - w - 1)
        val y = RNG.between(0, level.height - h - 1)
        val room = Room(x, y, w, h)
        if (rooms.none { other -> room.intersects(other) }) {
            log.debug { "Carving room $room" }
            level.carveRoom(room)
            rooms.add(room)
        } else {
            log.debug { "Room $room intersects with another; not adding" }
        }
    }

    rooms.forEachIndexed({index, room ->
        if (index == 0) {
            level.startingPoint = room.center()
        } else {
            val previousCenter = rooms[index - 1].center()
            if (RNG.nextBoolean()) {
                level.createHorizontalTunnel(previousCenter.x, room.center().x, previousCenter.y)
                level.createVerticalTunnel(previousCenter.y, room.center().y, room.center().x)
            } else {
                level.createVerticalTunnel(previousCenter.y, room.center().y, room.center().x)
                level.createHorizontalTunnel(previousCenter.x, room.center().x, previousCenter.y)
            }
        }
    })
    log.info { "Generated ${level.width} x ${level.height} level with ${rooms.size} rooms" }
}