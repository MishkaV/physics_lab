package view.fragments.verificationWorksScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.physics_lab.R
import view.activities.currentFragInMain
import view.activities.currentFragMain
import view.activities.currentUserData
import view.activities.firebaseRequest
import view.fragments.verificationWorksScreen.notReadyScreenVer.NotReadyScreenVer

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
        println(currentUserData.verification_works)
        super.onViewCreated(view, savedInstanceState)
        if (currentUserData.finish_works == null || currentUserData.finish_works!!.size == 0) {
            val notReadyScreen = NotReadyScreenVer()
            makeCurrentFragmentInMainWindow(notReadyScreen, "notReadyScreen")
        }
        else {
            var orientation = RecyclerView.VERTICAL
            var spanCount = 1

            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewVerificationWork)
            val layoutManager = GridLayoutManager(requireContext(), spanCount, orientation, false)

            recyclerView.layoutManager = layoutManager
            //firebaseRequest.setAdapter(recyclerView, fragmentManager)
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
