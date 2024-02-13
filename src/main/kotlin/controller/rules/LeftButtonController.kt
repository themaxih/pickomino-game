package controller.rules

import javafx.event.ActionEvent
import javafx.event.EventHandler
import view.RulesView

class LeftButtonController(rulesView: RulesView) : EventHandler<ActionEvent> {

    private val rulesView: RulesView

    init {
        this.rulesView = rulesView
    }

    override fun handle(event: ActionEvent) {
        rulesView.current = (rulesView.current - 1 + rulesView.rulesImagesPaths.size) % rulesView.rulesImagesPaths.size
        rulesView.pane.children.clear() // Efface les images précédentes
        rulesView.updateImage(rulesView.current)
    }
}