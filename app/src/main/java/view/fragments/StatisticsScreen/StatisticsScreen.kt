package view.fragments.StatisticsScreen

import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.physics_lab.R
import com.getbase.floatingactionbutton.FloatingActionButton
import presenter.statisticsAdapter.PieChartData
import presenter.statisticsAdapter.PieChartRecyclerAdapter
import view.activities.currentFragInMain
import view.activities.currentUserData
import view.activities.labsData


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

        var orientation = RecyclerView.VERTICAL
        var spanCount = 1
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewStatisticScreen)
        val layoutManager = GridLayoutManager(requireContext(), spanCount, orientation, false)
        var listData = ArrayMap<String, ArrayList<PieChartData>>()

        listData = putData(listData, findDistributionByThemes(), "Распределение по темам")
        listData = putData(listData, findDistributionByWorks(), "Типы работ")

        if (findDistributionByScore().size != 0)
            listData = putData(listData, findDistributionByScore(), "Оценки")

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = fragmentManager?.let { PieChartRecyclerAdapter(listData, it) }


        val buttonBar = view.findViewById<FloatingActionButton>(R.id.floatingActioButtonBarChart)

        buttonBar.setOnClickListener() {
            val barChartScreen = StatisticsScreenBarChart()
            makeCurrentFragmentInMainWindow(barChartScreen, "barChartScreen")
        }
    }

    private fun putData(
        listData: ArrayMap<String,ArrayList<PieChartData>>,
        listUploadData : HashMap<String, Float>,
        theme : String)
    : ArrayMap<String, ArrayList<PieChartData>>
    {
        var arrayListPieData = ArrayList<PieChartData>()

        for (data in listUploadData) {
            var pieChartData = PieChartData()
            pieChartData.value = data.value
            pieChartData.description = data.key
            arrayListPieData.add(pieChartData)
        }
        listData.put(theme, arrayListPieData)

        return listData
    }

    private fun findDistributionByThemes(): HashMap<String, Float> {
        var listData = HashMap<String, Float>()
        for (labs in labsData) {
            if (labs.classNumber == currentUserData.grade_level) {
                for (lab in labs.listLabs) {
                    Log.d("CHECK_FIND", lab.info.theme.toString())
                    if (!listData.containsKey(lab.info.theme)) {
                        listData[lab.info.theme.toString()] = 1F
                    } else {
                        val value = listData[lab.info.theme.toString()]
                        if (value != null) {
                            listData[lab.info.theme.toString()] = value + 1F
                        } else
                            listData[lab.info.theme.toString()] = 1F
                    }
                }
                return listData
            }
        }
        return listData
    }

    private fun findDistributionByScore(): HashMap<String, Float> {
        var listData = HashMap<String, Float>()
        for (labs in currentUserData.finish_works) {
            if (!listData.containsKey(labs.value["score"])) {
                listData[labs.value["score"].toString()] = 1F
            } else {
                val value = listData[labs.value["score"].toString()]
                if (value != null) {
                    listData[labs.value["score"].toString()] = value + 1F
                } else
                    listData[labs.value["score"].toString()] = 1F
            }
        }
        return listData
    }

    private fun findDistributionByWorks(): HashMap<String, Float> {
        var listData = HashMap<String, Float>()
        listData["Активные работы"] = currentUserData.active_works.size.toFloat()
        listData["Завершенные работы"] = currentUserData.finish_works.size.toFloat()
        return listData
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

    private fun makeCurrentFragmentInMainWindow(fragment: Fragment, name: String) {
        currentFragInMain = name
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.main_fragment, fragment)
            addToBackStack(name.toString())
            commit()
        }
    }
}
