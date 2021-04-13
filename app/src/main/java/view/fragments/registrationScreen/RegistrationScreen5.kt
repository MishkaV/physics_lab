package view.fragments.registrationScreen

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import com.example.physics_lab.R
import com.google.android.material.button.MaterialButton
import view.activities.currentFragInMain
import view.activities.currentFragMain
import view.fragments.activeWorksScreen.ActiveWorksScreen
import view.fragments.mainScreen.MainFragment

class RegistrationScreen5 : Fragment() {
    companion object {
        fun newInstance(): RegistrationScreen5 =
            RegistrationScreen5()

        var drawableResList = listOf(
            R.drawable.ic_example_1,
            R.drawable.ic_example_2,
            R.drawable.ic_example_3,
            R.drawable.ic_example_4,
            R.drawable.ic_example_5,
            R.drawable.ic_example_6,
            R.drawable.ic_example_7,
            R.drawable.ic_example_8,
            R.drawable.ic_example_9,
            R.drawable.ic_example_10,
            R.drawable.ic_example_11,
            R.drawable.ic_example_12,
            R.drawable.ic_example_13,
            R.drawable.ic_example_14,
            R.drawable.ic_example_15,
            R.drawable.ic_example_16,
            R.drawable.ic_example_17,
            R.drawable.ic_example_18,
            R.drawable.ic_example_19,
            R.drawable.ic_example_20,
            R.drawable.ic_example_21,
            R.drawable.ic_example_22,
            R.drawable.ic_example_23,
            R.drawable.ic_example_24,
            R.drawable.ic_example_25
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_screen5, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTagView()

        val mainFragment = MainFragment()
        val activeWorksScreen = ActiveWorksScreen()
        val buttonNext = view.findViewById<MaterialButton>(R.id.registrationButtonFrag5)

        buttonNext.setOnClickListener() {
            makeCurrentFragmentMainWindow(mainFragment, "mainFragment")
            makeCurrentFragmentInMainWindow(activeWorksScreen, "activeWorksScreen")
        }

    }

    private fun initTagView() {
        val tags = mutableListOf<VectorDrawableTagItem>()
        val tagSphereView = view?.findViewById<com.magicgoop.tagsphere.TagSphereView>(R.id.tagView)
        for(id in drawableResList){
            getVectorDrawable(id)?.let {
                tags.add(VectorDrawableTagItem(it))
            }
        }
        tagSphereView?.addTagList(tags)
        tagSphereView?.setRadius(2.75f)
    }

    private fun getVectorDrawable(id: Int): Drawable? =
        ContextCompat.getDrawable(requireContext(), id)

    private fun makeCurrentFragmentMainWindow(fragment: Fragment, name: String) {
        currentFragMain = name
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.main_fragmnet_layout, fragment)
            addToBackStack(name.toString())
            commit()
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
