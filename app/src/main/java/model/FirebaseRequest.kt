package model

import android.util.ArrayMap
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import presenter.activeWorkAdapter.ActiveWorkAdapter
import view.activities.currentUserData
import view.activities.dataAboutLabs
import view.activities.labsData

class FirebaseRequest {
    fun createNewUser() {
        val storage = FirebaseFirestore.getInstance()
        val map = HashMap<String, Any>()
        var labsName = ArrayList<String>()
        map["name"] = currentUserData.name.toString()
        map["surname"] = currentUserData.surname.toString()
        map["patronymic"] = currentUserData.patronymic.toString()
        map["email"] = currentUserData.email.toString()
        map["password"] = currentUserData.password.toString()
        map["type"] = currentUserData.type.toString()
        map["place_work"] = currentUserData.place_work.toString()
        map["grade_level"] = currentUserData.grade_level.toString()
        val arrayCurrLab = ArrayMap<String, HashMap<String, String>>()


        for (num_class in labsData) {
            if (num_class.classNumber == currentUserData.grade_level.toString()) {
                for (lab in num_class.listLabs) {
                    val dataCurrLab = HashMap<String, String>()
                    dataCurrLab["name"] = lab.name.toString()
                    dataCurrLab["score"] = "0"
                    dataCurrLab["time"] = "00:00:00"
                    dataCurrLab["comment"] = ""
                    arrayCurrLab[lab.name.toString()] = dataCurrLab
                    labsName.add(lab.name.toString())
                }
            }
        }

        map["active_works"] = arrayCurrLab
        currentUserData.active_works.putAll(arrayCurrLab)

        map["verification_works"] = ArrayMap<String, HashMap<String, String>>()
        map["finish_works"] = ArrayMap<String, HashMap<String, String>>()


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

    suspend fun getUserData() =
        withContext(Dispatchers.Default) {
            val storage = FirebaseFirestore
                .getInstance()
                .document("Users/${currentUserData.email}")
            storage.get()
                .addOnSuccessListener(object : OnSuccessListener<DocumentSnapshot> {
                    override fun onSuccess(p0: DocumentSnapshot?) {
                        if (p0 != null) {
                            if (p0.exists()) {
                                Log.d("SET_CURRENT_USER", "Success upload")
                                currentUserData.name = p0.get("name") as String
                                currentUserData.surname = p0.get("surname") as String
                                currentUserData.patronymic = p0.get("patronymic") as String
                                currentUserData.type = p0.get("type") as String
                                currentUserData.place_work = p0.get("place_work") as String
                                currentUserData.grade_level = p0.get("grade_level") as String

                                var active_works = p0.get("active_works") as HashMap<*, *>
                                for (lab in active_works) {
                                    val dataUploadLab = HashMap<String, String>()
                                    var dataLab = lab.value as HashMap<*, *>
                                    dataUploadLab["name"] = dataLab["name"].toString()
                                    dataUploadLab["score"] = dataLab["score"].toString()
                                    dataUploadLab["time"] = dataLab["time"].toString()
                                    currentUserData.active_works.put(dataLab["name"].toString(), dataUploadLab)
                                }

                            }
                        }
                    }
                })
                .await()
            currentUserData
        }


    suspend fun getDataLabs() =
        withContext(Dispatchers.Default) {

            val storage = FirebaseFirestore
                .getInstance()
                .collection("labList")

            storage.get()
                .addOnSuccessListener { querySnapshot ->
                    querySnapshot.forEach { doc ->
                        val document = LabsDataFirebase()
                        document.classNumber = doc.id
                        val list = doc.data.toMutableMap()

                        for (lab in list) {
                            var curr_lab = Lab()
                            val labs_info = lab.value as MutableMap<*, *>?
                            curr_lab.name = lab.key

                            if (labs_info != null) {
                                for (i in labs_info) {
                                    when (i.key.toString()) {
                                        "name" -> curr_lab.info.name = i.value as String?
                                        "theme" -> curr_lab.info.theme = i.value as String?
                                        "description" -> curr_lab.info.description = i.value as String?
                                        "equipment" -> curr_lab.info.equipment = i.value as String?
                                    }
                                }
                                document.listLabs.add(curr_lab)
                            }
                        }
                        labsData.add(document)

                    }
                }
                .addOnFailureListener {
                    object : OnFailureListener {
                        override fun onFailure(p0: java.lang.Exception) {
                            Log.d("GET_DATA_LABS", "FAIL TO UPLOAD")
                        }
                    }
                }
                .await()
            labsData
        }

//
//    fun setAdapter(recyclerView: RecyclerView, fragmentManager: FragmentManager?) {
//        val storage = FirebaseFirestore
//            .getInstance()
//            .document("Users/${currentUserData.email}")
//        storage.get()
//            .addOnSuccessListener(object : OnSuccessListener<DocumentSnapshot> {
//                override fun onSuccess(p0: DocumentSnapshot?) {
//                    if (p0 != null) {
//                        if (p0.exists()) {
//                            Log.d("SET_CURRENT_USER", "Success upload")
//                            currentUserData.name = p0.get("name") as String
//                            currentUserData.surname = p0.get("surname") as String
//                            currentUserData.patronymic = p0.get("patronymic") as String
//                            currentUserData.type = p0.get("type") as String
//                            currentUserData.place_work = p0.get("place_work") as String
//                            currentUserData.active_works = p0.get("active_works") as ArrayList<String>?
//                            currentUserData.verification_works = p0.get("verification_works") as ArrayList<String>?
//                            currentUserData.finish_works = p0.get("finish_works") as ArrayList<String>?
//                            val gradel_level = p0.get("grade_level") as String
//                            if (gradel_level != "NONE")
//                                currentUserData.grade_level = gradel_level
//                            else
//                                currentUserData.grade_level = null
//                            recyclerView.adapter =
//                                fragmentManager?.let { ActiveWorkAdapter(currentUserData, it) }
//                        }
//                    }
//                }
//            })
//    }
}