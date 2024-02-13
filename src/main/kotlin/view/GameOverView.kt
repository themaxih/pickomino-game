package view

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import model.Player
import model.PlayerRank

class GameOverView: GridPane() {
    private val flowPaneTop : FlowPane = FlowPane()
    val homeButton: Button = Button("Retour à l'acceuil")

    private val pane: GridPane = GridPane()

    init {
        this.alignment = Pos.TOP_CENTER

        // Title =======================================================================================================

        val vBoxTop = VBox()
        val title = Label("Fin de la partie")
        val results = Label("Résultats")

        title.font = Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 30.0)
        title.textFill = Color.BLACK
        title.padding = Insets(10.0)

        results.font = Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 20.0)

        vBoxTop.alignment = Pos.CENTER
        vBoxTop.children.addAll(title, results)

        flowPaneTop.alignment = Pos.TOP_CENTER
        flowPaneTop.children.add(vBoxTop)

        // Button ======================================================================================================

        homeButton.styleClass.add("displayButton")

        val buttonGridPane = GridPane()
        buttonGridPane.alignment = Pos.CENTER
        buttonGridPane.children.add(homeButton)

        // =============================================================================================================

        val rowTop = RowConstraints()
        rowTop.percentHeight = 10.0

        val rowCenter = RowConstraints()
        rowCenter.percentHeight = 70.0

        val rowBottom = RowConstraints()
        rowBottom.percentHeight = 20.0

        this.rowConstraints.addAll(rowTop, rowCenter, rowBottom)

        // Ajouter les panneaux à la Vue
        add(flowPaneTop, 0, 0)
        add(pane, 0, 1)
        add(buttonGridPane,0,2)

        setVgrow(pane, Priority.ALWAYS)

    }

    fun showPlayers(playersRank: ArrayList<PlayerRank>) {
        pane.children.clear()

        for ((index, playerRank) in playersRank.withIndex()) {
            val playerNameVBox = VBox()
            val nameLabel = Label(playerRank.player.name)
            nameLabel.font = Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 20.0)
            nameLabel.padding = Insets(0.0,0.0,10.0,0.0)

            val rankLabel = Label("Top ${playerRank.rank}")
            rankLabel.font = Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 22.0)
            rankLabel.padding = Insets(5.0,0.0,5.0,0.0)
            rankLabel.alignment = Pos.CENTER

            val scoreHBox = HBox()
            scoreHBox.alignment = Pos.CENTER

            val scoreLabel = Label("Score : ")
            scoreLabel.font = Font.font("Tahoma", FontPosture.REGULAR, 15.0)
            val score = Label(playerRank.player.score.toString())
            score.font = Font.font("Tahoma", FontPosture.REGULAR, 20.0)
            scoreHBox.children.addAll(scoreLabel, score)

            scoreHBox.padding = Insets(5.0,0.0,5.0,0.0)
            playerNameVBox.children.addAll(rankLabel, nameLabel, scoreHBox)
            playerNameVBox.alignment = Pos.CENTER

            /* Spécifier les contraintes de positionnement du VBox */
            setConstraints(playerNameVBox, 0, index+1)

            playerNameVBox.styleClass.add("cadre-noir")
            playerNameVBox.style = "-fx-background-color: ${playerRank.player.customColor.colorCode};"

            playerNameVBox.padding = Insets(10.0,20.0,10.0,20.0)

            pane.vgap = 10.0

            pane.children.addAll(playerNameVBox)
            pane.alignment = Pos.CENTER
        }
    }

    fun setButtonController(btn: Button, action: EventHandler<ActionEvent>) {
        btn.addEventHandler(ActionEvent.ACTION,action)
    }

}