package presenter.finishWorkAdapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.physics_lab.R
import kotlinx.android.synthetic.main.recyclerview_item_finish_works.view.*
import model.CurrentUserData
import model.Lab
import view.activities.currentFragInMain
import view.activities.labsData
import view.fragments.descriptionScreen.DescriptionScreen
import view.fragments.descriptionScreenFinish.DescriptionScreenFinish

class FinishWorkAdapter(var currentUserData: CurrentUserData, var fragmentManager: FragmentManager): RecyclerView.Adapter<FinishWorkAdapter.FinishWorkHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinishWorkHolder {
        val itemHolder = LayoutInflater
            .from(parent?.context)
            .inflate(R.layout.recyclerview_item_finish_works, parent, false)
        return FinishWorkHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: FinishWorkHolder, position: Int) {
        var currLab = Lab()
        holder.itemView.labImage.setImageResource(R.drawable.background_me2)
        holder.itemView.labName.text = currentUserData.finish_works.keyAt(position)

        for (labs in labsData) {
            if (labs.classNumber == currentUserData.grade_level) {
                for (lab in labs.listLabs) {
                    if (currentUserData.finish_works.keyAt(position) == lab.name) {
                        currLab = lab
                    }
                }
            }
        }

        holder.itemView.labTheme.text = currLab.info.theme
        holder.itemView.labScore.text = "Оценка: " + currentUserData.finish_works.valueAt(position)["score"]

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val descriptionScreenFinish = DescriptionScreenFinish()
                val bundle = Bundle()

                bundle.putInt("labPosition", position)
                descriptionScreenFinish.arguments = bundle
                makeCurrentInActiveFragmentWindow(descriptionScreenFinish, "descriptionScreenFinish", fragmentManager)
            }
        })
    }

    override fun getItemCount(): Int {
        return currentUserData.finish_works.size
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

    class FinishWorkHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

}