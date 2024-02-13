package controller.game

import javafx.event.EventHandler
import javafx.scene.input.MouseEvent
import view.GameView

class HoverButtonsController (gameView: GameView) : EventHandler<MouseEvent> {

    private val gameView : GameView

    init {
        this.gameView = gameView
    }

    override fun handle(event: MouseEvent) {
        if (event.eventType == MouseEvent.MOUSE_ENTERED) {
            gameView.rollDicesButton.scaleX = 1.05
            gameView.rollDicesButton.scaleY = 1.05
        }
        if (event.eventType == MouseEvent.MOUSE_EXITED) {
            gameView.rollDicesButton.scaleX = 1.0
            gameView.rollDicesButton.scaleY = 1.0
        }
    }
}



