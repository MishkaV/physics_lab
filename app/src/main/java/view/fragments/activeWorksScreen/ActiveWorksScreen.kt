package view.fragments.activeWorksScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.physics_lab.R
import model.LabData
import presenter.activeWorkAdapter.ActiveWorkAdapter
import view.activities.firebaseRequest


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var orientation = RecyclerView.VERTICAL
        var spanCount = 1

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewActiveWork)
        val layoutManager = GridLayoutManager(requireContext(), spanCount, orientation, false)

        recyclerView.layoutManager = layoutManager
        firebaseRequest.setAdapter(recyclerView, fragmentManager)
    }

    /*private fun createAdapter(): RecyclerView.Adapter<*>? {
        val element = LabData()
        element.name = "Работа 4"
        element.deadline = "12:12:2000"
        element.image = R.drawable.background_start
        val list = arrayListOf<LabData>()
        for(i in 1..20){
            list.add(element)
        }
        return fragmentManager?.let { ActiveWorkAdapter(list, it) }
    }

     */
}
