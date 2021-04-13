package model

import java.util.*

class LabsDataFirebase {
    var classNumber: String? = null
    var listLabs = ArrayList<Lab>()
}

class Lab {
    var name: String? = null
    var info = Information()
}

class Information {
    var name: String? = null
    var theme: String? = null
    var description: String? = null
    var equipment: String? = null
}

