package view

import iut.info1.pickomino.data.DICE
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label

import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.stage.Screen
import model.Player
import view.objects.DiceButton
import view.objects.PickominoButton
import view.objects.PlayerBox
import java.io.File

class GameView : GridPane() {

    private val leftPane: GridPane /* Contient les panneaux de chaque joueur */

    private val rightPane: GridPane /* Contient les panneaux des dés ( "lancer dés", "dés choisis" ) */

    private val centerPane: GridPane /* Contient le panneau des pickominos */

    val diceScoreLabel : Label

    val playerLabel : Label

    val rollDicesButton: Button

    val rollDices = TilePane()
    val keptDices = TilePane()

    val pickominosTilePane = TilePane()

    val pickominoButtons = arrayListOf<PickominoButton>()

    val screenWidthBinding = this.widthProperty()
    val screenHeightBinding = this.heightProperty()

    val dicePercentageScale = 0.085

    val diceImagesPaths = arrayOf(
        "src/main/resources/pictures/dices/1bleue.png",
        "src/main/resources/pictures/dices/2bleue.png",
        "src/main/resources/pictures/dices/3bleue.png",
        "src/main/resources/pictures/dices/4bleue.png",
        "src/main/resources/pictures/dices/5bleue.png",
        "src/main/resources/pictures/dices/1vert.png",
        "src/main/resources/pictures/dices/2vert.png",
        "src/main/resources/pictures/dices/3vert.png",
        "src/main/resources/pictures/dices/4vert.png",
        "src/main/resources/pictures/dices/5vert.png",
        "src/main/resources/pictures/dices/1orange.png",
        "src/main/resources/pictures/dices/2orange.png",
        "src/main/resources/pictures/dices/3orange.png",
        "src/main/resources/pictures/dices/4orange.png",
        "src/main/resources/pictures/dices/5orange.png",
        "src/main/resources/pictures/dices/1violet.png",
        "src/main/resources/pictures/dices/2violet.png",
        "src/main/resources/pictures/dices/3violet.png",
        "src/main/resources/pictures/dices/4violet.png",
        "src/main/resources/pictures/dices/5violet.png",
        "src/main/resources/pictures/dices/vers.png",
    )

    val pickominoImagesPaths = arrayOf(
        "src/main/resources/pictures/pickomino/0.png",
        "src/main/resources/pictures/pickomino/21.png",
        "src/main/resources/pictures/pickomino/22.png",
        "src/main/resources/pictures/pickomino/23.png",
        "src/main/resources/pictures/pickomino/24.png",
        "src/main/resources/pictures/pickomino/25.png",
        "src/main/resources/pictures/pickomino/26.png",
        "src/main/resources/pictures/pickomino/27.png",
        "src/main/resources/pictures/pickomino/28.png",
        "src/main/resources/pictures/pickomino/29.png",
        "src/main/resources/pictures/pickomino/30.png",
        "src/main/resources/pictures/pickomino/31.png",
        "src/main/resources/pictures/pickomino/32.png",
        "src/main/resources/pictures/pickomino/33.png",
        "src/main/resources/pictures/pickomino/34.png",
        "src/main/resources/pictures/pickomino/35.png",
        "src/main/resources/pictures/pickomino/36.png"

    )

    val screenBounds = Screen.getPrimary().bounds

    private val playerDiceImagesPaths = arrayListOf<String>()

    /*
    val minecraftFont = Font.loadFont(
        this::class.java.getResourceAsStream("/fonts/Retro Gaming.ttf"),
        15.0
    )
    */

    init {
        this.leftPane = GridPane()
        this.rightPane = GridPane()
        this.centerPane = GridPane()
        this.rollDicesButton = Button("Lancer les dés")
        rollDicesButton.styleClass.add("displayButton")
        this.diceScoreLabel = Label("0")
        this.playerLabel = Label("")

        // Left Pane ==============================================================================================

        leftPane.vgap = 10.0
        leftPane.padding = Insets(10.0,0.0,10.0,10.0)
        leftPane.alignment = Pos.CENTER

        // Center Pane ==============================================================================================

        val diceScoresVBox = VBox()

        val diceScoreText = Label("Score des dés :")
        diceScoreText.padding = Insets(0.0,0.0,10.0,0.0)

        diceScoreLabel.font = Font.font("Tahoma", FontPosture.REGULAR, 30.0)
        diceScoreLabel.font = Font.font("Tahoma", FontPosture.REGULAR, 30.0)

        val rollsDiceVBox = VBox()
        val rollsDiceHBox = HBox()

        playerLabel.font = Font.font("Tahoma", FontPosture.REGULAR, 40.0)
        playerLabel.padding = Insets(0.0,20.0,0.0,0.0)

        pickominosTilePane.alignment = Pos.CENTER
        pickominosTilePane.hgap = 10.0
        pickominosTilePane.vgap = 10.0

        /* Ajout des Boutons Pickomino sauf le 0 au centre et dans la liste des boutons pickomino */
        for (pickominoImagePath in pickominoImagesPaths) {
            if(pickominoImagePath.contains("/0")) {
                continue
            }
            val file = File(pickominoImagePath)

            val pickominoButton = PickominoButton(file, screenWidthBinding, screenHeightBinding, Integer.parseInt(pickominoImagePath.substring(38,40)),false)
            pickominoButtons.add(pickominoButton)
            pickominosTilePane.children.add(pickominoButton)
        }

        centerPane.padding = Insets(10.0,0.0,10.0,0.0)
        centerPane.alignment = Pos.CENTER

        diceScoresVBox.children.addAll(diceScoreText, diceScoreLabel)
        diceScoresVBox.alignment = Pos.CENTER

        rollsDiceHBox.children.addAll(playerLabel, rollDicesButton)
        rollsDiceHBox.alignment = Pos.CENTER

        rollsDiceVBox.children.addAll(rollsDiceHBox)
        rollsDiceVBox.alignment = Pos.CENTER


        val rowTop = RowConstraints()
        rowTop.percentHeight = 70.0

        val rowCenter = RowConstraints()
        rowCenter.percentHeight = 15.0

        val rowBottom = RowConstraints()
        rowBottom.percentHeight = 15.0

        centerPane.rowConstraints.addAll(rowTop, rowCenter, rowBottom)

        setHgrow(pickominosTilePane, Priority.ALWAYS)

        /* Ajout des panneaux à la vue */
        centerPane.add(pickominosTilePane,0,0)
        centerPane.add(diceScoresVBox,0,1)
        centerPane.add(rollsDiceVBox,0,2)

        centerPane.prefHeight = screenBounds.height

        // Right Pane ===============================================================================================

        val rollDicesVBox = VBox()

        val rollsDicesLabel = Label("Dés lancés")
        rollsDicesLabel.font = Font.font("Tahoma", FontPosture.REGULAR, 20.0)
        rollsDicesLabel.padding = Insets(10.0)

        val keptDicesVBox = VBox()

        val keptDicesLabel = Label("Dés choisis")
        keptDicesLabel.font = Font.font("Tahoma", FontPosture.REGULAR, 20.0)
        keptDicesLabel.padding = Insets(10.0)

        rollDicesVBox.alignment = Pos.TOP_CENTER
        keptDicesVBox.alignment = Pos.TOP_CENTER

        rollDices.alignment = Pos.CENTER
        keptDices.alignment = Pos.CENTER

        rollDices.hgap = 10.0
        rollDices.vgap = 10.0

        keptDices.hgap = 10.0
        keptDices.vgap = 10.0

        rollDicesVBox.children.addAll(rollsDicesLabel, rollDices)
        keptDicesVBox.children.addAll(keptDicesLabel, keptDices)

        setHgrow(rollDicesVBox, Priority.ALWAYS)
        setHgrow(keptDicesVBox, Priority.ALWAYS)

        rollDicesVBox.styleClass.add("cadre-noir")
        keptDicesVBox.styleClass.add("cadre-noir")

        val topRightRowConstraints = RowConstraints()
        topRightRowConstraints.percentHeight = 50.0

        val bottomRightRowConstraints = RowConstraints()
        bottomRightRowConstraints.percentHeight = 50.0

        rightPane.rowConstraints.addAll(topRightRowConstraints, bottomRightRowConstraints)

        rightPane.padding = Insets(20.0,20.0,20.0,0.0)
        rightPane.alignment = Pos.CENTER
        rightPane.vgap = 20.0

        rightPane.add(rollDicesVBox,0,0)
        rightPane.add(keptDicesVBox,0,1)

        // =============================================================================================================

        /* On va ici centrer les différents panneaux et définir les contraintes de colonnes */

        val leftColumn = ColumnConstraints()
        leftColumn.percentWidth = 25.0

        val centerColumn = ColumnConstraints()
        centerColumn.percentWidth = 50.0

        val rightColumn = ColumnConstraints()
        rightColumn .percentWidth = 25.0

        columnConstraints.addAll(leftColumn, centerColumn, rightColumn)

        // Ajouter les panneaux à la Vue
        add(leftPane, 0, 0)
        add(centerPane, 1, 0)
        add(rightPane, 2, 0)

        /*
        centerPane.minHeightProperty().bind(screenHeightBinding)
        centerPane.maxHeightProperty().bind(screenHeightBinding)*/


        /* Petite partie Debug pour afficher les différentes parties de l'interface avec des couleurs différentes */

        /*val debugCouleurs = false
        if (debugCouleurs) {
            leftPane.style = "-fx-background-color: green;"
            centerPane.style = "-fx-background-color: red;"
            rightPane.style = "-fx-background-color: blue;"
            rollsDicesLabel.style = "-fx-background-color: orange;"
            keptDicesLabel.style = "-fx-background-color: cyan;"
            rollsDiceVBox.style = "-fx-background-color: green;"
            rollsDiceHBox.style = "-fx-background-color: cyan"
            keptDicesVBox.style = "-fx-background-color: purple;"
            rollDicesVBox.style = "-fx-background-color: pink;"
            pickominosTilePane.style = "-fx-background-color: yellow;"
        }*/

    }

    fun addPlayerBoxes(players : ArrayList<Player>) {
         for (player in players) {
             val file = File(pickominoImagesPaths[0])
             val playerBox = PlayerBox(player, PickominoButton(file,screenWidthBinding,screenHeightBinding,0, true))

             /* Spécifier les contraintes de positionnement du VBox */
             setConstraints(playerBox, 0, player.id)
             setHgrow(playerBox, Priority.ALWAYS)
             setVgrow(playerBox, Priority.ALWAYS)

             leftPane.children.add(playerBox)
        }

    }

    fun setButtonController(btn: Button, action: EventHandler<ActionEvent>) {
        btn.addEventHandler(ActionEvent.ACTION,action)
    }

    fun setHoverController(btn: Button, action: EventHandler<MouseEvent>) {
        btn.addEventHandler(MouseEvent.MOUSE_ENTERED,action)
        btn.addEventHandler(MouseEvent.MOUSE_EXITED,action)
    }

    fun setHoverPickominosController(buttons: List<Button>, action: EventHandler<MouseEvent>) {
        for (btn in buttons) {
            btn.addEventHandler(MouseEvent.MOUSE_ENTERED,action)
            btn.addEventHandler(MouseEvent.MOUSE_EXITED,action)
        }
    }

    fun updateRightPane(rolls : List<DICE>, pane : TilePane) {

        pane.children.clear()

        for(roll in rolls) {
            val file = File(playerDiceImagesPaths[roll.ordinal])

            val diceButton = DiceButton(file,screenWidthBinding,screenHeightBinding,dicePercentageScale,roll.name,roll.ordinal)
            pane.children.add(diceButton)

        }
    }

    fun updateCenterPane(availablePickos : List<Int>) {
        pickominosTilePane.children.clear()
        for (pickominoButton in pickominoButtons) {
            if(pickominoButton.pickominoValue in availablePickos) {
                pickominosTilePane.children.add(pickominoButton)
            }
        }
    }

    fun updateLeftPane(topPickos : List<Int>, players: ArrayList<Player>) {
        for(child in leftPane.children) {
            if(child is PlayerBox) {
                if(topPickos[child.player.id] == 0) {
                    child.changePickominoButton(PickominoButton(File(pickominoImagesPaths[0]),screenWidthBinding,screenHeightBinding,0,true))
                } else {
                    for(pickominoButton in pickominoButtons) {
                        if(pickominoButton.pickominoValue == topPickos[child.player.id]) {
                            child.changePickominoButton(pickominoButton)
                        }
                    }
                }
                child.score.text = players[child.player.id].score.toString()
            }
        }
    }

    fun updateDiceList(color : String) {
        /* Stocke les index des dés correspondant à la couleur du joueur */

        playerDiceImagesPaths.clear()

        /* Filtre les dés pour qu'ils correspondent à la couleur choisie */
        diceImagesPaths.forEachIndexed { index, path ->
            if (path.contains(color) || path.contains("vers")) {
                playerDiceImagesPaths.add(path)

            }
        }
    }
}