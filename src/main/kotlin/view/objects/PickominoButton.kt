package view.objects

import javafx.beans.binding.Bindings
import javafx.beans.property.DoubleProperty
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.control.Button
import javafx.scene.control.ContentDisplay
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import java.io.File

class PickominoButton(file : File, screenWidthBinding : ReadOnlyDoubleProperty, screenHeightBinding : ReadOnlyDoubleProperty, pickominoValue : Int, isInPlayerBox: Boolean) : Button() {


    val pickominoValue : Int

    private var scalePercentagePickominoWidht : DoubleProperty = SimpleDoubleProperty()
    private var scalePercentagePickominoHeight : DoubleProperty = SimpleDoubleProperty()

    init {

        this.pickominoValue = pickominoValue

        this.scalePercentagePickominoWidht.value = 0.045
        this.scalePercentagePickominoHeight.value = 0.18
        updateScale(isInPlayerBox)

        val pickominoUrl = file.toURI().toString()
        val imagePickomino = ImageView(Image(pickominoUrl))

        val imageWidthBinding = Bindings.multiply(screenWidthBinding, scalePercentagePickominoWidht)
        val imageHeightBinding = Bindings.multiply(screenHeightBinding, scalePercentagePickominoHeight)

        imagePickomino.fitWidthProperty().bind(imageWidthBinding)
        imagePickomino.fitHeightProperty().bind(imageHeightBinding)

        imagePickomino.isPreserveRatio = true

        this.graphic = imagePickomino
        this.background = null
        this.border = null
        this.contentDisplay = ContentDisplay.GRAPHIC_ONLY
    }

    fun updateScale(isInPlayerBox : Boolean) {

        if(isInPlayerBox) {
            this.scalePercentagePickominoWidht.value = 0.038
            this.scalePercentagePickominoHeight.value = 0.15
        } else {
            this.scalePercentagePickominoWidht.value = 0.045
            this.scalePercentagePickominoHeight.value = 0.18
        }

    }





}