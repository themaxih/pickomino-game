package model

import io.mockk.every
import io.mockk.mockk
import iut.info1.pickomino.Connector
import iut.info1.pickomino.data.DICE
import iut.info1.pickomino.data.Game
import iut.info1.pickomino.data.STATUS
import iut.info1.pickomino.data.State
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.math.pow

internal class PickominoGameTest {
    companion object {
        var mockkConnector: Connector = mockk()
        var game: PickominoGame = PickominoGame(mockkConnector)
        var mockkGame : Game = mockk() // Mock de la partie

        @JvmStatic
        fun playerNamesProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(arrayListOf("Joueur1", "Joueur2", "Joueur3", "Joueur4")),
                Arguments.of(arrayListOf("Joueur1", "Joueur2", "Joueur3")),
                Arguments.of(arrayListOf("Joueur1", "Joueur2")),
                Arguments.of(arrayListOf("Léo", "Maxence", "Samuel", "Loan")),
                Arguments.of(arrayListOf("Arnaud", "Jean-François", "Olivier"))
            )
        }

        fun count(dice: DICE) : Int {
            return if (dice == DICE.worm) {
                5
            } else {
                dice.ordinal + 1
            }
        }

        fun countDices(dices: Array<DICE>) : Int {
            var count = 0
            for (dice in dices) {
                count += count(dice)
            }
            return count
        }
        @JvmStatic
        fun getDiceScoreProvider() : Stream<Arguments> {
            val stream = Stream.builder<Arguments>()
            val listOfDices = listOf(DICE.d1, DICE.d2, DICE.d3, DICE.d4, DICE.d5, DICE.worm)
            var dices = emptyArray<DICE>()
            var oracle = 0

            // On teste toutes les combinaisons possibles de dés jusqu'à 4 dés => 1_555 combinaisons
            // Car si on souhaite tester toutes les combinaisons possibles, il y en aurait 6**8 (8 dés à 6 facess) => 1_679_616 combinaisons

            // On teste 6**1 combinaisons de dés (1 dé à 6 faces) => 6 combinaisons
            for (i in 0 until 6) {
                dices = emptyArray()
                oracle = 0
                dices += listOfDices[i]
                oracle += count(dices[0])
                stream.add(Arguments.of(dices.toList(), oracle))
            }

            // On teste 6**2 combinaisons de dés (2 dés à 6 faces) => 36 combinaisons
            for (i in 0 until 6.0.pow(2).toInt()) {
                dices = emptyArray()
                oracle = 0
                for (j in 0 until 2) {
                    dices += listOfDices[(i / 6.0.pow(j).toInt()) % 6]
                    oracle += count(dices[j])
                }
                stream.add(Arguments.of(dices.toList(), oracle))
            }

            // On teste 6**3 combinaisons de dés (3 dés à 6 faces) => 216 combinaisons
            for (i in 0 until 6.0.pow(3).toInt()) {
                dices = emptyArray()
                oracle = 0
                for (j in 0 until 3) {
                    dices += listOfDices[(i / 6.0.pow(j).toInt()) % 6]
                    oracle += count(dices[j])
                }
                stream.add(Arguments.of(dices.toList(), oracle))
            }

            // On teste 6**4 combinaisons de dés (4 dés à 6 faces) => 1296 combinaisons
            for (i in 0 until 6.0.pow(4).toInt()) {
                dices = emptyArray()
                oracle = 0
                for (j in 0 until 4) {
                    dices += listOfDices[(i / 6.0.pow(j).toInt()) % 6]
                    oracle += count(dices[j])
                }
                stream.add(Arguments.of(dices.toList(), oracle))
            }

            // Cas dés vides
            dices = emptyArray()
            oracle = 0
            stream.add(Arguments.of(dices.toList(), oracle))

            // Cas dés remplis
            for (i in 0 until 6) {
                dices = arrayOf(listOfDices[i], listOfDices[i], listOfDices[i], listOfDices[i], listOfDices[i], listOfDices[i], listOfDices[i], listOfDices[i])
                oracle = countDices(dices)
                stream.add(Arguments.of(dices.toList(), oracle))
            }

            return stream.build()
        }
    }

    @BeforeEach
    fun init() {
        mockkConnector = mockk()
        mockkGame = mockk() // Mock de la partie
        every { mockkConnector.gameState(1, 2) } returns mockkGame
        every { mockkConnector.newGame(2) } returns Pair(1,2) // Mock de la fonction newGame, qui renvoie un identifiant de partie et une clé de partie
        every { mockkConnector.newGame(3) } returns Pair(1,2) // Mock de la fonction newGame, qui renvoie un identifiant de partie et une clé de partie
        every { mockkConnector.newGame(4) } returns Pair(1,2) // Mock de la fonction newGame, qui renvoie un identifiant de partie et une clé de partie
        game = PickominoGame(mockkConnector)
    }

    @ParameterizedTest
    @MethodSource("playerNamesProvider")
    fun startGameTest(playerNames : ArrayList<String>) {
        val customColors : ArrayList<CustomColor> = arrayListOf(CustomColor("bleue","#5b6ee1"),CustomColor("vert","#6abe30"),CustomColor("orange","#df7126"),CustomColor("violet","#9c2ec6"))// Couleurs des joueurs

        game.startGame(playerNames) // On lance la partie

        assertEquals(game.getPlayers().size, playerNames.size, "Le nombre de joueurs n'est pas correct") // On vérifie que le nombre de joueurs est correct

        for (i in 0 until playerNames.size) {
            assertEquals(game.getPlayers()[i].toString(), Player(i, playerNames[i], customColors[i]).toString()) // On vérifie que les joueurs sont corrects
        }
    }

    @Test
    fun throwDicesWhenCantTest() {
        game.startGame(arrayListOf("Joueur1", "Joueur2", "Joueur3", "Joueur4")) // On lance la partie

        every { mockkGame.score() } returns listOf(0,0,0,0)

        val gameMockk = mockk<Game>() // Mock de la partie
        every { mockkConnector.rollDices(1, 2) } returns listOf(DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1) // Mock de la fonction rollDices, qui renvoie une liste de dés
        every { mockkConnector.gameState(1, 2)} returns gameMockk // Mock de la fonction gameState, qui renvoie l'état de la partie

        every { gameMockk.current.status } returns STATUS.KEEP_DICE // Mock de l'état de la partie, qui renvoie le statut KEEP_DICE

        assertEquals(false, game.throwDices()) // On vérifie que la fonction renvoie false, car on est dans le mauvais état
    }

    @Test
    fun throwDicesWhenCanTest() {
        game.startGame(arrayListOf("Joueur1", "Joueur2", "Joueur3", "Joueur4")) // On lance la partie

        every { mockkConnector.rollDices(1, 2) } returns listOf(DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1) // Mock de la fonction rollDices, qui renvoie une liste de dés

        every { mockkGame.current.status } returns STATUS.ROLL_DICE // Mock de l'état de la partie, qui renvoie le statut ROLL_DICE
        every { mockkGame.score() } returns listOf(0,0,0,0)
        every { mockkGame.pickosStackTops() } returns listOf(0,0,0,0)

        assertEquals(true, game.throwDices()) // On vérifie que la fonction renvoie false, car on est dans le mauvais état
    }

    @ParameterizedTest
    @MethodSource("playerNamesProvider")
    fun getPlayersTest(playerNames : ArrayList<String>) {
        game.startGame(playerNames) // On lance la partie

        val players = game.getPlayers() // On récupère les joueurs

        assertEquals(players.size, playerNames.size) // On vérifie que le nombre de joueurs est correct
        for (i in 0 until playerNames.size) {
            assertEquals(players[i].name, Player(i, playerNames[i], CustomColor("","")).name) // On vérifie que les joueurs sont corrects
        }
    }

    @ParameterizedTest
    @MethodSource("playerNamesProvider")
    fun getPlayerByIdTest(playerNames : ArrayList<String>) {
        game.startGame(playerNames) // On lance la partie

        for (i in 0 until playerNames.size) {
            assertEquals(game.getPlayerById(i).name, Player(i, playerNames[i], CustomColor("","")).name) // On vérifie que les joueurs sont corrects
        }
    }

    @ParameterizedTest
    @MethodSource("getDiceScoreProvider")
    fun getDiceScoreTest(dices : List<DICE>, oracle : Int) {
        game.startGame(arrayListOf("Joueur1", "Joueur2", "Joueur3", "Joueur4")) // On lance la partie

        val gameMockk = mockk<Game>() // Mock de la partie
        every { mockkConnector.gameState(1,2) } returns gameMockk // Mock de la fonction gameState, qui renvoie l'état de la partie
        every { gameMockk.current.keptDices } returns dices

        assertEquals(oracle, game.getDiceScore()) // On vérifie que la fonction renvoie le bon score
    }

    /*@Test
    fun getSortedByScoreTest() {
        val pickominoGame = PickominoGame(mockkConnector)
        pickominoGame.startGame(arrayListOf("Léo","Loan","Samuel","Maxence"))

        pickominoGame.getPlayers()[0].pickominoList.add(26)
        pickominoGame.getPlayers()[0].score = 6
        pickominoGame.getPlayers()[3].pickominoList.add(36)
        pickominoGame.getPlayers()[3].score = 6

        println(pickominoGame.getPlayers())

        for (playerRank in pickominoGame.getSortedByScore()) {
            println("#${playerRank.rank} - ${playerRank.player.name} | score : ${playerRank.player.score} - maxPicko : ${playerRank.player.pickominoList.maxOrNull()}")
        }


        assertEquals(true,true)
    }*/
}
