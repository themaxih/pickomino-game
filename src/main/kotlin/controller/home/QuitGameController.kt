package controller.home

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.stage.Stage

class QuitGameController(stage : Stage) : EventHandler<ActionEvent> {

    private val stage : Stage

    init {
        this.stage = stage
    }

    override fun handle(event: ActionEvent) {
        stage.close()
    }
}