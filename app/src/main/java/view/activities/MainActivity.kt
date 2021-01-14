package view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.physics_lab.R
import view.fragments.loginScreen.LoginScreen
import view.fragments.registrationScreen.RegistrationScreen1
import view.fragments.startScreen.StartScreen

class MainActivity : AppCompatActivity() {
    lateinit var startScreen: StartScreen
    lateinit var loginScreen: LoginScreen
    lateinit var registrationScreen: RegistrationScreen1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startScreen = StartScreen()
        loginScreen = LoginScreen()
        registrationScreen = RegistrationScreen1()

        makeCurrentFragmentMainWindow(startScreen, "startScreen")
    }


    private fun makeCurrentFragmentMainWindow(fragment: Fragment, name: String) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragmnet_layout, fragment)
            addToBackStack(name.toString())
            commit()
        }
    }

    fun onClick(view: View) {
        when(view.id) {
            R.id.button_login -> makeCurrentFragmentMainWindow(loginScreen, "loginScreen")
            R.id.button_registration -> makeCurrentFragmentMainWindow(registrationScreen, "registrationScreen")
        }
    }
}
