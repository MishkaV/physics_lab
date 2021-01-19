package view.fragments.registrationScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup

import com.example.physics_lab.R
import com.google.android.material.button.MaterialButton
import view.activities.currentFragMain
import view.activities.currentUserData

class RegistrationScreen3 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_screen3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val registrationScreenTeacher = RegistrationScreenTeacher()
        val registrationScreenStudent = RegistrationScreenStudent()
        val buttonNext = view.findViewById<MaterialButton>(R.id.registrationButtonFrag3)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)


        buttonNext.setOnClickListener() {
            val selected = view.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            if (selected.text == "Учитель") {
                makeCurrentFragmentMainWindow(registrationScreenTeacher, "registrationScreenTeacher")
                currentUserData.type = "Учитель"
            } else {
                makeCurrentFragmentMainWindow(registrationScreenStudent, "registrationScreenStudent")
                currentUserData.type = "Ученик"
            }
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
