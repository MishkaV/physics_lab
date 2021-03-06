package view.fragments.registrationScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast

import com.example.physics_lab.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import view.activities.currentFragMain
import view.activities.currentUserData
import view.activities.firebaseRequest

class RegistrationScreenTeacher : Fragment() {
    val registrationScreen5 = RegistrationScreen5()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_screen4_teacher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonNext = view.findViewById<MaterialButton>(R.id.registrationButtonFragTeacher)
        val spinnerWork = view.findViewById<Spinner>(R.id.spinnerWork)

        buttonNext.setOnClickListener() {
            currentUserData.place_work = spinnerWork.selectedItem.toString()
            registrationRequest()
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

    private fun registrationRequest() {
        if (currentUserData.email != null && currentUserData.password != null) {
            FirebaseAuth
                .getInstance()
                .createUserWithEmailAndPassword(currentUserData.email!!, currentUserData.password!!)
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        Toast.makeText(
                            context,
                            "Извините, но такой пользователь уже существует",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@addOnCompleteListener
                    }
                    makeCurrentFragmentMainWindow(registrationScreen5, "registrationScreen5")
                    firebaseRequest.createNewUser()
                    Log.d("registrationRequest", "Successfull!")
                }
        }
    }
}
