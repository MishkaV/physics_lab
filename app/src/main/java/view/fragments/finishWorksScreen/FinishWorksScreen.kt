package view.fragments.finishWorksScreen

import android.os.Bundle
import android.util.ArrayMap
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.example.physics_lab.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import presenter.finishWorkAdapter.FinishWorkAdapter
import view.activities.currentUserData
import view.activities.firebaseRequest
import view.fragments.finishWorksScreen.notReadyScreenFinish.NotReadyScreenFinish

class FinishWorksScreen : Fragment() {
    private var coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_finish_works_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var orientation = RecyclerView.VERTICAL
        var spanCount = 1

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewFinishWork)
        val layoutManager = GridLayoutManager(requireContext(), spanCount, orientation, false)

        if (currentUserData.finish_works!!.size == 0) {
            val notReadyScreenFinish = NotReadyScreenFinish()
            makeCurrentFragmentInMainWindow(notReadyScreenFinish, "notReadyScreenFinish")
        }
        else {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = fragmentManager?.let { FinishWorkAdapter(currentUserData, it) }
        }

        val swipeLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeLayoutFinish)
        swipeLayout.setOnRefreshListener(object  : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                coroutineScope.launch {
                    currentUserData.active_works = ArrayMap<String, HashMap<String, String>>()
                    currentUserData.finish_works = ArrayMap<String, HashMap<String, String>>()
                    currentUserData =  firebaseRequest.getUserData()
                    if (currentUserData.finish_works.size == 0) {
                        val notReadyScreenFinish = NotReadyScreenFinish()
                        makeCurrentFragmentInMainWindow(notReadyScreenFinish, "notReadyScreenFinish")
                    }
                    else {
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
