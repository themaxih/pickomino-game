package model

class Player(id : Int, name : String, customColor: CustomColor) {

    var id : Int
    private set

    var name : String
    private set

    var topPickomino : Int = 0

    var score : Int = 0

    val customColor : CustomColor

    val pickominoList : ArrayList<Int>

    init {
        this.id = id
        this.name = name
        this.customColor = customColor
        this.pickominoList = arrayListOf()
    }

    override fun toString(): String {
        return "Player(id=$id, name='$name', topPickomino=$topPickomino, score=$score)"
    }

}