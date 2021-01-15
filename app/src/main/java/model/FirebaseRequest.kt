package model

import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import view.activities.currentUserData
import java.lang.Exception

class FirebaseRequest {
    fun createNewUser() {
        val storage = FirebaseFirestore.getInstance()
        val map = HashMap<String, Any>()
        map["name"] = currentUserData.name.toString()
        map["surname"] = currentUserData.surname.toString()
        map["patronymic"] = currentUserData.patronymic.toString()
        map["email"] = currentUserData.email.toString()
        map["password"] = currentUserData.password.toString()
        map["type"] = currentUserData.type.toString()
        map["place_work"] = currentUserData.place_work.toString()
        if (currentUserData.grade_level != null)
            map["grade_level"] = currentUserData.grade_level.toString()
        else
            map["grade_level"] = "NONE"

        storage.collection("Users").document(currentUserData.email.toString())
            .set(map)
            .addOnSuccessListener(object : OnSuccessListener<Void> {
                override fun onSuccess(p0: Void?) {
                    Log.d("FIRECLOUD_REQUEST", "Success")
                }
            })
            .addOnFailureListener(object : OnFailureListener {
                override fun onFailure(p0: Exception) {
                    Log.d("FIRECLOUD_REQUEST", p0.toString())
                }
            })
    }

    fun setCurrentUser() {
        val storage = FirebaseFirestore
            .getInstance()
            .document("Users/${currentUserData.email}")
            storage.get()
            .addOnSuccessListener(object : OnSuccessListener<DocumentSnapshot> {
                override fun onSuccess(p0: DocumentSnapshot?) {
                    if (p0 != null) {
                        if (p0.exists()) {
                            Log.d("SET_CURRENT_USER", "Success upload")
                            currentUserData.name = p0.get("email") as String
                            currentUserData.surname = p0.get("patronymic") as String
                            currentUserData.patronymic = p0.get("patronymic") as String
                            currentUserData.type = p0.get("type") as String
                            currentUserData.place_work = p0.get("place_work") as String
                            val gradel_level = p0.get("email") as String
                            if (gradel_level == "NONE")
                                currentUserData.grade_level = gradel_level
                            else
                                currentUserData.grade_level = null
                        }
                    }
                }
            })


    }
}