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
import view.activities.currentFragInMain
import view.activities.currentUserData
import view.activities.labsData

class StatisticsScreenBarChart : Fragment() {
    private var listDataByGroup = ArrayList<ArrayList<String>>()

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
        listData = putData(listData, findDistributionByWorks(), "Типы работ")

        val byScore = findDistributionByScore()
        for (data in byScore) {
            if (data.value != 0F) {
                listData = putData(listData, byScore, "Оценки")
                break
            }
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter =
            fragmentManager?.let { BarChartRecyclerAdapter(listData, it, listDataByGroup) }

        val buttonBar = view.findViewById<FloatingActionButton>(R.id.floatingActioButtonPieChart)
        buttonBar.setOnClickListener() {
            val pieChartScreen = StatisticsScreen()
            makeCurrentFragmentInMainWindow(pieChartScreen, "pieChartScreen")
        }
    }


    private fun putData(
        listData: ArrayMap<String, ArrayList<BarChartData>>,
        listUploadData: HashMap<Float, Float>,
        theme: String
    )
            : ArrayMap<String, ArrayList<BarChartData>> {
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
        var listDataThemes = ArrayList<String>()
        var listData = HashMap<Float, Float>()
        for (labs in labsData) {
            if (labs.classNumber == currentUserData.grade_level) {
                for (lab in labs.listLabs) {
                    if (!(lab.info.theme in listDataThemes)) {
                        listData.put((listData.size).toFloat(), 1F)
                        listDataThemes.add(lab.info.theme.toString())
                    } else {
                        val pos = listDataThemes.indexOf(lab.info.theme)
                        val value = listData[pos.toFloat()]
                        if (value != null) {
                            listData[pos.toFloat()] = value + 1F
                        } else {
                            listData[pos.toFloat()] = 1F
                        }
                    }
                }
                listDataByGroup.add(listDataThemes)
                return listData
            }
        }
        listDataByGroup.add(listDataThemes)
        return listData
    }

    private fun findDistributionByScore(): HashMap<Float, Float> {
        var listData = HashMap<Float, Float>()
        var listDataScore = ArrayList<String>()
        listDataScore.add("0")
        listDataScore.add("1")
        listDataScore.add("2")
        listDataScore.add("3")
        listDataScore.add("4")
        listDataScore.add("5")
        listData[0F] = 0F
        listData[1F] = 0F
        listData[2F] = 0F
        listData[3F] = 0F
        listData[4F] = 0F
        listData[5F] = 0F
        for (labs in currentUserData.finish_works) {
            val pos = listDataScore.indexOf(labs.value["score"].toString())
            val value = listData[pos.toFloat()]
            if (value != null) {
                listData[pos.toFloat()] = value + 1F
            } else {
                listData[pos.toFloat()] = 1F
            }
        }
        listDataByGroup.add(listDataScore)
        return listData
    }

    private fun findDistributionByWorks(): HashMap<Float, Float> {
        var listData = HashMap<Float, Float>()
        var listDataWorks = ArrayList<String>()
        listData[0F] = currentUserData.active_works.size.toFloat()
        listData[1F] = currentUserData.finish_works.size.toFloat()

        listDataWorks.add("Активные работы")
        listDataWorks.add("Завершенные работы")
        listDataByGroup.add(listDataWorks)
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