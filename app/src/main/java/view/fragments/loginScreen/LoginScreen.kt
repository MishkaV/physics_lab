package view.fragments.loginScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.physics_lab.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login_screen.*
import view.activities.currentUserData
import view.activities.firebaseRequest
import view.fragments.activeWorksScreen.ActiveWorksScreen
import view.fragments.loginScreen.resetPasswordScreen.ResetPasswordScreen
import view.fragments.mainScreen.MainFragment

class LoginScreen : Fragment() {
    val mainFragment = MainFragment()
    val activeWorksScreen = ActiveWorksScreen()
    val resetPasswordScreen = ResetPasswordScreen()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonLogin = view.findViewById<MaterialButton>(R.id.loginButtonFrag)
        val buttonReset = view.findViewById<MaterialButton>(R.id.resetButton)

        buttonLogin.setOnClickListener() {
            authorization()
        }
        buttonReset.setOnClickListener() {
            makeCurrentFragmentMainWindow(resetPasswordScreen, "resetPasswordScreen")
        }
    }

    fun authorization() {
        mailEditLayoutLog.error = null
        passwordEditLayoutLog.error = null
        if (!(mailEditTextLog?.text.toString() == "" || passwordEditTextLog?.text.toString() == "")) {
            val email = mailEditTextLog?.text.toString()
            val password = passwordEditTextLog?.text.toString()

            Log.d("AUTHORIZATION", email)
            Log.d("AUTHORIZATION", password)

            FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        Toast.makeText(
                            context,
                            "Извините, но набрана неверная комбинация логин/пароль",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@addOnCompleteListener
                    }
                    currentUserData.email = email
                    currentUserData.password = password
                    firebaseRequest.setCurrentUser()
                    makeCurrentFragmentMainWindow(mainFragment, "mainFragment")
                    makeCurrentFragmentInMainWindow(activeWorksScreen, "activeWorksScreen")
                    Log.d("AUTHORIZATION", "Successfull!")
                }
        } else if (mailEditTextLog?.text.toString() == "") {
            mailEditLayoutLog.error = "Введите почту"
        } else passwordEditLayoutLog.error = "Введите пароль"
    }

    private fun makeCurrentFragmentMainWindow(fragment: Fragment, name: String) {
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.main_fragmnet_layout, fragment)
            addToBackStack(name.toString())
            commit()
        }
    }

    private fun makeCurrentFragmentInMainWindow(fragment: Fragment, name: String) {
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.main_fragment, fragment)
            addToBackStack(name.toString())
            commit()
        }
    }
}
