package com.malcolmcrum.roguelike.entity

import com.malcolmcrum.roguelike.Point

class Entity(var x: Int, var y: Int, val char: Char) {
    constructor(point: Point, char: Char) : this(point.x, point.y, char)
}