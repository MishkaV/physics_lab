package presenter.descriptionAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.recyclerview_description.view.*


class DescriptionAdapter(var list: List<Model>, var context: Context, var type: String) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return list.size
    }

    @SuppressLint("ResourceType")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        var view: View? = null
        if (type == "active")
            view = layoutInflater.inflate(com.example.physics_lab.R.layout.recyclerview_description, container, false)
        else
            view = layoutInflater.inflate(com.example.physics_lab.R.layout.recyclerview_description_finish, container, false)

        view.descrTopic.text = list[position].title
        view.descrDescrp.text = list[position].description

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}