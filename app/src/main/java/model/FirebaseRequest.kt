package model

import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import view.activities.currentUserData
import java.lang.Exception

class FirebaseRequest {
    fun createNewUser(){
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
}