package view.fragments.verificationWorksScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.physics_lab.R
import model.LabData
import presenter.activeWorkAdapter.ActiveWorkAdapter

class VerificationWorksScreen : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verification_works_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var orientation = RecyclerView.VERTICAL
        var spanCount = 1

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewVerificationWork)
        val layoutManager = GridLayoutManager(requireContext(), spanCount, orientation, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = createAdapter()
    }

    private fun createAdapter(): RecyclerView.Adapter<*>? {
        val element = LabData()
        element.name = "Работа 4"
        element.deadline = "12:12:2000"
        element.image = R.drawable.background_start
        val list = arrayListOf<LabData>()
        for(i in 1..20){
            list.add(element)
        }
        return ActiveWorkAdapter(list)
    }
}
