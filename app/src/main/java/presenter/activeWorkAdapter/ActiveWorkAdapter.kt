package presenter.activeWorkAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.physics_lab.R
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import model.LabData

class ActiveWorkAdapter(var data : List<LabData>): RecyclerView.Adapter<ActiveWorkAdapter.ActiveWorkHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveWorkHolder {
        val itemHolder = LayoutInflater
            .from(parent?.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return ActiveWorkHolder(itemHolder)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ActiveWorkHolder, position: Int) {
        data[position].image?.let { holder.itemView.labImage.setImageResource(it) }
        holder.itemView.labName.text = data[position].name
        holder.itemView.labDeadline.text = "Дедлайн сдачи: " + data[position].deadline
    }
    class ActiveWorkHolder(view: View) : RecyclerView.ViewHolder(view) {
     }
}