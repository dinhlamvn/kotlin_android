package android.leo.lesson2_layoutaboutme

import android.content.Context
import android.databinding.DataBindingUtil
import android.leo.lesson2_layoutaboutme.databinding.ActivityMainBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val user : User = User("Dinh Lam", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.user = user

        binding.doneButton.setOnClickListener {
            addNickName(it)
        }
    }

    private fun addNickName(view : View) {

        binding.apply {
            user?.nickName = nicknameEdit.text.toString()
            invalidateAll()
            nicknameText.visibility = View.VISIBLE
            nicknameEdit.visibility = View.GONE
            doneButton.visibility = View.GONE
        }

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
