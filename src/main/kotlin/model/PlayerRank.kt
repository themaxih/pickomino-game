package model

class PlayerRank(player: Player, rank: Int) {

    val player : Player

    val rank : Int

    init {
        this.player = player
        this.rank = rank
    }

}