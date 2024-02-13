package view.objects

import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import model.Player

class PlayerBox(val player: Player, pickominoButton: PickominoButton) : VBox() {

    val playerLabel = Label(player.name)
    val scoreHBox = HBox()
    val labelScore = Label("Score : ")
    val score = Label(player.score.toString())

    init {
        this.style = "-fx-background-color: " + player.customColor.colorCode + ";"

        playerLabel.font = Font.font("Tahoma",FontWeight.BOLD ,FontPosture.REGULAR, 20.0)
        playerLabel.padding = Insets(0.0,0.0,0.0,0.0)

        scoreHBox.alignment = Pos.CENTER
        labelScore.font = Font.font("Tahoma", FontPosture.REGULAR, 15.0)
        score.font = Font.font("Tahoma", FontPosture.REGULAR, 15.0)
        scoreHBox.children.addAll(labelScore, score)
        scoreHBox.padding = Insets(5.0,0.0,5.0,0.0)

        this.children.addAll(playerLabel,labelScore, scoreHBox, pickominoButton)

        this.alignment = Pos.CENTER

        this.styleClass.add("cadre-noir")
        this.padding = Insets(0.0,0.0,10.0,0.0)



    }

    fun changePickominoButton(pickominoButton: PickominoButton) {

        for(i in 0 until this.children.size) {
            if(this.children[i] is PickominoButton) {
                this.children.remove(this.children[i])
            }
        }
        this.children.add(pickominoButton)
    }

}