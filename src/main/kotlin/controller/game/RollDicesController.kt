package controller.game

import iut.info1.pickomino.data.STATUS
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.input.MouseEvent
import javafx.scene.Scene
import javafx.scene.control.Alert
import model.PickominoGame
import view.GameOverView
import view.objects.DiceButton
import view.GameView

class RollDicesController(model: PickominoGame, gameView : GameView, scene : Scene, gameOverView: GameOverView) : EventHandler<ActionEvent> {

    private val model : PickominoGame

    private val gameView : GameView

    private val scene : Scene

    private val gameOverView : GameOverView

    init {
        this.model = model
        this.gameView = gameView
        this.scene = scene
        this.gameOverView = gameOverView
    }

    override fun handle(event: ActionEvent) {

        val player = model.getCurrentPlayer()
        if(model.throwDices()) {
            if(model.getGame().current.status == STATUS.GAME_FINISHED) {
                gameOverView.showPlayers(model.getSortedByScore())

                updatesGameView()

                model.getSortedByScore()
                model.clearPlayers()
                scene.root = gameOverView
            }
            if(player != model.getCurrentPlayer()) {

                val dialog = Alert(Alert.AlertType.INFORMATION)
                dialog.title = "Joueur suivant"
                dialog.headerText = null
                dialog.contentText = "Dommage ! Lors de votre lancé de dé, vous êtes tombés sur des dés que vous aviez déjà conservés"
                dialog.showAndWait()

                model.reloadInformation()

                updatesGameView()

                if(player.pickominoList.isNotEmpty()) {
                    player.pickominoList.removeLast()
                }

                for(pickominoBtn in gameView.pickominoButtons) {
                    pickominoBtn.updateScale(pickominoBtn.pickominoValue in model.getGame().pickosStackTops())
                }
            } else {
                gameView.updateRightPane(model.getRolls(),gameView.rollDices)
                for (child in gameView.rollDices.children) {
                    if(child is DiceButton) {
                        child.addEventHandler(MouseEvent.MOUSE_ENTERED, HoverDicesController(gameView))
                        child.addEventHandler(MouseEvent.MOUSE_EXITED, HoverDicesController(gameView))
                        child.addEventHandler(ActionEvent.ANY, KeepDicesController(model, gameView,scene, gameOverView))
                    }
                }
            }
        }
    }

    private fun updatesGameView() {
        gameView.updateRightPane(model.getRolls(),gameView.rollDices)
        gameView.updateRightPane(model.getKeptDices(),gameView.keptDices)
        gameView.updateCenterPane(model.getGame().accessiblePickos())
        gameView.diceScoreLabel.text = model.getDiceScore().toString()
        gameView.playerLabel.text = model.getCurrentPlayer().name
        gameView.updateDiceList(model.getCurrentPlayer().customColor.colorName)
        gameView.updateLeftPane(model.getGame().pickosStackTops(),model.getPlayers())
    }

}