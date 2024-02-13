package view

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import javafx.scene.control.Label
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.*
import javafx.scene.layout.*

class HomeView : GridPane() {
    val twoPlayersButton: Button
    val threePlayersButton: Button
    val fourPlayersButton: Button

    val rulesButton: Button

    val quitButton: Button

    init {
        this.twoPlayersButton = Button("2 Joueurs")
        this.threePlayersButton = Button("3 Joueurs")
        this.fourPlayersButton = Button("4 Joueurs")
        this.rulesButton = Button("Règles")
        this.quitButton = Button("Quitter")

        // Top Pane ================================================================================================

        val flowPaneTop = FlowPane()
        flowPaneTop.alignment = Pos.TOP_CENTER

        // Title

        val title = Label("Pickomino !")
        title.font = Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 30.0)
        title.textFill = Color.BLACK
        title.padding = Insets(10.0)

        flowPaneTop.children.add(title)

        // Center Pane ==============================================================================================

        val centerTilePane = TilePane()
        centerTilePane.alignment = Pos.CENTER
        centerTilePane.children.addAll(twoPlayersButton, threePlayersButton, fourPlayersButton)
        centerTilePane.hgap = 10.0

        // Bottom Pane =================================================================================================

        val bottomTilePane = TilePane()
        bottomTilePane.alignment = Pos.TOP_CENTER
        bottomTilePane.children.add(rulesButton)

        // Left Pane ==============================================================================================

        val leftTilePane = TilePane()
        leftTilePane.alignment = Pos.BOTTOM_LEFT
        leftTilePane.padding = Insets(0.0,0.0,10.0,10.0)
        leftTilePane.children.add(quitButton)

        // Right Pane ===============================================================================================

        val rightTilePane = TilePane()
        rightTilePane.alignment = Pos.BOTTOM_RIGHT
        rightTilePane.padding = Insets(0.0,10.0,10.0,0.0)

        val version = Label("v1.0")
        version.font = Font.font("Tahoma", FontPosture.REGULAR, 20.0)
        version.textFill = Color.BLACK

        rightTilePane.children.add(version)

        // StyleClasses ================================================================================================

        twoPlayersButton.styleClass.addAll("displayButton", "displayButton-2-players")
        threePlayersButton.styleClass.addAll("displayButton", "displayButton-3-players")
        fourPlayersButton.styleClass.addAll("displayButton", "displayButton-4-players")
        rulesButton.styleClass.add("displayButton")
        quitButton.styleClass.add("displayButton")


        // RowConstraints ==============================================================================================

        val rowTop = RowConstraints()
        rowTop.percentHeight = 20.0

        val rowCenter = RowConstraints()
        rowCenter.percentHeight = 40.0

        val rowBottom = RowConstraints()
        rowBottom.percentHeight = 40.0

        this.rowConstraints.addAll(rowTop, rowCenter, rowBottom)

        // ColumnConstraints ===========================================================================================

        val columnLeft = ColumnConstraints()
        columnLeft.percentWidth = 20.0

        val columnCenter = ColumnConstraints()
        columnCenter.percentWidth = 60.0

        val columnRight = ColumnConstraints()
        columnRight.percentWidth = 20.0

        this.columnConstraints.addAll(columnLeft, columnCenter, columnRight)

        // =============================================================================================================

        /* Ajout des panneaux à la vue */

        add(flowPaneTop, 1, 0)
        add(centerTilePane, 1, 1)
        add(bottomTilePane, 1, 2)
        add(leftTilePane,0,2)
        add(rightTilePane,2,2)

    }

    fun setButtonController(btn: Button, action: EventHandler<ActionEvent>) {
        btn.addEventHandler(ActionEvent.ACTION,action)
    }
}