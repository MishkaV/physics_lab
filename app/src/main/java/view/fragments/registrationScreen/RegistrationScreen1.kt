package view.fragments.registrationScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.physics_lab.R
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_login_screen.*
import kotlinx.android.synthetic.main.fragment_registration_screen.*
import view.activities.currentFragMain
import view.activities.currentUserData

class RegistrationScreen1 : Fragment() {
    val registrationScreen2 = RegistrationScreen2()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonNext = view.findViewById<MaterialButton>(R.id.registrationButtonFrag1)

        buttonNext.setOnClickListener() {
            getNames()
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

    private fun getNames() {
        nameEditLayoutReg.error = null
        surnameEditLayoutReg.error = null
        patronymicEditLayoutReg.error = null
        if (!(nameEditTextReg?.text.toString() == ""
                    || surnameEditTextReg?.text.toString() == ""
                    || patronymicEditTextReg?.text.toString() == "")
        ) {
            currentUserData.name = nameEditTextReg?.text.toString()
            currentUserData.surname = surnameEditTextReg?.text.toString()
            currentUserData.patronymic = patronymicEditTextReg?.text.toString()
            makeCurrentFragmentMainWindow(registrationScreen2, "registrationScreen2")

        } else if (nameEditTextReg?.text.toString() == "") {
            nameEditLayoutReg.error = "Введите имя"
        } else if (surnameEditTextReg?.text.toString() == "") {
            surnameEditLayoutReg.error = "Введите фамилию"
        } else {
            patronymicEditLayoutReg.error = "Введите отчество"
        }
    }
}
