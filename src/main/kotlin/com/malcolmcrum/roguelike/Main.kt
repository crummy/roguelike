package com.malcolmcrum.roguelike

import asciiPanel.AsciiPanel
import com.malcolmcrum.roguelike.entity.Entity
import mu.KotlinLogging
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

    private val terminal = AsciiPanel()
    private val player: Entity
    private val npc: Entity
    private val entities = HashSet<Entity>()


    init {
        add(terminal)
        player = Entity(terminal.widthInCharacters/2, terminal.heightInCharacters/2, '@')
        npc = Entity(terminal.widthInCharacters/2 + 5, terminal.heightInCharacters/2, 'N')
        entities.add(player)
        entities.add(npc)
        pack()
        addKeyListener(this)
        repaint()
    }

    override fun repaint(time: Long, x: Int, y: Int, width: Int, height: Int) {
        terminal.clear()
        for (entity in entities) {
            terminal.write(entity.char, entity.x, entity.y)
        }
        terminal.repaint()
    }

    override fun keyPressed(e: KeyEvent?) {
        log.debug { "Received key pressed: ${e?.keyCode}" }
        when (e?.keyCode) {
            KeyEvent.VK_UP -> player.y--
            KeyEvent.VK_DOWN -> player.y++
            KeyEvent.VK_LEFT -> player.x--
            KeyEvent.VK_RIGHT -> player.x++
        }
        repaint()
    }

    override fun keyReleased(e: KeyEvent?) {}

    override fun keyTyped(e: KeyEvent?) {}

}