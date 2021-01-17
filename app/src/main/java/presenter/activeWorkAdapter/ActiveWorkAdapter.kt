package presenter.activeWorkAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.physics_lab.R
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import model.CurrentUserData
import model.LabData
import view.activities.currentUserData
import view.activities.dataAboutLabs
import view.fragments.statisticsScreen.StatisticsScreen

class ActiveWorkAdapter(var currentUserData: CurrentUserData, var fragmentManager: FragmentManager): RecyclerView.Adapter<ActiveWorkAdapter.ActiveWorkHolder>() {
    lateinit var labsName: ArrayList<String>
    lateinit var labsTheme: ArrayList<String>
    lateinit var labsEquipment: ArrayList<String>
    lateinit var labsDesciption: ArrayList<String>

    init{
        setData()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveWorkHolder {
        val itemHolder = LayoutInflater
            .from(parent?.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return ActiveWorkHolder(itemHolder, fragmentManager)
    }

    override fun getItemCount(): Int {
        return labsName.size
    }

    override fun onBindViewHolder(holder: ActiveWorkHolder, position: Int) {
        holder.itemView.labImage.setImageResource(R.drawable.background_start)
        holder.itemView.labName.text = labsName[position]
        holder.itemView.labTheme.text = labsTheme[position]
        holder.itemView.labDeadline.text = "Дедлайн сдачи: " + "20.12.2021"
    }
    class ActiveWorkHolder(view: View, fragmentManager: FragmentManager) : RecyclerView.ViewHolder(view) {
        init{
            view.setOnClickListener() {
                val statisticsScreen = StatisticsScreen()
                makeCurrentInActiveFragmentWindow(statisticsScreen, "statisticsScreen", fragmentManager)
            }
        }
        private fun makeCurrentInActiveFragmentWindow(fragment: Fragment, name: String, fragmentManager: FragmentManager) {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.main_fragment, fragment)
                addToBackStack(name.toString())
                commit()
            }
        }
     }

    private fun setData(){
        when(currentUserData.grade_level){
            "7" -> {
                labsName = dataAboutLabs.labs7Name
                labsTheme = dataAboutLabs.labs7Theme
                labsEquipment = dataAboutLabs.labs7Equipment
                labsDesciption = dataAboutLabs.labs7Description
            }
            "8" -> {
                labsName = dataAboutLabs.labs8Name
                labsTheme = dataAboutLabs.labs8Theme
                labsEquipment = dataAboutLabs.labs8Equipment
                labsDesciption = dataAboutLabs.labs8Description

            }
            "9" -> {
                labsName = dataAboutLabs.labs9Name
                labsTheme = dataAboutLabs.labs9Theme
                labsEquipment = dataAboutLabs.labs9Equipment
                labsDesciption = dataAboutLabs.labs9Description
            }
            else -> {
                Log.d("WTF", "IM HERE")
            }
        }
    }
}