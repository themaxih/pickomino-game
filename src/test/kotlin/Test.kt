import iut.info1.pickomino.Connector
import iut.info1.pickomino.data.DICE
import iut.info1.pickomino.data.STATUS
import iut.info1.pickomino.exceptions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class TestTakePicko {

    // Choix du nombre de joueur
    private val nbPlayers = 2
    // Création d'un partie sur le serveur
    private val connect = Connector.factory("172.26.82.76", "8080", debug = true)
    private var id = 0
    private var key = 0

    // Création d'une Fonction qui sera réutilisée pour toutes les fonctions de la classe
    @BeforeEach
    fun setup() {
        val identification = connect.newGame(nbPlayers)
        id = identification.first
        key = identification.second
    }

    @Test
    fun testConformedToRules() {
    // Test permettant de vérifier une issue qui posait problème au début

        // On oriente les lancers de dés afin que le joueur 1 possède le pickomino 26
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d1, DICE.d2, DICE.d2))
        connect.keepDices(id, key, DICE.d1)
        // Le joueur 1 prend le pickomino 26
        connect.takePickomino(id, key, 26)

        // On vérifie que le pickomino 26 est bien dans le haut de la pile du joueur 1
        assertEquals(26, connect.gameState(id, key).pickosStackTops()[0], "Le joueur 1 a normalement le pickomino 26 au sommet de sa pile")

        // On oriente les lancers de dés afin que le joueur 2 fasse 26 aussi
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d1, DICE.d2, DICE.d2))
        connect.keepDices(id, key, DICE.d1)

        // On vérifie que lorsque le joueur 2 essaie de prendre le pickomino 25, le serveur ne renvoie pas d'erreur
        assertDoesNotThrow {
            connect.takePickomino(id, key, 25)
        }
    }

    @Test
    fun testTakeHiddenPicko() {
        // On veut ici voir que l'on ne peut pas prendre de pickominos recouvert

        // On oriente ici les dés afin de choisir le pickomino 23
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d3, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d3)
        connect.takePickomino(id, key, 23)

        // On oriente ici les dés afin de choisir le pickomino 24
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d4)
        connect.takePickomino(id, key, 24)

        // On oriente ici les dés afin de choisir le pickomino 25
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.takePickomino(id, key, 25)

        // On demande ici au joueur 2 de faire 23 afin voir si il peut récupérer le pickomino caché
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d3, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d3)
        assertThrows<BadPickominoChosenException> {
            connect.takePickomino(id, key, 23)
        }
    }

    @Test
    fun testTakeHigherPicko() {
    // Test vérifiant que l'on ne peut prendre de pickominos de valeur supérieur a la somme de dés que l'on a obtenu

        // On oriente ici les dés afin de faire la valeur 24
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d3, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d3)

        assertThrows<BadPickominoChosenException> {
            // On choisis ici le pickomino voulu
            connect.takePickomino(id, key, 24)
        }
    }

    @Test
    fun testTakeLowerPicko() {
    // Test vérifiant que nous ne pouvons prendre de pickominos de valeur inférieur a celui qui devrait être pris

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d3, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d3)
        connect.takePickomino(id, key, 23)

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d2, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d2)
        assertThrows<BadPickominoChosenException> {
            connect.takePickomino(id, key, 21)
        }
    }

    @Test
    fun testTakeGoodPicko() {
    // Test vérifiant que nous prenons le bon pickomino

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d3, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d3)

        assertDoesNotThrow {
            connect.takePickomino(id, key, 23)
        }
    }

    @Test
    fun testStealPicko() {
    // Test vérifiant que l'on peut voler des pickominos de nos adversaires

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d3, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d3)
        connect.takePickomino(id, key, 23)

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d4)
        connect.takePickomino(id, key, 24)

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d4)

        assertDoesNotThrow {
            connect.takePickomino(id, key, 24)
        }

    }

    @Test
    fun testTakeOwnPicko() {
    // Test vérifiant que l'on ne peut pas prendre sont propre pickomino

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d3, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d3)
        connect.takePickomino(id, key, 23)

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d4)
        connect.takePickomino(id, key, 24)

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d3, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d3)

        assertThrows<BadPickominoChosenException> {
            connect.takePickomino(id, key, 23)
        }

    }

    @Test
    fun testTakePickoWithoutWorms() {
    // Test vérifiant que l'on ne peut pas prendre de pickominos si on a pas de vers

        connect.choiceDices(id, key, listOf(DICE.d5, DICE.d5, DICE.d5, DICE.d5, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d5)
        connect.choiceDices(id, key, listOf(DICE.d3, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d3)

        assertThrows<BadStepException> {
            connect.takePickomino(id, key, 23)
        }
    }
}

class TestDices {
    private val nbPlayers = 2
    private val connect = Connector.factory("172.26.82.76", "8080", debug = true)
    private var id = 0
    private var key = 0

    @BeforeEach
    fun setup() {
        val identification = connect.newGame(nbPlayers)
        id = identification.first
        key = identification.second
    }

    @Test
    fun testAlreadyTakenDices() {
    // Test vérifiant que l'on ne puisse pas prendre de dés déja pris lors de ce tour

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d3, DICE.d1, DICE.d1, DICE.worm))
        assertThrows<DiceAlreadyKeptException> {
            connect.keepDices(id, key, DICE.worm)
        }
    }

    @Test
    fun testNotAlreadyTakenDices() {
    // Test vérifiant que lorsque l'on prends un dés que l'on a pas pris cela ne lance pas d'erreur

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d3, DICE.d1, DICE.d1, DICE.worm))
        assertDoesNotThrow {
            connect.keepDices(id, key, DICE.d3)
        }
    }

    @Test
    fun testPickNotPresentDices() {
    // Test vérifiant que l'on ne peut prendre de dés non-présent dans notre lancer

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d3, DICE.d1, DICE.d1, DICE.worm))
        assertThrows<DiceNotInRollException> {
            connect.keepDices(id, key, DICE.d4)
        }
    }
}

class TestEndGame {
    private val nbPlayers = 2
    private val connect = Connector.factory("172.26.82.76", "8080", debug = true)
    private var id = 0
    private var key = 0

    @BeforeEach
    fun setup() {
        val identification = connect.newGame(nbPlayers)
        id = identification.first
        key = identification.second
    }

    private fun dices() {
    // Création d'un fonction qui sera réutilisée afin de d'obtenir la valeur 36

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d1))
        connect.keepDices(id, key, DICE.d1)
    }

    private fun dices2() {
    // Création d'une fonction permettant de faire en sorte qu'il ne reste que le pickomino 21 dans la partie

        for (i in 36  downTo 22) {
            dices()
            connect.takePickomino(id, key, i)
        }
    }

    @Test
    fun testEndGame() {
    // Test vérifiant que lorsque la partie est finis le status s'update bien

        for (i in 36  downTo 21) {
            dices()
            connect.takePickomino(id, key, i)
        }

        assertEquals(connect.gameState(id, key).current.status, STATUS.GAME_FINISHED)
    }

    @Test
    fun testGameProperEnd() {
    // Test vérifiant la fin de la partie lorsqu'il n'y a plus de pickominos en jeu

        dices2()
        dices()
        assertDoesNotThrow {
            connect.takePickomino(id, key, 21)
        }

    }

    @Test
    fun testGameDoNotProperEnd() {
    // Test permettant de vérifier qu'une game ne se finit pas lorsqu'il reste des pickominos en jeu

        dices2()
        assertThrows<BadStepException> {
            connect.takePickomino(id, key, 21)
        }
    }
}

class TestPickos {
    private val nbPlayers = 2
    private val connect = Connector.factory("172.26.82.76", "8080", debug = true)
    private var id = 0
    private var key = 0

    @BeforeEach
    fun setup() {
        val identification = connect.newGame(nbPlayers)
        id = identification.first
        key = identification.second
    }

    private fun dices() {
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d1))
        connect.keepDices(id, key, DICE.d1)
    }

    @Test
    fun testPickosStackTop() {
    // Test vérifiant que lorsqu'un pickominos est pris il rends bien indisponible les pickominos recouverts

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d1, DICE.d1, DICE.worm))
        connect.keepDices(id, key, DICE.d4)
        connect.takePickomino(id, key, 24)
        // 24 en haut j0

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d3, DICE.d1, DICE.d1, DICE.worm))
        connect.keepDices(id, key, DICE.d3)
        connect.takePickomino(id, key, 23)
        // 23 en haut j1

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d1, DICE.d1, DICE.worm))
        connect.keepDices(id, key, DICE.d4)
        connect.takePickomino(id, key, 34)
        // 34 en haut j0 et 24 en dessous

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d1, DICE.d5, DICE.worm))
        connect.keepDices(id, key, DICE.d4)
        assertThrows<BadPickominoChosenException> {
            connect.takePickomino(id, key, 24)
        }
    }

    @Test
    fun testAccessiblePickos() {
    // Test permettant de vérifier que lorsque 36 est pris par un joueur il n'est plus disponible dans la pioche


        //connect.gameState(id, key).accessiblePickos()
        println(connect.gameState(id, key).accessiblePickos().toString())

        dices()
        //println("J1 prends 36")
        connect.takePickomino(id, key, 36)
        //println(connect.gameState(id, key).accessiblePickos().toString())

        assertEquals(false, connect.gameState(id, key).accessiblePickos().contains(36))
        // On veut tester que 36 n'est pas dans la liste des pickos
    }

    @Test
    fun testPickoGoBackToMiddleWhenNoWorms() {
    // Test vérifiant que lorsque l'on perds, car on obtient aucun vers parmis les dés, le dernier pickomino de la pile du joueur ayant perdu retourne au centre

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d5, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d5)
        connect.takePickomino(id, key, 25)

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d3, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d3)
        connect.takePickomino(id, key, 23)

        //connect.gameState(id, key).accessiblePickos()

        connect.choiceDices(id, key, listOf(DICE.d5, DICE.d5, DICE.d5, DICE.d5, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d5)
        connect.choiceDices(id, key, listOf(DICE.d3, DICE.d3, DICE.d3, DICE.d3))
        connect.keepDices(id, key, DICE.d3)

        assertEquals(false, connect.gameState(id, key).accessiblePickos().contains(36))
        //connect.gameState(id, key).accessiblePickos()

    }

    @Test
    fun testPickoGoBackToMiddleWhenOnlyDicesAlreadyOwned() {
    // Test vérifiant que lorsque l'on perds, car on obtient seulement des dés deja obtenus, le dernier pickomino de la pile du joueur ayant perdu retourne au centre

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d5, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d5)
        connect.takePickomino(id, key, 25)

        //connect.gameState(id, key).accessiblePickos()

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d5, DICE.d5, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d5)
        connect.takePickomino(id, key, 30)

        //connect.gameState(id, key).accessiblePickos()

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d5, DICE.d5, DICE.d5, DICE.d1))
        connect.keepDices(id, key, DICE.d5)
        connect.choiceDices(id, key, listOf(DICE.worm))

        assertEquals(false, connect.gameState(id, key).accessiblePickos().contains(36))
        //connect.gameState(id, key).accessiblePickos()
    }

    @Test
    fun testPickoSumNotSufficient() {
    // Test permettant de vérifier que la somme des dés pris ne soit pas suffisante pour pouvoir prendre un pickomino, et dans ce cas que 
    // le pickomino de la valeur la plus haute devienne non-disponibles

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d5, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d5)
        connect.takePickomino(id, key, 25)

        //connect.gameState(id, key).accessiblePickos()

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d5, DICE.d5, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d5)
        connect.takePickomino(id, key, 30)

        //connect.gameState(id, key).accessiblePickos()

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d1)

        //println(connect.gameState(id, key).current)
        assertEquals(false, connect.gameState(id, key).accessiblePickos().contains(36))
        //connect.gameState(id, key).accessiblePickos()

    }

    @Test
    fun testEveryPickoAreAvailableAtStart() {
    // Test vérifiant que tous les pickominos sont disponibles au début de la game

        for (i in 36 downTo 21) {
            assertEquals(true, connect.gameState(id, key).accessiblePickos().contains(i))
        }
    }

    @Test
    fun testHigherValuedPickoStillAvailableAfterALostTurn() {
    // Test vérifiant que lorsqu'un pickomino est renvoyé au centre, si c'est la valeur la plus élevée, elle reste disponible 

        //connect.gameState(id, key).accessiblePickos()
        dices()
        connect.takePickomino(id, key, 36)

        //connect.gameState(id, key).accessiblePickos()

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d5, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d5)
        connect.takePickomino(id, key, 25)

        //connect.gameState(id, key).accessiblePickos()

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d5, DICE.d5, DICE.d5, DICE.d1))
        connect.keepDices(id, key, DICE.d5)
        connect.choiceDices(id, key, listOf(DICE.worm))

        assertEquals(true, connect.gameState(id, key).accessiblePickos().contains(36))
        //connect.gameState(id, key).accessiblePickos()

    }

    @Test
    fun testNumberOfPlayerPileEqualsToPlayerNumber() {
    // Test vérifiant que le nombre de pile disponibles pour les pickominos quand ils sont récuperés par des joueurs est bien égal aux nombres de joueurs

        assertEquals(nbPlayers, connect.gameState(id, key).pickosStackTops().size)
    }

    @Test
    fun testStartGamePlayerPointsAreAt0() {
    // Test vérifiant que les joueurs commencent bien la partie a 0 points

        assertEquals(true, connect.gameState(id, key).pickosStackTops().contains(0))
    }

    @Test
    fun testPickoAreGoingToPlayerPile() {
    // Test vérifiant que les pickominos récuperé aillent bien dans la pile du joueur les aillant pris

        dices()
        connect.takePickomino(id, key, 36)
        assertEquals(true, connect.gameState(id, key).pickosStackTops().contains(36))
    }

    @Test
    fun testPickoRemoveFromPlayerPile() {
    // Test permettant de vérifier que les pickominos se retirent bien de la pile lors d'un tour perdu

        dices()
        connect.takePickomino(id, key, 36)

        dices()
        connect.takePickomino(id, key, 35)

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d1)

        assertEquals(true, connect.gameState(id, key).pickosStackTops().contains(0))
    }

    @Test
    fun testPickoRemovedFromPileWhenStolen() {
    // Test vérifiant que les pickominos volés se retirent bien de la pile du joueur volé

        dices()
        connect.takePickomino(id, key, 36)

        dices()
        connect.takePickomino(id, key, 36)

        assertEquals(true, connect.gameState(id, key).pickosStackTops().contains(0))
    }

}

class TestScore {
    private val nbPlayers = 2
    private val connect = Connector.factory("172.26.82.76", "8080", debug = true)
    private var id = 0
    private var key = 0

    @BeforeEach
    fun setup() {
        val identification = connect.newGame(nbPlayers)
        id = identification.first
        key = identification.second
    }
    private var oracle = listOf<Int>()

    // Fonction permettant d'obtenir la valeur 36 pour faciliter certains test
    private fun dices() {
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d1))
        connect.keepDices(id, key, DICE.d1)
    }

    // Fonction permettant de changer le nombre de joueurs
    private fun dices3() {
        for (i in nbPlayers-1 downTo 0) {
            oracle = oracle + 0
        }
    }

    @Test
    fun testScoreIsAt0OnStart() {
        // Test du score a 0 quand la partie commence

        //print(connect.gameState(id, key).score())
        //connect.gameState(id, key).score()
        dices3()
        assertEquals(oracle, connect.gameState(id, key).score())
    }

    @Test
    fun testScoreUpdatedWhenPickoPicked() {
        // Test Augmentation du score lorsqu'un joueur récupère un pickomino

        dices()
        connect.takePickomino(id, key, 36)
        assertEquals(4, connect.gameState(id, key).score()[0])
        //print(connect.gameState(id, key).score())

    }

    @Test
    fun testScoreDecreasingWhenLostTurn() {
        // Test du fait que le score descende quand le tour est perdu

        dices()
        // Choix du pickomino 36 par le joueur 1
        connect.takePickomino(id, key, 36)

        dices()
        // Choix du pickomino 35 par le joueur 2
        connect.takePickomino(id, key, 35)

        // Perte du tour par le joueur 1
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d1)

        // Vérification que le joueur 1 possède son score a 0
        assertEquals(0, connect.gameState(id, key).score()[0])

    }

    @Test
    fun testUpdatedScoreWhenPickoStolen() {
        // Test update du score quand un picko est volé

        dices()
        // Choix du pickomino 36 par le joueur 1
        connect.takePickomino(id, key, 36)

        dices()
        // Vol du pickomino 36 par le joueur 2
        connect.takePickomino(id, key, 36)

        // Vérification de fait que le joueur 0 possède un score de 0
        assertEquals(0, connect.gameState(id, key).score()[0])
    }

}

class TestStartGame {
    private val nbPlayers = 2
    private val connect = Connector.factory("172.26.82.76", "8080", debug = false)
    private var id = 0
    private var key = 0

    @BeforeEach
    fun setup() {
        val identification = connect.newGame(nbPlayers)
        id = identification.first
        key = identification.second
    }

    // Test de la bonne clé de game
    @Test
    fun testGoodGameKey() {
        assertNotNull(connect.gameState(id, key))
    }

    // Test de la mauvaise clé de game
    @Test
    fun testWrongGameKey() {
        key = 0
        assertThrows<IncorrectKeyException> {
            connect.gameState(id, key)
        }
    }


}
