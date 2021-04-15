package view.fragments.registrationScreen

import android.os.Bundle
import android.util.ArrayMap
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.CurrentUserData
import view.activities.currentFragMain
import view.activities.currentUserData
import view.activities.firebaseRequest

class RegistrationScreenStudent : Fragment() {
    private var coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

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

        val buttonNext = view.findViewById<MaterialButton>(R.id.registrationButtonFragStudent)
        val spinnerSchool = view.findViewById<Spinner>(R.id.spinnerSchool)
        val spinnerClass = view.findViewById<Spinner>(R.id.spinnerClass)

        buttonNext.setOnClickListener() {
            currentUserData.place_work = spinnerSchool.selectedItem.toString()
            currentUserData.grade_level = spinnerClass.selectedItem.toString()
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
                            "Проверьте данные пользователя и подключение к интернету",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@addOnCompleteListener
                    }

                    val registrationScreen5 = RegistrationScreen5()
                    makeCurrentFragmentMainWindow(registrationScreen5, "registrationScreen5")

                    currentUserData.active_works = ArrayMap<String, HashMap<String, String>>()

                    coroutineScope.launch {
                        currentUserData.active_works = ArrayMap<String, HashMap<String, String>>()
                        currentUserData.finish_works = ArrayMap<String, HashMap<String, String>>()
                        firebaseRequest.getDataLabs()
                        firebaseRequest.createNewUser()
                    }
                    Log.d("registrationRequest", "Successfull!")
                }
                .addOnFailureListener {
                    Log.d("registrationRequest", it.toString())
                }
        }
    }
}
