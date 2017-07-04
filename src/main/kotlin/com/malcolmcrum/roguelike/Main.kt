package com.malcolmcrum.roguelike

import asciiPanel.AsciiPanel
import com.malcolmcrum.roguelike.entity.Entity
import com.malcolmcrum.roguelike.tile.Tile
import mu.KotlinLogging
import java.awt.Color
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame

fun main(args: Array<String>) {
    log.info { "Starting Roguelike..." }
    val main = Main()
    main.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    main.isVisible = true
    log.info { "Roguelike initialized." }
}

private val log = KotlinLogging.logger {}

class Main : JFrame(), KeyListener {

    private val SCREEN_WIDTH = 80
    private val SCREEN_HEIGHT = 50
    private val VIEWPORT_WIDTH = 80
    private val VIEWPORT_HEIGHT = 45
    private val terminal = AsciiPanel(SCREEN_WIDTH, SCREEN_HEIGHT)
    private val player: Entity
    private val npc: Entity
    private val entities = HashSet<Entity>()
    private val map: Array<Array<Tile>>

    inline fun <reified T> matrix2d(height: Int, width: Int, init: (Int, Int) -> Array<T>) = Array(height, { row -> init(row, width) })

    init {
        add(terminal)
        player = Entity(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, '@')
        npc = Entity(SCREEN_WIDTH/2 + 5, SCREEN_HEIGHT/2, 'N')
        entities.add(player)
        entities.add(npc)
        map = makeMap()
        addKeyListener(this)
        pack()
        repaint()
    }

    private fun makeMap(): Array<Array<Tile>> {
        val map = matrix2d(VIEWPORT_HEIGHT, VIEWPORT_WIDTH,  { _, width: Int -> Array(width) { _ -> Tile(false) } })
        map[20][20].blocked = true
        map[20][20].blockSight = true
        map[20][25].blocked = true
        map[20][25].blockSight = true
        return map
    }

    override fun repaint(time: Long, X: Int, Y: Int, width: Int, height: Int) {
        terminal.clear()
        for (y in 0..map.size - 1) {
            for (x in 0..map[0].size - 1) {
                val tile = map[y][x]
                if (tile.blockSight) {
                    terminal.write('#', x, y, Color.WHITE)
                } else {
                    terminal.write('.', x, y, Color.DARK_GRAY)
                }
            }
        }
        for (entity in entities) {
            terminal.write(entity.char, entity.x, entity.y)
        }
        terminal.repaint()
    }

    override fun keyPressed(e: KeyEvent?) {
        log.debug { "Received key pressed: ${e?.keyCode}" }
        var x = player.x
        var y = player.y
        when (e?.keyCode) {
            KeyEvent.VK_UP -> y--
            KeyEvent.VK_DOWN -> y++
            KeyEvent.VK_LEFT -> x--
            KeyEvent.VK_RIGHT -> x++
        }
        if (isTileFree(x, y)) {
            player.x = x
            player.y = y
        }
        repaint()
    }

    private fun isTileFree(x: Int, y: Int): Boolean {
        return map[y][x].blocked == false
    }

    override fun keyReleased(e: KeyEvent?) {}

    override fun keyTyped(e: KeyEvent?) {}

}