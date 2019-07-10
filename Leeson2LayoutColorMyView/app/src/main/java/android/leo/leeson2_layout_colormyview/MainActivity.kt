package android.leo.leeson2_layout_colormyview

import android.databinding.BindingConversion
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.leo.leeson2_layout_colormyview.databinding.ActivityMainBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val box : Box = Box()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.box = box

        setBoxListeners()
    }

    private fun setBoxListeners() {
        val listOfBox = listOf(
                box_one_text, box_two_text, box_three_text, box_four_text, box_five_text,
                red_button, yellow_button, green_button
        )

        for (it in listOfBox) {
            it.setOnClickListener {
                makeColorToBox(it)
            }
        }
    }

    private fun makeColorToBox(view : View) {
        binding.apply {
            when (view.id) {
                R.id.box_one_text -> box?.color1 = android.R.color.darker_gray
                R.id.box_two_text -> box?.color2 = android.R.color.holo_orange_light
                R.id.box_three_text -> box?.color3 = android.R.color.holo_green_light
                R.id.box_four_text -> box?.color4 = android.R.color.holo_green_dark
                R.id.box_five_text -> box?.color5 = android.R.color.holo_green_light
                R.id.red_button -> box?.color3 = R.color.my_red
                R.id.yellow_button -> box?.color4 = R.color.my_yellow
                R.id.green_button -> box?.color5 = R.color.my_green

                else -> view.setBackgroundColor(Color.LTGRAY)
            }

            invalidateAll()
        }
    }
}
