package controller.home

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import view.RulesView

class RulesButtonController(rulesView : RulesView, scene : Scene) : EventHandler<ActionEvent> {

    private val rulesView : RulesView

    private val scene : Scene

    init {
        this.rulesView = rulesView
        this.scene = scene
    }

    override fun handle(event: ActionEvent) {
        rulesView.updateImage(0)
        scene.root = rulesView
    }

}