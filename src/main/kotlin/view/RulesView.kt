package view

import javafx.beans.binding.Bindings
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import java.io.File

class RulesView : GridPane() {
    private val flowPaneTop: FlowPane
    val pane: GridPane

    val backButton: Button
    val rightButton: Button
    val leftButton: Button

    val rulesImagesPaths = arrayOf(
        "src/main/resources/pictures/regles/regles1.png",
        "src/main/resources/pictures/regles/regles2.png",
        "src/main/resources/pictures/regles/regles3.png",
        "src/main/resources/pictures/regles/regles4.png",
        "src/main/resources/pictures/regles/regles5.png",
        "src/main/resources/pictures/regles/regles6.png",
        "src/main/resources/pictures/regles/regles7.png",
        "src/main/resources/pictures/regles/regles8.png"
    )

    var current = 0

    init {

        this.backButton = Button("Retour")
        this.backButton.styleClass.add("displayButton")

        this.rightButton = Button(">")
        this.rightButton.styleClass.add("displayButton")

        this.leftButton = Button("<")
        this.leftButton.styleClass.add("displayButton")

        this.flowPaneTop = FlowPane()
        this.pane = GridPane()
        this.alignment = Pos.TOP_CENTER

        // TextBox ==============================================================================================

        val title = Label("Règles du jeu")
        title.font = Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 30.0)
        title.textFill = Color.BLACK
        title.padding = Insets(10.0)

        flowPaneTop.alignment = Pos.TOP_CENTER
        flowPaneTop.children.add(title)

        // Pane =====================================================================================================

        pane.alignment = Pos.CENTER

        // LeftPane ==============================================================================================

        val backButtonTilePane = TilePane()
        backButtonTilePane.alignment = Pos.BOTTOM_LEFT
        backButtonTilePane.padding = Insets(0.0, 0.0, 10.0, 10.0)
        backButtonTilePane.children.add(backButton)

        // RightPane ==============================================================================================

        val arrowsButtonTilePane = TilePane()
        arrowsButtonTilePane.alignment = Pos.BOTTOM_RIGHT
        arrowsButtonTilePane.padding = Insets(0.0, 10.0, 10.0, 0.0)
        arrowsButtonTilePane.hgap = 10.0
        arrowsButtonTilePane.children.addAll(leftButton, rightButton)

        // RowConstraints =========================================================================================

        val rowTop = RowConstraints()
        rowTop.percentHeight = 10.0

        val rowCenter = RowConstraints()
        rowCenter.percentHeight = 80.0

        val rowBottom = RowConstraints()
        rowBottom.percentHeight = 10.0

        this.rowConstraints.addAll(rowTop, rowCenter, rowBottom)

        // ColumnConstraints ======================================================================================

        val columnLeft = ColumnConstraints()
        columnLeft.percentWidth = 20.0

        val columnCenter = ColumnConstraints()
        columnCenter.percentWidth = 60.0

        val columnRight = ColumnConstraints()
        columnRight.percentWidth = 20.0

        this.columnConstraints.addAll(columnLeft, columnCenter, columnRight)

        /* Ajout des Panneaux à la vue */

        add(flowPaneTop, 1, 0)
        add(pane, 1, 1)
        add(backButtonTilePane, 0, 2)
        add(arrowsButtonTilePane, 2, 2)

        setVgrow(pane, Priority.ALWAYS)

    }

    fun updateImage(index: Int) {
        val file = File(rulesImagesPaths[index])
        val ruleUrl = file.toURI().toString()
        val imageRule = ImageView(Image(ruleUrl))

        /* Change le scale de l'image */
        val scalePercentageRule = 0.8

        /* Change le scale de l'image en pourcentage */
        val screenWidthBinding = this.widthProperty()
        val screenHeightBinding = this.heightProperty()

        val imageWidthBinding = Bindings.multiply(screenWidthBinding, scalePercentageRule)
        val imageHeightBinding = Bindings.multiply(screenHeightBinding, scalePercentageRule)

        imageRule.fitWidthProperty().bind(imageWidthBinding)
        imageRule.fitHeightProperty().bind(imageHeightBinding)

        imageRule.isPreserveRatio = true

        pane.children.add(imageRule)
    }

    fun setButtonController(btn: Button, action: EventHandler<ActionEvent>) {
        btn.addEventHandler(ActionEvent.ACTION,action)
    }
}