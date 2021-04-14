package view.fragments.activeWorksScreen

import android.os.Bundle
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.physics_lab.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import presenter.activeWorkAdapter.ActiveWorkAdapter
import view.activities.currentUserData
import view.activities.firebaseRequest
import view.fragments.activeWorksScreen.notReadyScreenActive.NotReadyScreenActive


class ActiveWorksScreen : Fragment() {
    private var currentToast: Toast? = null
    private var coroutineScope = CoroutineScope(Dispatchers.Main.immediate)
    private val notReadyScreenActive = NotReadyScreenActive()

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

        if (currentUserData.active_works.size == 0) {
            makeCurrentFragmentInMainWindow(notReadyScreenActive, "notReadyScreenActive")
        }
        else {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = fragmentManager?.let { ActiveWorkAdapter(currentUserData, it) }
        }

        val swipeLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeLayoutActive)
        swipeLayout.setOnRefreshListener(object  : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                coroutineScope.launch {
                    currentUserData.active_works = ArrayMap<String, HashMap<String, String>>()
                    currentUserData.finish_works = ArrayMap<String, HashMap<String, String>>()
                    currentUserData =  firebaseRequest.getUserData()
                    if (currentUserData.active_works.size == 0) {
                        makeCurrentFragmentInMainWindow(notReadyScreenActive, "notReadyScreenActive")
                    }
                    else {
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                    swipeLayout.isRefreshing = false
                }
            }

        })
            // createSpotlight(view)
    }
    /*
    private fun createSpotlight(view: View) {
        view.findViewById<com.getbase.floatingactionbutton.FloatingActionButton>(R.id.floatingActioButtonSpotlight).setOnClickListener {
            val targets = ArrayList<Target>()

            val firstRoot = FrameLayout(requireContext())
            val first = layoutInflater.inflate(R.layout.layout_target, firstRoot)
            val firstTarget = Target.Builder()
                .setAnchor(200f, 600f)
                .setShape(Circle(100f))
                .setEffect(RippleEffect(100f, 200f, Color.argb(30, 124, 255, 90)))
                .setOverlay(first)
                .setOnTargetListener(object : OnTargetListener {
                    override fun onStarted() {
                        Toast.makeText(context, "first target is started", Toast.LENGTH_SHORT).show()
                    }
                    override fun onEnded() {
                        Toast.makeText(context, "first target is ended", Toast.LENGTH_SHORT).show()
                    }
                })
                .build()

            targets.add(firstTarget)

            val secondRoot = FrameLayout(requireContext())
            val second = layoutInflater.inflate(R.layout.layout_target, secondRoot)
            if(BOTTOM_BAR != null) {

                val secondTarget = Target.Builder()
                    .setAnchor(BOTTOM_BAR!!)
                    .setShape(Circle(100f))
                    .setEffect(RippleEffect(100f, 200f, Color.argb(30, 124, 255, 90)))
                    .setOverlay(first)
                    .setOnTargetListener(object : OnTargetListener {
                        override fun onStarted() {
                            Toast.makeText(context, "first target is started", Toast.LENGTH_SHORT).show()
                        }

                        override fun onEnded() {
                            Toast.makeText(context, "first target is ended", Toast.LENGTH_SHORT).show()
                        }
                    })
                    .build()

                targets.add(secondTarget)
            }


            val spotlight = Spotlight.Builder(requireActivity())
                .setTargets(targets)
                .setBackgroundColorRes(R.color.spotlightBackground)
                .setDuration(1000L)
                .setAnimation(DecelerateInterpolator(2f))
                .setOnSpotlightListener(object : OnSpotlightListener {
                    override fun onStarted() {
                        currentToast?.cancel()
                        currentToast = Toast.makeText(
                            requireContext(),
                            "spotlight is started",
                            Toast.LENGTH_SHORT
                        )
                        currentToast?.show()
                        floatingActioButtonSpotlight.isEnabled = false
                    }

                    override fun onEnded() {
                        currentToast?.cancel()
                        currentToast = Toast.makeText(
                            requireContext(),
                            "spotlight is ended",
                            Toast.LENGTH_SHORT
                        )
                        currentToast?.show()
                       // floatingActioButtonSpotlight.isEnabled = true
                    }
                })
                .build()

            spotlight.start()

            val nextTarget = View.OnClickListener { spotlight.next() }

            val closeSpotlight = View.OnClickListener { spotlight.finish() }

            first.findViewById<View>(R.id.close_target).setOnClickListener(nextTarget)
            second.findViewById<View>(R.id.close_target).setOnClickListener(nextTarget)

            first.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
            second.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        }
    }
*/
    private fun makeCurrentFragmentInMainWindow(fragment: Fragment, name: String) {
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.main_fragment, fragment)
            addToBackStack(name.toString())
            commit()
        }
    }
}
