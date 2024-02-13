package controller.game

import iut.info1.pickomino.data.DICE
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.input.MouseEvent
import view.GameView
import view.objects.DiceButton

class HoverDicesController (gameView: GameView) : EventHandler<MouseEvent> {

    private val gameView : GameView

    init {
        this.gameView = gameView
    }

    override fun handle(event: MouseEvent) {
        val btn : DiceButton = event.source as DiceButton
        val diceButtons = arrayListOf<DiceButton>()

        for (child in gameView.rollDices.children) {
            if(child is DiceButton) {
                if(child.name == btn.name) {
                    diceButtons.add(child)
                }
            }
        }

        for(diceButton in diceButtons) {
            if (event.eventType == MouseEvent.MOUSE_ENTERED) {
                diceButton.scaleX = 1.05
                diceButton.scaleY = 1.05
            }
            if (event.eventType == MouseEvent.MOUSE_EXITED) {
                diceButton.scaleX = 1.0
                diceButton.scaleY = 1.0
            }
        }
    }
}