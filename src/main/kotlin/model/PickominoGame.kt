package model

import iut.info1.pickomino.Connector
import iut.info1.pickomino.data.DICE
import iut.info1.pickomino.data.Game
import iut.info1.pickomino.data.STATUS
import iut.info1.pickomino.exceptions.BadPickominoChosenException
import iut.info1.pickomino.exceptions.DiceAlreadyKeptException
import iut.info1.pickomino.exceptions.PickominoException

class PickominoGame(connector : Connector) {

    private var connector : Connector

    private var gameId : Int = 0

    private var gameKey : Int = 0

    private var players : ArrayList<Player> = arrayListOf()

    private var customColors : ArrayList<CustomColor> = arrayListOf(CustomColor("bleue","#5b6ee1"),CustomColor("vert","#6abe30"),CustomColor("orange","#df7126"),CustomColor("violet","#9c2ec6"))

    private var gameStarted : Boolean = false

    init {
        this.connector = connector
    }

    fun startGame(playerNames : ArrayList<String>) {
        val identification = connector.newGame(playerNames.size)
        this.gameId = identification.first
        this.gameKey = identification.second

        players.clear()
        for(i in 0 until playerNames.size) {
            players.add(Player(i,playerNames[i],customColors[i]))
        }

        this.gameStarted = true
    }

    fun throwDices() : Boolean {
        if(getGame().current.status == STATUS.ROLL_DICE || getGame().current.status == STATUS.ROLL_DICE_OR_TAKE_PICKOMINO) {
            connector.rollDices(gameId,gameKey)
            reloadInformation()
            return true
        }
        return false
    }

    fun keepDices(de : DICE) : Boolean {
        return try {
            connector.keepDices(gameId,gameKey,de)
            true
        } catch(e : DiceAlreadyKeptException) {
            false
        }
    }

    fun takePickomino(pickomino: Int) : Boolean {
        return try {
            connector.takePickomino(gameId,gameKey,pickomino)
            reloadInformation()
            true
        } catch(e : PickominoException) {
            false
        }
    }

    fun getGame() : Game {
        return connector.gameState(gameId,gameKey)
    }


    fun getPlayers() : ArrayList<Player> {
        return players
    }

    fun getPlayerById(id : Int) : Player {
        return players[id]
    }

    fun reloadInformation() {
        for(i in 0 until players.size) {
            players[i].score = getGame().score()[i]
            players[i].topPickomino = getGame().pickosStackTops()[i]
        }
    }

    fun getRolls() : List<DICE> {
        return getGame().current.rolls
    }

    fun getKeptDices() : List<DICE> {
        return getGame().current.keptDices
    }

    fun getDiceScore() : Int {

        var score : Int = 0
        for(dice in getKeptDices()) {
            score += if(dice.name == "worm") {
                5
            } else {
                dice.ordinal + 1
            }
        }
        return score
    }

    fun getCurrentPlayer() : Player {
        return players[getGame().current.player]
    }

    fun clearPlayers() {
        this.players.clear()
    }

    fun getSortedByScore() : ArrayList<PlayerRank> {

        val playerRankList = arrayListOf<PlayerRank>()
        var rank = 1

        if(getGame().current.status == STATUS.GAME_FINISHED) {

            players.sortWith(PlayerRankComparator())

            for(player in players) {
                if(player.score != 0) {
                    playerRankList.add(PlayerRank(player,rank))
                    rank += 1
                    continue
                }
                playerRankList.add(PlayerRank(player,rank))
            }
        }

        return playerRankList
    }


}