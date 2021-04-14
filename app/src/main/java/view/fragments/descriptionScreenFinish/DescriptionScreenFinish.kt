package view.fragments.descriptionScreenFinish

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.physics_lab.R
import kotlinx.android.synthetic.main.fragment_description_screen.*
import kotlinx.android.synthetic.main.fragment_description_screen_finish.*
import kotlinx.android.synthetic.main.recyclerview_item_finish_works.*
import presenter.descriptionAdapter.DescriptionAdapter
import presenter.descriptionAdapter.Model
import view.activities.currentUserData

class DescriptionScreenFinish : Fragment() {
    lateinit var labsScore: String
    lateinit var labsComment: String
    lateinit var labsTime: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null)
        {
            val labPosition = bundle.getInt("labPosition")
            labsScore = currentUserData.finish_works.valueAt(labPosition)["score"].toString()
            labsComment = currentUserData.finish_works.valueAt(labPosition)["comment"].toString()
            labsTime = currentUserData.finish_works.valueAt(labPosition)["time"].toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_description_screen_finish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = ArrayList<Model>()

        list.add(
            Model(
            "Оценка",
                labsScore
        )
        )

        list.add(
            Model(
            "Время выполнения",
            labsTime
        )
        )
        list.add(
            Model(
            "Комментарий системы",
            labsComment
        )
        )

        viewPagerFinish.adapter = context?.let { DescriptionAdapter(list, it, "finish") }
    }
}