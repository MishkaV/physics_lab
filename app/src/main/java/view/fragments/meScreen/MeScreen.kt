package view.fragments.meScreen

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.physics_lab.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_description_screen.*
import kotlinx.android.synthetic.main.fragment_me_screen.*
import presenter.activeWorkAdapter.descriptionAdapter.DescriptionAdapter
import presenter.activeWorkAdapter.descriptionAdapter.Model
import presenter.activeWorkAdapter.meAdapter.MeAdapter
import presenter.activeWorkAdapter.statisticsAdapter.PieChartListAdapter
import view.activities.currentUserData
import view.fragments.startScreen.StartScreen


class MeScreen : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ME_SCREEN_CHEKER", currentUserData.name.toString())
        Log.d("ME_SCREEN_CHEKER", currentUserData.grade_level.toString())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val exitButton = view.findViewById<MaterialButton>(R.id.exitButton)
        val startScreen = StartScreen()
        val list = ArrayList<Model>()

        exitButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                FirebaseAuth.getInstance().signOut()
                makeCurrentFragmentMainWindow(startScreen, "startScreen")
            }
        })

        list.add(
            Model(
                "О себе:",
                "Имя: ${currentUserData.name}\n\n" +
                        "Фамилия: ${currentUserData.surname}\n\n" +
                        "Отчество: ${currentUserData.patronymic}"
            )
        )

        list.add(
            Model(
                "Где учусь:",
                "Класс: ${currentUserData.grade_level}\n\n" +
                        "Школа: ${currentUserData.place_work}\n"
            )
        )

        viewPagerMe.adapter = context?.let { MeAdapter(list, it) }
        createGraphViewPager(view)
    }

    private fun createGraphViewPager(view: View) {
        val pagerView = view.findViewById<ViewPager>(R.id.graphViewPager)
        val list = ArrayList<PieData>()

        for (i in 1..5)
        {
            list.add(createPieChart(view))
        }

        pagerView.adapter = context?.let { PieChartListAdapter(list, it) }
    }

    private fun createPieChart(view: View): PieData {
        val data = ArrayList<PieEntry>()
        data.add(PieEntry(5F, "Механика"))
        data.add(PieEntry(3F, "Квантовая механика"))
        data.add(PieEntry(2F, "Термодинамика"))

        val pieDataSet = PieDataSet(data, "Распределение работ")
        pieDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 16f

        return PieData(pieDataSet)
    }

    private fun makeCurrentFragmentMainWindow(fragment: Fragment, name: String) {
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.main_fragmnet_layout, fragment)
            addToBackStack(name.toString())
            commit()
        }
    }

}
