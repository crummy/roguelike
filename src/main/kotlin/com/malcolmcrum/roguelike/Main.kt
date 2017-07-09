package com.malcolmcrum.roguelike

import asciiPanel.AsciiPanel
import com.malcolmcrum.roguelike.entity.Entity
import com.malcolmcrum.roguelike.level.Generator
import com.malcolmcrum.roguelike.level.Level
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
    private val level = Level(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)


    init {
        add(terminal)
        Generator(level)
        player = Entity(level.startingPoint, '@')
        npc = Entity(SCREEN_WIDTH/2 + 5, SCREEN_HEIGHT/2, 'N')
        entities.add(player)
        entities.add(npc)
        addKeyListener(this)
        pack()
        repaint()
    }

    override fun repaint(time: Long, X: Int, Y: Int, width: Int, height: Int) {
        terminal.clear()
        for (x in 0..(level.width - 1)) {
            for (y in 0..(level.height - 1)) {
                log.info { "Getting tile $x, $y" }
                val tile = level.getTile(x, y)
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
        var x = player.x
        var y = player.y
        when (e?.keyCode) {
            KeyEvent.VK_UP -> y--
            KeyEvent.VK_DOWN -> y++
            KeyEvent.VK_LEFT -> x--
            KeyEvent.VK_RIGHT -> x++
        }
        if (level.isTileFree(x, y)) {
            log.debug { "Moved player to $x, $y" }
            player.x = x
            player.y = y
        } else {
            log.debug { "Cannot move to position $x, $y - tile is blocked" }
        }
        repaint()
    }

    override fun keyReleased(e: KeyEvent?) {}

    override fun keyTyped(e: KeyEvent?) {}

}