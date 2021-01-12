package view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.physics_lab.R
import view.fragments.startScreen.StartScreen

class MainActivity : AppCompatActivity() {
    lateinit var startScreen: StartScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startScreen = StartScreen()
        makeCurrentFragmentMainWindow(startScreen, "startScreen")
    }


    private fun makeCurrentFragmentMainWindow(fragment: Fragment, name: String) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragmnet_layout, fragment)
            addToBackStack(name.toString())
            commit()
        }
    }
}
