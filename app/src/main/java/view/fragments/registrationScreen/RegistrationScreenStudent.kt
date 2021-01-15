package view.fragments.registrationScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.physics_lab.R
import com.google.android.material.button.MaterialButton

class RegistrationScreenStudent : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_screen_student, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val registrationScreen5 = RegistrationScreen5()
        val buttonNext = view.findViewById<MaterialButton>(R.id.registrationButtonFragStudent)

        buttonNext.setOnClickListener() {
            makeCurrentFragmentMainWindow(registrationScreen5, "registrationScreen5")
        }
    }

    private fun makeCurrentFragmentMainWindow(fragment: Fragment, name: String) {
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.main_fragmnet_layout, fragment)
            addToBackStack(name.toString())
            commit()
        }
    }
}
