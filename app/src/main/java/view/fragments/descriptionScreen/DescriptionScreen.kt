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
import model.Lab
import presenter.activeWorkAdapter.descriptionAdapter.DescriptionAdapter
import presenter.activeWorkAdapter.descriptionAdapter.Model
import view.activities.currentUserData
import view.activities.dataAboutLabs
import view.activities.labsData

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
            Log.d("WTF", labName)
            var curr_lab = findLab(labName)
            if(curr_lab != null) {
                labsTheme = curr_lab.info.theme.toString()
                labsEquipment = curr_lab.info.equipment.toString()
                labsDesciption = curr_lab.info.description.toString()
            }
            else
                Log.d("WTF", "WTF")

        }

    }

    private fun findLab(labName: String): Lab? {


        for (num_class in labsData) {
            for (lab in num_class.listLabs){
                if (lab.name == labName)
                    return lab
            }
        }
        return null
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