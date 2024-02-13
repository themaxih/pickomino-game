package controller.game

import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.input.MouseEvent
import view.GameView
import view.objects.PickominoButton

class HoverPickominosController (gameView: GameView) : EventHandler<MouseEvent> {

    private val gameView : GameView

    init {
        this.gameView = gameView
    }

    override fun handle(event: MouseEvent) {
        val btn : Button = event.source as Button
        if (event.eventType == MouseEvent.MOUSE_ENTERED) {
            btn.scaleX = 1.05
            btn.scaleY = 1.05
        }
        if (event.eventType == MouseEvent.MOUSE_EXITED) {
            btn.scaleX = 1.0
            btn.scaleY = 1.0
        }
    }
}