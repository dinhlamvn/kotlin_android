package android.leo.lesson1_firstapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var imageDice : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnRoll : Button = findViewById(R.id.btnRoll)
        btnRoll.setOnClickListener {
            roll()
        }

        imageDice = findViewById(R.id.imageDice)
    }

    private fun roll() {
        val randInt = Random().nextInt(6) + 1

        val drawable = when (randInt) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        imageDice.setImageResource(drawable)
    }
}
