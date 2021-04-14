package presenter.meAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.recyclerview_about_me.view.*
import presenter.descriptionAdapter.Model

class MeAdapter(var list: List<Model>, var context: Context) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return list.size
    }

    @SuppressLint("ResourceType")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(com.example.physics_lab.R.layout.recyclerview_about_me, container, false)

        view.meTopic.text = list[position].title
        view.meDescrp.text = list[position].description

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}