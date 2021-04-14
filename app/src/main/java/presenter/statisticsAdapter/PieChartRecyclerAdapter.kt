package presenter.statisticsAdapter

import android.graphics.Color
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.physics_lab.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.piechart_list.view.*
import model.CurrentUserData
import presenter.finishWorkAdapter.FinishWorkAdapter

class PieChartRecyclerAdapter(var listData: ArrayMap<String, ArrayList<PieChartData>>, var fragmentManager: FragmentManager) : RecyclerView.Adapter<PieChartRecyclerAdapter.PieChartRecyclerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PieChartRecyclerHolder {
        val itemHolder = LayoutInflater
            .from(parent?.context)
            .inflate(R.layout.piechart_list, parent, false)
        return PieChartRecyclerAdapter.PieChartRecyclerHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: PieChartRecyclerHolder, position: Int) {
        holder.itemView.pieChart.data = createPieChart(position)
        holder.itemView.pieChart.centerText = listData.keyAt(position)
        holder.itemView.pieChart.animateY(1000, Easing.EaseInOutExpo)
        holder.itemView.pieChart.description.isEnabled = false
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    private fun createPieChart(position: Int): PieData {
        val data = ArrayList<PieEntry>()
        for (uploadData in listData.valueAt(position)) {
            data.add(PieEntry(uploadData.value, uploadData.description))
        }

        val pieDataSet = PieDataSet(data, "")
        if (position % 2  == 0)
            pieDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        else
            pieDataSet.colors = ColorTemplate.JOYFUL_COLORS.toList()
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 16f

        return PieData(pieDataSet)
    }

    class PieChartRecyclerHolder(view: View) : RecyclerView.ViewHolder(view) {
    }
}

class PieChartData{
    var value: Float = 0F
    var description: String? = null
}