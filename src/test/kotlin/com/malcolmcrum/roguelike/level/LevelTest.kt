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
        val room = Room(0, 0, 9, 9)

        level.carveRoom(room)

        assertTrue(level.getTile(0, 0).isBlocked)
        assertFalse(level.getTile(1, 1).isBlocked)

        assertTrue(level.getTile(9, 9).isBlocked)
        assertFalse(level.getTile(8, 8).isBlocked)
    }

    @Test
    fun testVerticalTunnel() {
        val level = Level(10, 10)
        level.createVerticalTunnel(2, 6, 5)

        for (y in 2..6) {
            assertFalse(level.getTile(5, y).isBlocked)
        }
        assertTrue(level.getTile(1, 5).isBlocked)
        assertTrue(level.getTile(7, 5).isBlocked)
        assertTrue(level.getTile(3, 6).isBlocked)
        assertTrue(level.getTile(3, 4).isBlocked)
    }

    @Test
    fun testHorizontalTunnel() {
        val level = Level(10, 10)
        level.createHorizontalTunnel(2, 6, 5)

        for (x in 2..6) {
            assertFalse(level.getTile(x, 5).isBlocked)
        }
        assertTrue(level.getTile(5, 1).isBlocked)
        assertTrue(level.getTile(5, 7).isBlocked)
        assertTrue(level.getTile(6, 3).isBlocked)
        assertTrue(level.getTile(4, 3).isBlocked)
    }

    @Test
    fun testVerticalTunnelReverse() {
        val level = Level(10, 10)
        level.createVerticalTunnel(6, 2, 5)

        for (y in 2..6) {
            assertFalse(level.getTile(5, y).isBlocked)
        }
        assertTrue(level.getTile(1, 5).isBlocked)
        assertTrue(level.getTile(7, 5).isBlocked)
        assertTrue(level.getTile(3, 6).isBlocked)
        assertTrue(level.getTile(3, 4).isBlocked)
    }

}