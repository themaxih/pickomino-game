package view.objects

import javafx.beans.binding.Bindings
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.scene.control.Button
import javafx.scene.control.ContentDisplay
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Background
import java.io.File

class DiceButton(file : File, screenWidthBinding : ReadOnlyDoubleProperty, screenHeightBinding : ReadOnlyDoubleProperty , scalePercentageDice : Double, name : String, ordinal : Int) : Button() {

    val name : String
    val value : Int

    init {

        this.name = name

        if(name == "worm") {
            this.value = 5
        } else {
            this.value = ordinal + 1
        }

        val diceUrl = file.toURI().toString()
        val imageDice = ImageView(Image(diceUrl))

        val imageWidthBinding = Bindings.multiply(screenWidthBinding, scalePercentageDice)
        val imageHeightBinding = Bindings.multiply(screenHeightBinding, scalePercentageDice)

        imageDice.fitWidthProperty().bind(imageWidthBinding)
        imageDice.fitHeightProperty().bind(imageHeightBinding)

        imageDice.isPreserveRatio = true

        this.graphic = imageDice
        this.background = Background.EMPTY
        this.contentDisplay = ContentDisplay.GRAPHIC_ONLY
    }




}