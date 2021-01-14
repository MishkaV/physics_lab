package view.fragments.activeWorksScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.physics_lab.R
import com.yalantis.guillotine.animation.GuillotineAnimation.GuillotineBuilder


class ActiveWorksScreen : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_active_works_screen, container, false)
    }

}
