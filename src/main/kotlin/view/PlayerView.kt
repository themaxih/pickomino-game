package view

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight

class PlayerView() : GridPane() {
    private val flowPaneTop: FlowPane
    private val pane: GridPane

    val startButton: Button
    val homeButton: Button

    var textFields : ArrayList<TextField> = arrayListOf()

    init {
        this.startButton = Button("Valider")
        startButton.styleClass.add("displayButton")

        this.homeButton = Button("Annuler")
        homeButton.styleClass.add("displayButton")

        this.flowPaneTop = FlowPane()
        flowPaneTop.alignment = Pos.TOP_CENTER

        this.pane = GridPane()
        pane.alignment = Pos.CENTER

        this.alignment = Pos.TOP_CENTER


        // Buttons =====================================================================================================

        val buttonsGridPane = GridPane()
        buttonsGridPane.alignment = Pos.TOP_CENTER

        val rowTopButton = RowConstraints()
        rowTopButton.percentHeight = 50.0

        val rowBottomButton = RowConstraints()
        rowBottomButton.percentHeight = 50.0

        buttonsGridPane.rowConstraints.addAll(rowTopButton, rowBottomButton)

        buttonsGridPane.add(startButton,0,0)
        buttonsGridPane.add(homeButton,0,1)

        // =============================================================================================================

        val rowTop = RowConstraints()
        rowTop.percentHeight = 10.0

        val rowCenter = RowConstraints()
        rowCenter.percentHeight = 60.0

        val rowBottom = RowConstraints()
        rowBottom.percentHeight = 30.0

        this.rowConstraints.addAll(rowTop, rowCenter, rowBottom)

        /* Ajout des panneaux à la vue */
        add(flowPaneTop, 0, 0)
        add(pane, 0, 1)
        add(buttonsGridPane,0,2)

        setVgrow(pane, Priority.ALWAYS)
    }

    fun addPlayers(nbJoueurs: Int) {
        pane.children.clear()
        flowPaneTop.children.clear()

        val title = Label("Partie à ${nbJoueurs} joueurs !")
        title.font = Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 30.0)
        title.textFill = Color.BLACK
        title.padding = Insets(10.0)

        flowPaneTop.children.add(title)

        for (i in 1..nbJoueurs) {
            val playerNameVbox = VBox()

            val label = Label("Nom du joueur ${i} :")
            label.font = Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 20.0)
            label.padding = Insets(0.0,0.0,10.0,0.0)

            val textField = TextField()
            textFields.add(textField)

            playerNameVbox.children.addAll(label, textField)
            playerNameVbox.alignment = Pos.CENTER
            playerNameVbox.padding = Insets(0.0,0.0,20.0,0.0)

            /* Spécifier les contraintes de positionnement du VBox */
            setConstraints(playerNameVbox, 0, i)

            pane.children.add(playerNameVbox)
        }
    }

    fun setButtonController(btn: Button, action: EventHandler<ActionEvent>) {
        btn.addEventHandler(ActionEvent.ACTION,action)
    }

    fun getTextFieldList(): ArrayList<TextField> {
        return textFields
    }
}