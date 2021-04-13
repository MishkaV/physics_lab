package view.fragments.finishWorksScreen.notReadyScreenFinish

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.physics_lab.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import view.activities.activeWorksScreen
import view.activities.currentUserData
import view.activities.finishWorksScreen
import view.activities.firebaseRequest

class NotReadyScreenFinish : Fragment() {
    private var coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_not_ready_screen_finish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val swipeLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeLayoutFinishNotReady)
        swipeLayout.setOnRefreshListener(object  : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                coroutineScope.launch {
                    currentUserData = firebaseRequest.getUserData()
                    if (currentUserData.finish_works != null) {
                        var orientation = RecyclerView.VERTICAL
                        var spanCount = 1

                        makeCurrentFragmentInMainWindow(finishWorksScreen, "finishWorksScreen")
                        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewActiveWork)
                        if (recyclerView != null)
                            recyclerView.adapter?.notifyDataSetChanged()
                    }
                    swipeLayout.isRefreshing = false
                }
            }

        })

    }

    private fun makeCurrentFragmentInMainWindow(fragment: Fragment, name: String) {
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.main_fragment, fragment)
            addToBackStack(name.toString())
            commit()
        }
    }

}