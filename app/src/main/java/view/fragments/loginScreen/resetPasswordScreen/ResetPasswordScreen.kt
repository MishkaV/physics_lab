package view.fragments.loginScreen.resetPasswordScreen

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
import kotlinx.android.synthetic.main.fragment_reset_password_screen.*
import view.fragments.loginScreen.LoginScreen

class ResetPasswordScreen : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_password_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonReset = view.findViewById<MaterialButton>(R.id.resetButtonFrag)

        buttonReset.setOnClickListener() {
            resetPassword()
        }
    }

    fun resetPassword() {
        mailResetLayout?.error = null
        if (mailResetText?.text.toString() != "") {
            val email = mailResetText?.text.toString()

            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("RESET_PASSWORD", "Email sent.")
                        Toast.makeText(
                            context,
                            "Письмо успешно отправлено",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Извините, но такой почты не существует",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@addOnCompleteListener
                    }
                }
        } else
            mailResetLayout?.error = "Введите почту"
    }

}
