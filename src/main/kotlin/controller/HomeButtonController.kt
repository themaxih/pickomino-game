package controller

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import view.HomeView

class HomeButtonController(homeView: HomeView, scene: Scene) : EventHandler<ActionEvent> {

    private val homeView : HomeView

    private val scene : Scene

    init {
        this.homeView = homeView
        this.scene = scene
    }

    override fun handle(event: ActionEvent) {
        scene.root = homeView
    }
}