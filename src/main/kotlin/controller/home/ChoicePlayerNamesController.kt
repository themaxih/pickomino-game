package controller.home

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import view.PlayerView

class ChoicePlayerNamesController(playerView: PlayerView, scene: Scene) : EventHandler<ActionEvent> {

    private val playerView : PlayerView

    private val scene : Scene

    init {
        this.playerView = playerView
        this.scene = scene
    }

    override fun handle(event: ActionEvent) {

        val btn : Button = event.source as Button
        val nbPlayers = Integer.parseInt(btn.text.subSequence(0,1) as String)

        playerView.textFields.clear()
        playerView.addPlayers(nbPlayers)
        scene.root = playerView
    }
}