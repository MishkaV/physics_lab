package view.fragments.meScreen

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.physics_lab.R
import kotlinx.android.synthetic.main.fragment_description_screen.*
import kotlinx.android.synthetic.main.fragment_me_screen.*
import presenter.activeWorkAdapter.descriptionAdapter.DescriptionAdapter
import presenter.activeWorkAdapter.descriptionAdapter.Model
import presenter.activeWorkAdapter.meAdapter.MeAdapter
import view.activities.currentUserData


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
        val list = ArrayList<Model>()
        meHello.text = "Здравствуйте, " + currentUserData.name + "!"
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
    }


}
