package controller.game

import iut.info1.pickomino.data.STATUS
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Alert
import model.PickominoGame
import model.Player
import view.GameOverView
import view.objects.PickominoButton
import view.GameView

class TakePickominoController(model: PickominoGame, gameView : GameView, scene : Scene, gameOverView: GameOverView) : EventHandler<ActionEvent> {

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
        val pickominoButton : PickominoButton = event.source as PickominoButton
        val player : Player = model.getCurrentPlayer()
        if(model.takePickomino(pickominoButton.pickominoValue)) {
            if(model.getGame().current.status == STATUS.GAME_FINISHED) {
                gameOverView.showPlayers(model.getSortedByScore())

                updatesGameView()

                model.getSortedByScore()
                model.clearPlayers()
                scene.root = gameOverView
            } else {

                val dialog = Alert(Alert.AlertType.INFORMATION)
                dialog.title = "Joueur suivant"
                dialog.headerText = null
                dialog.contentText = "Bravo ! Vous avez récupéré le pickomino ${pickominoButton.pickominoValue}, il s'ajoute à votre pile de pickomino"
                dialog.showAndWait()

                player.pickominoList.add(pickominoButton.pickominoValue)

                updatesGameView()

                for(pickominoBtn in gameView.pickominoButtons) {
                    pickominoBtn.updateScale(pickominoBtn.pickominoValue in model.getGame().pickosStackTops())
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