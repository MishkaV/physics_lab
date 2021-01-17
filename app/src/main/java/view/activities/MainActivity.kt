package view.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.physics_lab.R
import com.yalantis.guillotine.animation.GuillotineAnimation.GuillotineBuilder
import model.CurrentUserData
import model.DataAboutLabs
import model.FirebaseRequest
import view.fragments.loginScreen.LoginScreen
import view.fragments.mainScreen.MainFragment
import view.fragments.registrationScreen.RegistrationScreen1
import view.fragments.startScreen.StartScreen

var currentUserData = CurrentUserData()
val firebaseRequest = FirebaseRequest()
var dataAboutLabs = DataAboutLabs()

class MainActivity : AppCompatActivity() {
    private val RIPPLE_DURATION: Long = 250
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
