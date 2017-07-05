package com.malcolmcrum.roguelike.level

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LevelTest {
    @Test
    fun countTiles() {
        val level = Level(20, 20)

        assertEquals(level.getTiles().count(), 20*20)
    }

}