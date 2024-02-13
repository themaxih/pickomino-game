package model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class PlayerTest {
    companion object {
        @JvmStatic
        fun toStringTestProvider() : Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(1, "Léo", 26, 12),
                Arguments.of(2, "Samuel", 32, 5),
                Arguments.of(3, "Maxence", 24, 6),
                Arguments.of(4, "Loan", 35, 9),
                Arguments.of(5, "Jean-François", 29, 7),
                Arguments.of(6, "Jean-Michel", 30, 8),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("toStringTestProvider")
    fun toStringTest(id : Int, name : String, topPickomino : Int, score : Int) {
        val player = Player(id, name, CustomColor("","")) // On crée un joueur avec une couleur nulle car on ne l'utilise pas dans le test
        player.topPickomino = topPickomino // On modifie le topPickomino du joueur
        player.score = score // On modifie le score du joueur
        assertEquals(player.toString(), "Player(id=$id, name='$name', topPickomino=$topPickomino, score=$score)") // On vérifie que le toString() renvoie bien ce qu'on attend
    }
}