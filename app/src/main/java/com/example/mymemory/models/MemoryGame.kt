package com.example.mymemory.models

import com.example.mymemory.utils.DEFAULT_ICONS

class  MemoryGame(private  val boardSize: BoardSize){


    var cards : List<MemoryCard>
    var numbPairsFound = 0
    private var numCardFlips = 0

    private var indexOfSingleSelectedCard :Int? = null

    init {
        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumbPairs())
        val randomizedImages = (chosenImages + chosenImages).shuffled()
        cards = randomizedImages.map { MemoryCard(it) }
    }

    fun flipCard(position: Int): Boolean {

        numCardFlips++

        var foundMatch = false

        val card = cards[position]

        if(indexOfSingleSelectedCard == null){
            restoreCards()
            indexOfSingleSelectedCard = position
        }else{
         foundMatch =  checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }


        card.isFaceUp = !card.isFaceUp
        return  foundMatch

    }

    private fun checkForMatch(position1: Int, position2: Int) : Boolean{
        if(cards[position1].identifier != cards[position2].identifier){
            return  false
        }
        cards[position1].isMatched = true
        cards[position2].isMatched = true
        numbPairsFound++
        return  true

    }

    private fun restoreCards() {
        for( card in cards){
            if(!card.isMatched){
                card.isFaceUp = false
            }

        }
    }

    fun haveWonGame(): Boolean {

        return  numbPairsFound == boardSize.getNumbPairs()

    }

    fun isCardFaceUp(position: Int): Boolean {
        return  cards[position].isFaceUp

    }

    fun getNumMoves(): Int {
        return numCardFlips/2


    }
}