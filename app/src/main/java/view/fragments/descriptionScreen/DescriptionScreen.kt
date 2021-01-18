package view.fragments.descriptionScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.physics_lab.R
import kotlinx.android.synthetic.main.fragment_description_screen.*
import presenter.activeWorkAdapter.descriptionAdapter.DescriptionAdapter
import presenter.activeWorkAdapter.descriptionAdapter.Model
import view.activities.dataAboutLabs

class DescriptionScreen : Fragment() {
    lateinit var labName: String
    lateinit var labsTheme: String
    lateinit var labsEquipment: String
    lateinit var labsDesciption: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null)
        {
            labName = bundle.getString("labName").toString()
            val userClass = bundle.getString("userClass")

            when(userClass){
                "7" -> {
                    val position = dataAboutLabs.labs7Name.indexOf(labName)
                    labsTheme = dataAboutLabs.labs7Theme[position]
                    labsEquipment = dataAboutLabs.labs7Equipment[position]
                    labsDesciption = dataAboutLabs.labs7Description[position]
                }
                "8" -> {
                    val position = dataAboutLabs.labs8Name.indexOf(labName)
                    labsTheme = dataAboutLabs.labs8Theme[position]
                    labsEquipment = dataAboutLabs.labs8Equipment[position]
                    labsDesciption = dataAboutLabs.labs8Description[position]

                }
                "9" -> {
                    val position = dataAboutLabs.labs9Name.indexOf(labName)
                    labsTheme = dataAboutLabs.labs9Theme[position]
                    labsEquipment = dataAboutLabs.labs9Equipment[position]
                    labsDesciption = dataAboutLabs.labs9Description[position]
                }
                else -> {
                    Log.d("WTF", "IM HERE")
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_description_screen, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = ArrayList<Model>()

        list.add(Model(
            "Какая тема?",
            labName
        ))

        list.add(Model(
            "О чем?",
                labsDesciption
        ))
        list.add(Model(
            "Что используем?",
            labsEquipment
        ))

        viewPager.adapter = context?.let { DescriptionAdapter(list, it) }

    }


}