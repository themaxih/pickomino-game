import controller.HomeButtonController
import controller.game.RollDicesController
import controller.game.HoverButtonsController
import controller.game.HoverDicesController
import controller.game.HoverPickominosController
import controller.home.ChoicePlayerNamesController
import controller.home.QuitGameController
import controller.players.StartGameController
import controller.home.RulesButtonController
import controller.rules.RightButtonController
import controller.rules.LeftButtonController
import iut.info1.pickomino.Connector
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Screen
import javafx.stage.Stage
import model.PickominoGame
import view.*

class Jeu: Application() {
    override fun start(primaryStage: Stage) {
        val connect = Connector.factory("172.26.82.76", "8080")
        val homeView = HomeView()
        val gameView = GameView()
        val playerView = PlayerView()
        val rulesView = RulesView()
        val gameOverView = GameOverView()
        val model = PickominoGame(connect)

        val screenBounds = Screen.getPrimary().bounds

        val scene = Scene(homeView, screenBounds.width, screenBounds.height-80)

        scene.stylesheets.add("styles/style.css")

        gameView.setHoverPickominosController(gameView.pickominoButtons, HoverPickominosController(gameView))
        gameView.setButtonController(gameView.rollDicesButton, RollDicesController(model,gameView,scene,gameOverView))
        gameView.setHoverController(gameView.rollDicesButton, HoverButtonsController(gameView))
        homeView.setButtonController(homeView.twoPlayersButton, ChoicePlayerNamesController(playerView, scene))
        homeView.setButtonController(homeView.threePlayersButton, ChoicePlayerNamesController(playerView, scene))
        homeView.setButtonController(homeView.fourPlayersButton, ChoicePlayerNamesController(playerView, scene))
        homeView.setButtonController(homeView.rulesButton, RulesButtonController(rulesView, scene))
        playerView.setButtonController(playerView.startButton, StartGameController(model, homeView, playerView, gameView, scene,gameOverView))
        playerView.setButtonController(playerView.homeButton, HomeButtonController(homeView, scene))
        rulesView.setButtonController(rulesView.rightButton, RightButtonController(rulesView))
        rulesView.setButtonController(rulesView.leftButton, LeftButtonController(rulesView))
        rulesView.setButtonController(rulesView.backButton, HomeButtonController(homeView, scene))
        gameOverView.setButtonController(gameOverView.homeButton, HomeButtonController(homeView, scene))

        val stage = Stage()

        stage.title = "Pickomino"
        stage.scene = scene

        stage.show()

        homeView.setButtonController(homeView.quitButton,QuitGameController(stage))
    }

}

fun main() {
    Application.launch(Jeu::class.java)
}


