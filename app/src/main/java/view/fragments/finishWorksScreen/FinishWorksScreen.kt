package view.fragments.finishWorksScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.physics_lab.R
import view.activities.currentUserData
import view.activities.firebaseRequest
import view.fragments.finishWorksScreen.notReadyScreenFinish.NotReadyScreenFinish

class FinishWorksScreen : Fragment() {

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
        if (currentUserData.finish_works == null || currentUserData.finish_works!!.size == 0) {
            val notReadyScreenFinish = NotReadyScreenFinish()
            makeCurrentFragmentInMainWindow(notReadyScreenFinish, "notReadyScreenFinish")
        }
        else {
            var orientation = RecyclerView.VERTICAL
            var spanCount = 1

            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewFinishWork)
            val layoutManager = GridLayoutManager(requireContext(), spanCount, orientation, false)

            recyclerView.layoutManager = layoutManager
            firebaseRequest.setAdapter(recyclerView, fragmentManager)
        }
    }

    private fun makeCurrentFragmentInMainWindow(fragment: Fragment, name: String) {
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.main_fragment, fragment)
            addToBackStack(name.toString())
            commit()
        }
    }
}
