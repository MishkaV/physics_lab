package view.fragments.StatisticsScreen

import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.physics_lab.R
import com.getbase.floatingactionbutton.FloatingActionButton
import presenter.statisticsAdapter.BarChartData
import presenter.statisticsAdapter.BarChartRecyclerAdapter
import presenter.statisticsAdapter.PieChartData
import presenter.statisticsAdapter.PieChartRecyclerAdapter
import view.activities.currentFragInMain
import view.activities.currentUserData
import view.activities.labsData

class StatisticsScreenBarChart : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics_screen_bar_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var orientation = RecyclerView.VERTICAL
        var spanCount = 1

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewStatisticScreenBar)
        val layoutManager = GridLayoutManager(requireContext(), spanCount, orientation, false)

        var listData = ArrayMap<String, ArrayList<BarChartData>>()

        listData = putData(listData, findDistributionByThemes(), "Распределение по темам")
//        listData = putData(listData, findDistributionByWorks(), "Распределение по работам")

//        if (findDistributionByScore().size != 0)
//            listData = putData(listData, findDistributionByScore(), "Распределение по оценкам")

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = fragmentManager?.let { BarChartRecyclerAdapter(listData, it) }

        val buttonBar = view.findViewById<FloatingActionButton>(R.id.floatingActioButtonPieChart)
        buttonBar.setOnClickListener() {
            val pieChartScreen = StatisticsScreen()
            makeCurrentFragmentInMainWindow(pieChartScreen, "pieChartScreen")
        }
    }


    private fun putData(
        listData: ArrayMap<String,ArrayList<BarChartData>>,
        listUploadData : HashMap<Float, Float>,
        theme : String)
            : ArrayMap<String, ArrayList<BarChartData>>
    {
        var arrayListPieData = ArrayList<BarChartData>()

        for (data in listUploadData) {
            var pieChartData = BarChartData()
            pieChartData.x = data.key
            pieChartData.y = data.value
            arrayListPieData.add(pieChartData)
        }
        listData.put(theme, arrayListPieData)
        return listData
    }

    private fun findDistributionByThemes(): HashMap<Float, Float> {
        var listData = HashMap<Float, Float>()
        var listDataCheck = ArrayList<String>()
        for (labs in labsData) {
            if (labs.classNumber == currentUserData.grade_level) {
                for (lab in labs.listLabs) {
                    if (!(lab.info.theme in listDataCheck)) {
                        listData.put((listData.size - 1).toFloat(), 1F)
                        listDataCheck.add(lab.info.theme.toString())
                    } else {
                        val pos = listDataCheck.indexOf(lab.info.theme)
                        val value = listData[pos.toFloat()]
                        if (value != null) {
                            listData[pos.toFloat()] = value + 1F
                        }
                        else {
                            listData[pos.toFloat()] = 1F
                        }
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

    private fun makeCurrentFragmentInMainWindow(fragment: Fragment, name: String) {
        currentFragInMain = name
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.main_fragment, fragment)
            addToBackStack(name.toString())
            commit()
        }
    }
}