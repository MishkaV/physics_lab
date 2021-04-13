package view.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.physics_lab.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_statistics_screen.*
import presenter.activeWorkAdapter.statisticsAdapter.BarChartListAdapter
import presenter.activeWorkAdapter.statisticsAdapter.PieChartListAdapter


class StatisticsScreen : Fragment() {
    private var currentToast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        Для PieChart
        val pagerView = view.findViewById<ViewPager>(R.id.barChartViewPager)
        val list = ArrayList<PieData>()

        for (i in 1..5)
        {
            list.add(createPieChart(view))
        }

        pagerView.adapter = context?.let { PieChartListAdapter(list, it) }


//        Для BarChart
//        val pagerView = view.findViewById<ViewPager>(R.id.barChartViewPager)
//        val list = ArrayList<BarData>()
//
//        for (i in 1..5)
//        {
//            list.add(createBarChart(view))
//        }
//
//        pagerView.adapter = context?.let { BarChartListAdapter(list, it) }

    }


    private fun createPieChart(view: View): PieData {

        val pieChart = view.findViewById<PieChart>(R.id.pieChart)
        val data = ArrayList<PieEntry>()
        data.add(PieEntry(5F, "Механика"))
        data.add(PieEntry(3F, "Квантовая механика"))
        data.add(PieEntry(2F, "Термодинамика"))

        val pieDataSet = PieDataSet(data, "Распределение работ")
        pieDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 16f

        val pieData = PieData(pieDataSet)
        return pieData
//        pieChart.data = pieData
//        pieChart.description.isEnabled = false
//        pieChart.centerText = "Распределение"
//        pieChart.animateY(1000, Easing.EaseInOutExpo)
    }

/*
    private fun createBarChart(view: View): BarData {
        val barChart = view.findViewById<BarChart>(R.id.barChart)
        val data = ArrayList<BarEntry>()
        data.add(BarEntry(2014F,420F))
        data.add(BarEntry(2015F,720F))
        data.add(BarEntry(2015F,120F))
        data.add(BarEntry(2016F,320F))
        data.add(BarEntry(2016F,920F))
        data.add(BarEntry(2017F,220F))

        val barDataSet = BarDataSet(data, "Data")

        barDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 16f

        val barData = BarData(barDataSet)

        return barData
//        barChart.setFitBars(true)
//        barChart.data = barData
//        barChart.description.text = "Hello world"
//        barChart.animateY(1000)
    }
*/

}
