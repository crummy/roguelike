package com.malcolmcrum.roguelike.level

import com.malcolmcrum.roguelike.Point
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class RoomTest {
    @Test
    fun testIntersects() {
        val a = Room(0, 0, 10, 10)
        val b = Room(0, 0, 1, 1)

        assertTrue(a.intersects(b))
    }

    @Test
    fun testSingleOverlap() {
        val a = Room(0, 0, 1, 1)
        val b = Room(1, 1, 1, 1)

        assertTrue(a.intersects(b))
    }

    @Test
    fun testNoOverlap() {
        val a = Room(0, 0, 1, 1)
        val b = Room(0, 2, 1, 1)

        assertFalse(a.intersects(b))
    }

    @Test
    fun testTransitiveProperty() {
        val a = Room(0, 0, 10, 10)
        val b = Room(0, 0, 1, 1)

        assertTrue(a.intersects(b))
        assertTrue(b.intersects(a))
    }

    @Test
    fun testTransitivePropertyWithNoOverlap() {
        val a = Room(0, 0, 1, 1)
        val b = Room(0, 2, 1, 1)

        assertFalse(a.intersects(b))
        assertFalse(b.intersects(a))
    }

    @Test
    fun testCenter() {
        val center = Room(0, 0, 10, 10).center()

        assertEquals(center, Point(5, 5))
    }

    @Test
    fun testCenterNonZero() {
        val center = Room(10, 10, 10, 10).center()

        assertEquals(center, Point(15, 15))
    }

    @Test
    fun testDimensions() {
        val room = Room(10, 10, 10, 5)

        assertEquals(10, room.x1)
        assertEquals(10, room.y1)
        assertEquals(20, room.x2)
        assertEquals(15, room.y2)
    }

}