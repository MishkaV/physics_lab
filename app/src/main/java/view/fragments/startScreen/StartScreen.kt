package view.fragments.startScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.physics_lab.R
import view.activities.currentFragMain
import view.fragments.loginScreen.LoginScreen
import view.fragments.registrationScreen.RegistrationScreen1
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class StartScreen : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonLogin = view.findViewById<MaterialButton>(R.id.button_login)
        val buttonRegistration = view.findViewById<MaterialButton>(R.id.button_registration)

        buttonLogin.setOnClickListener() {
            val loginScreen = LoginScreen()
            makeCurrentFragmentMainWindow(loginScreen, "loginScreen")
        }

        buttonRegistration.setOnClickListener() {
            val registrationScreen = RegistrationScreen1()
            makeCurrentFragmentMainWindow(registrationScreen, "loginScreen")
        }
    }


    private fun makeCurrentFragmentMainWindow(fragment: Fragment, name: String) {
        currentFragMain = name
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.main_fragmnet_layout, fragment)
            addToBackStack(name.toString())
            commit()
        }
    }
}
