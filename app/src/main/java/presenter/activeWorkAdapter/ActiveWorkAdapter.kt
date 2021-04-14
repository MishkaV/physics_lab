package presenter.activeWorkAdapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.physics_lab.R
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import kotlinx.android.synthetic.main.recyclerview_item.view.labImage
import model.CurrentUserData
import model.Lab
import view.activities.*
import view.fragments.descriptionScreen.DescriptionScreen

class ActiveWorkAdapter(var currentUserData: CurrentUserData, var fragmentManager: FragmentManager): RecyclerView.Adapter<ActiveWorkAdapter.ActiveWorkHolder>() {
    private  var currLab = Lab()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveWorkHolder {
        val itemHolder = LayoutInflater
            .from(parent?.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return ActiveWorkHolder(itemHolder)
    }

    override fun getItemCount(): Int {
            return currentUserData.active_works.size
    }

    override fun onBindViewHolder(holder: ActiveWorkHolder, position: Int) {
            holder.itemView.labImage.setImageResource(R.drawable.background_start)
            holder.itemView.labName.text = currentUserData.active_works.keyAt(position)

            Log.d("ACTIVE_ADAPTER", position.toString())
            Log.d("ACTIVE_ADAPTER", "active: " + currentUserData.active_works.keyAt(position))

            for (labs in labsData) {
                if (labs.classNumber == currentUserData.grade_level) {
                    for (lab in labs.listLabs) {
                        if (currentUserData.active_works.keyAt(position) == lab.name) {
                            currLab = lab
                        }
                    }
                }
            }
            holder.itemView.labTheme.text = currLab.info.theme
//            holder.itemView.labDeadline.text = "Дедлайн сдачи: " + "20.12.2021"

            Log.d("ACTIVE_ADAPTER","find: " + currLab.name)
            holder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val descriptionScreen = DescriptionScreen()
                    val bundle = Bundle()

                    bundle.putString("labName", currentUserData.active_works.keyAt(position))
                    descriptionScreen.arguments = bundle
                    makeCurrentInActiveFragmentWindow(descriptionScreen, "descriptionScreen", fragmentManager)
                }
            })
    }
    class ActiveWorkHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    private fun makeCurrentInActiveFragmentWindow(
        fragment: Fragment,
        name: String,
        fragmentManager: FragmentManager
    ) {
        currentFragInMain = name
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.main_fragment, fragment)
            addToBackStack(name.toString())
            commit()
        }
    }
}