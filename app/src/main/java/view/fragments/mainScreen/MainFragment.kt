package view.fragments.mainScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.physics_lab.R
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar
import kotlinx.android.synthetic.main.fragment_main.*
import view.activities.currentFragInMain
import view.fragments.StatisticsScreen.StatisticsScreen
import view.fragments.activeWorksScreen.ActiveWorksScreen
import view.fragments.finishWorksScreen.FinishWorksScreen
import view.fragments.meScreen.MeScreen

var BOTTOM_BAR: View? = null

class MainFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomBar = view.findViewById<ExpandableBottomBar>(R.id.bottom_navigation)
        bottomBar.select(R.id.ic_active_work)
        if (bottom_navigation != null) {
            BOTTOM_BAR = bottomBar
            val activeWorksScreen = ActiveWorksScreen()
            val finishWorksScreen = FinishWorksScreen()
            val statisticsScreen = StatisticsScreen()
            val meScreen = MeScreen()

            bottomBar.onItemSelectedListener = { view, menuItem ->
                when (menuItem.itemId) {
                    R.id.ic_active_work -> {
                        makeCurrentFragmentInMainWindow(activeWorksScreen, "activeWorksScreen")
                    }
                    R.id.ic_finish_work -> {
                        makeCurrentFragmentInMainWindow(finishWorksScreen, "finishWorksScreen")
                    }
                    R.id.ic_on_verification_works -> {
                        makeCurrentFragmentInMainWindow(statisticsScreen, "statisticsScreen")
                    }
                    R.id.ic_user -> {
                        makeCurrentFragmentInMainWindow(meScreen, "meScreen")
                    }
                }
                true
            }
        }

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
