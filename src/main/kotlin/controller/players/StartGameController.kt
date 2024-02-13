package controller.players

import controller.game.TakePickominoController
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Alert
import model.PickominoGame
import view.GameOverView
import view.GameView
import view.PlayerView
import view.HomeView

class StartGameController(model: PickominoGame, homeView: HomeView, playerView: PlayerView, gameView: GameView, scene: Scene, gameOverView: GameOverView) : EventHandler<ActionEvent> {

    private val model : PickominoGame

    private val homeView : HomeView

    private val playerView : PlayerView

    private val gameView : GameView

    private val scene : Scene

    private val gameOverView : GameOverView

    private val playerNames : ArrayList<String> = arrayListOf()

    init {
        this.model = model
        this.homeView = homeView
        this.playerView = playerView
        this.gameView = gameView
        this.scene = scene
        this.gameOverView = gameOverView
    }

    override fun handle(event: ActionEvent) {

        val textFields = playerView.getTextFieldList()

        /* Vérifie et stocke les valeurs non vides dans l'ArrayList playerNames */
        for (t in textFields) {
            val textFieldContent = t.text.trim()
            if (textFieldContent.isNotEmpty() && playerNames.size < textFields.size && !textFieldContent.contains(" ") ) {
                playerNames.add(textFieldContent)
            } else if (textFieldContent.contains(" ") || textFieldContent.isEmpty()) {
                alertBox()
                return
            }
        }

        model.startGame(playerNames)
        gameView.addPlayerBoxes(model.getPlayers())
        gameView.pickominoButtons.forEach {
            it.addEventHandler(ActionEvent.ACTION, TakePickominoController(model, gameView,scene, gameOverView))
        }
        gameView.updateDiceList(model.getCurrentPlayer().customColor.colorName)
        gameView.updateCenterPane(model.getGame().accessiblePickos())
        gameView.playerLabel.text = model.getCurrentPlayer().name
        scene.root = gameView
    }


    private fun alertBox() {
        val dialog = Alert(Alert.AlertType.ERROR)
        dialog.title = "Erreur"
        dialog.headerText = null
        dialog.contentText = "Veuillez rentrer les noms de tous les joueurs"
        dialog.showAndWait()
    }
}
