package presenter.statisticsAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import kotlinx.android.synthetic.main.piechart_list.view.*

class PieChartListAdapter(var list: List<PieData>, var context: Context) : PagerAdapter()  {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return list.size
    }

    @SuppressLint("ResourceType")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(com.example.physics_lab.R.layout.piechart_list, container, false)

        view.pieChart.data = list[position]
        view.pieChart.centerText = "Распределение"
        view.pieChart.animateY(1000, Easing.EaseInOutExpo)
        view.pieChart.description.isEnabled = false

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}