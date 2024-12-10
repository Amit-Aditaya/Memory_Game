package com.example.mymemory

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymemory.models.BoardSize
import com.example.mymemory.models.MemoryCard
import com.example.mymemory.models.MemoryGame
import com.example.mymemory.utils.DEFAULT_ICONS
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var memoryGame: MemoryGame
    private lateinit var adapter: MemoryBoardAdapter
    private lateinit var rvBoard : RecyclerView
    private  lateinit var tvNumbMoves : TextView
    private  lateinit var tvNumbPairs  : TextView
    private  lateinit var clRoot  : ConstraintLayout


    private  var boardSize : BoardSize = BoardSize.HARD


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvBoard = findViewById(R.id.rvBoard)
        tvNumbMoves = findViewById(R.id.tvNumbMoves)
        tvNumbPairs = findViewById(R.id.tvnumbPairs)
        clRoot = findViewById(R.id.clRoot)

        memoryGame = MemoryGame(boardSize)


        adapter =  MemoryBoardAdapter(this, boardSize, memoryGame.cards, object :MemoryBoardAdapter.CardClickListener{
            override fun onCardClicked(position: Int) {
              updateGameWithFlip(position)
            }
        })
        rvBoard.adapter = adapter
        rvBoard.setHasFixedSize(true)
        rvBoard.layoutManager = GridLayoutManager(this, boardSize.getWidth())
    }


    private fun updateGameWithFlip(position: Int) {

        if(memoryGame.haveWonGame()){
            Snackbar.make(clRoot, "You already won", Snackbar.LENGTH_LONG).show()
            return
        }
        if(memoryGame.isCardFaceUp(position)){
            Snackbar.make(clRoot, "Invalid Move", Snackbar.LENGTH_SHORT).show()
            return
        }


        if(memoryGame.flipCard(position)){
            tvNumbPairs.text = "Pairs: ${memoryGame.numbPairsFound}/ ${boardSize.getNumbPairs()}"
            if(memoryGame.haveWonGame()){
                Snackbar.make(clRoot, "You Won, Congratulations!!", Snackbar.LENGTH_LONG).show()

            }

        }
        tvNumbMoves.text = "Moves : ${memoryGame.getNumMoves()}"
        adapter.notifyDataSetChanged()

    }
}