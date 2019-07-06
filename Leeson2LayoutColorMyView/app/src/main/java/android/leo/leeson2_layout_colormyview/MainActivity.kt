package android.leo.leeson2_layout_colormyview

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setBoxListeners()
    }

    private fun setBoxListeners() {
        val listOfBox = listOf(
                box_one_text, box_two_text, box_three_text, box_four_text, box_five_text
        )

        for (it in listOfBox) {
            it.setOnClickListener {
                makeColorToBox(it)
            }
        }
    }

    private fun makeColorToBox(it: View) {
        when (it.id) {
            R.id.box_one_text -> it.setBackgroundColor(Color.DKGRAY)
            R.id.box_two_text -> it.setBackgroundColor(Color.GRAY)
            R.id.box_three_text -> it.setBackgroundResource(android.R.color.holo_green_light)
            R.id.box_four_text -> it.setBackgroundResource(android.R.color.holo_green_dark)
            R.id.box_five_text -> it.setBackgroundResource(android.R.color.holo_green_light)

            else -> it.setBackgroundColor(Color.LTGRAY)
        }
    }
}
