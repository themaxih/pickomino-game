package model

class PlayerRankComparator() : Comparator<Player> {


    override fun compare(player1: Player, player2: Player): Int {

        if(player1.score != player2.score) {
            return player2.score - player1.score
        } else {
            val maxPickominoPlayer1: Int = if(player1.pickominoList.maxOrNull() != null) {
                player1.pickominoList.maxOrNull() as Int
            } else {
                0
            }
            val maxPickominoPlayer2: Int = if(player2.pickominoList.maxOrNull() != null) {
                player2.pickominoList.maxOrNull() as Int
            } else {
                0
            }
            return maxPickominoPlayer2 - maxPickominoPlayer1
        }

    }

}