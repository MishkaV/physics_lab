package model

import android.util.ArrayMap

class CurrentUserData {
    var name: String? = null
    var surname: String? = null
    var patronymic: String? = null
    var email: String? = null
    var password: String? = null
    var type: String? = null
    var place_work: String? = null
    var grade_level: String? = null
    var active_works = ArrayMap<String, HashMap<String, String>>()
    var finish_works = ArrayMap<String, HashMap<String, String>>()
    var verification_works = ArrayMap<String, HashMap<String, String>>()
}