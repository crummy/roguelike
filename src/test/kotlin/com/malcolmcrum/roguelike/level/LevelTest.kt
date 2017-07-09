package com.malcolmcrum.roguelike.level

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class LevelTest {
    @Test
    fun testLevelDimensions() {
        val level = Level(20, 10)

        assertEquals(10, level.height)
        assertEquals(20, level.width)
    }

    @Test
    fun testLevelCoordinates() {
        val level = Level(20, 10)

        // just checking for no out of bounds errors
        level.getTile(0, 0)
        level.getTile(19, 9)
    }

    @Test
    fun testOutOfBounds() {
        val level = Level(20, 10)

        assertThrows(ArrayIndexOutOfBoundsException::class.java, { level.getTile(20, 10) })
    }

    @Test
    fun testCarving() {
        val level = Level(10, 10)
        val room = Room(0, 0, 10, 10)

        level.carveRoom(room)

        assertTrue(level.getTile(0, 0).isBlocked)
        assertFalse(level.getTile(1, 1).isBlocked)

        assertTrue(level.getTile(9, 9).isBlocked)
        assertFalse(level.getTile(8, 8).isBlocked)
    }

}