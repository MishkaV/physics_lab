package presenter.statisticsAdapter

import android.graphics.Color
import android.util.ArrayMap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.physics_lab.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.barchart_list.view.*
import kotlinx.android.synthetic.main.piechart_list.view.*

class BarChartRecyclerAdapter(
    var listData: ArrayMap<String, ArrayList<BarChartData>>,
    var fragmentManager: FragmentManager,
    var listDataByGroup: ArrayList<ArrayList<String>>
) : RecyclerView.Adapter<BarChartRecyclerAdapter.BarChartRecyclerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarChartRecyclerHolder {
        val itemHolder = LayoutInflater
            .from(parent?.context)
            .inflate(R.layout.barchart_list, parent, false)
        return BarChartRecyclerAdapter.BarChartRecyclerHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: BarChartRecyclerHolder, position: Int) {
        holder.itemView.barChart.data = createBarChart(position)
        holder.itemView.barChart.setFitBars(true)
        holder.itemView.barChart.animateY(1000)
        holder.itemView.barChart.description.text = ""

        Log.d("ADAPTER_", listDataByGroup[position].size.toString())
        Log.d("ADAPTER_", listDataByGroup[position].toString())

        when(listData.keyAt(position)){
           "Распределение по темам" -> {
               holder.itemView.barChart.xAxis.setLabelCount(listDataByGroup[0].size)
               holder.itemView.barChart.xAxis.valueFormatter = IndexAxisValueFormatter(listDataByGroup[0])
           }
            "Типы работ" -> {
               holder.itemView.barChart.xAxis.setLabelCount(listDataByGroup[1].size)
               holder.itemView.barChart.xAxis.valueFormatter = IndexAxisValueFormatter(listDataByGroup[1])
           }
            "Оценки" -> {
               holder.itemView.barChart.xAxis.setLabelCount(listDataByGroup[2].size)
               holder.itemView.barChart.xAxis.valueFormatter = IndexAxisValueFormatter(listDataByGroup[2])
           }
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }


    private fun createBarChart(position: Int): BarData {
        val data = ArrayList<BarEntry>()
        for (uploadData in listData.valueAt(position)) {
            data.add(BarEntry(uploadData.x, uploadData.y))
        }

        val barDataSet = BarDataSet(data, listData.keyAt(position))
        if (position % 2  == 0)
            barDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        else
            barDataSet.colors = ColorTemplate.JOYFUL_COLORS.toList()
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 16f

        return BarData(barDataSet)
    }

    class BarChartRecyclerHolder(view: View) : RecyclerView.ViewHolder(view) {
    }
}

class BarChartData{
    var x: Float = 0F
    var y: Float = 0F
}