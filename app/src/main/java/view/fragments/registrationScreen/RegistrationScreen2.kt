package view.fragments.registrationScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup

import com.example.physics_lab.R
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_registration_screen.*
import kotlinx.android.synthetic.main.fragment_registration_screen2.*
import view.activities.currentFragMain
import view.activities.currentUserData

class RegistrationScreen2 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_screen2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonNext = view.findViewById<MaterialButton>(R.id.registrationButtonFrag2)
        currentUserData.type = "Ученик"

        buttonNext.setOnClickListener() {
            getMailPassword()
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

    private fun getMailPassword() {
        mailEditLayoutReg.error = null
        passwordEditLayoutReg.error = null
        if (!(mailEditTextReg?.text.toString() == "" || passwordEditTextReg?.text.toString() == "")) {
            currentUserData.email = mailEditTextReg?.text.toString()
            currentUserData.password = passwordEditTextReg?.text.toString()

            val registrationScreenStudent =  RegistrationScreenStudent()
            makeCurrentFragmentMainWindow(registrationScreenStudent, "registrationScreenStudent")

        } else if (mailEditTextReg?.text.toString() == "") {
            mailEditLayoutReg.error = "Введите почту"
        } else {
            passwordEditLayoutReg.error = "Введите пароль"
        }
    }
}
