package presenter.statisticsAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.github.mikephil.charting.data.BarData
import kotlinx.android.synthetic.main.barchart_list.view.*

class BarChartListAdapter (var list: List<BarData>, var context: Context) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return list.size
    }

    @SuppressLint("ResourceType")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(com.example.physics_lab.R.layout.barchart_list, container, false)

        view.barChart.data = list[position]
        view.barChart.setFitBars(true)
        view.barChart.animateY(1000)

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}