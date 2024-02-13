package controller.game

import iut.info1.pickomino.data.DICE
import iut.info1.pickomino.data.STATUS
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Alert
import model.PickominoGame
import model.Player
import view.GameOverView
import view.objects.DiceButton
import view.GameView

class KeepDicesController(model: PickominoGame, gameView : GameView, scene: Scene, gameOverView: GameOverView) : EventHandler<ActionEvent> {

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
        val diceButton : DiceButton = event.source as DiceButton
        val player : Player = model.getCurrentPlayer()
        if(model.keepDices(DICE.valueOf(diceButton.name))) {

            if(model.getGame().current.status == STATUS.GAME_FINISHED) {
                gameOverView.showPlayers(model.getSortedByScore())
                updatesGameView()
                model.getSortedByScore()
                model.clearPlayers()
                scene.root = gameOverView
            }

            gameView.diceScoreLabel.text = model.getDiceScore().toString()
            gameView.updateRightPane(model.getRolls(),gameView.rollDices)
            gameView.updateRightPane(model.getKeptDices(),gameView.keptDices)

            if(player != model.getCurrentPlayer()) {

                val dialog = Alert(Alert.AlertType.INFORMATION)
                dialog.title = "Joueur suivant"
                dialog.headerText = null
                dialog.contentText = "Dommage ! Vous avez conservés tous les dés mais vous ne pouvez rien prendre !"
                dialog.showAndWait()

                if(player.pickominoList.isNotEmpty()) {
                    player.pickominoList.removeLast()
                }

                model.reloadInformation()

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