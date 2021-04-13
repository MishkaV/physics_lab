package view.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.physics_lab.R
import com.google.firebase.auth.FirebaseAuth
import com.skydoves.transformationlayout.onTransformationStartContainer
import com.yalantis.guillotine.animation.GuillotineAnimation.GuillotineBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.CurrentUserData
import model.DataAboutLabs
import model.FirebaseRequest
import model.LabsDataFirebase
import view.fragments.StatisticsScreen
import view.fragments.activeWorksScreen.ActiveWorksScreen
import view.fragments.activeWorksScreen.notReadyScreenActive.NotReadyScreenActive
import view.fragments.descriptionScreen.DescriptionScreen
import view.fragments.finishWorksScreen.FinishWorksScreen
import view.fragments.loginScreen.LoginScreen
import view.fragments.loginScreen.resetPasswordScreen.ResetPasswordScreen
import view.fragments.mainScreen.MainFragment
import view.fragments.registrationScreen.RegistrationScreen1
import view.fragments.registrationScreen.RegistrationScreen2
import view.fragments.registrationScreen.RegistrationScreen5
import view.fragments.registrationScreen.RegistrationScreenStudent
import view.fragments.startScreen.StartScreen
import view.fragments.verificationWorksScreen.VerificationWorksScreen

var currentUserData = CurrentUserData()
val firebaseRequest = FirebaseRequest()
var dataAboutLabs = DataAboutLabs()
var currentFragMain: String? = null
var currentFragInMain: String? = null
var labsData = ArrayList<LabsDataFirebase>()

lateinit var activeWorksScreen: ActiveWorksScreen

class MainActivity : AppCompatActivity() {
    lateinit var startScreen: StartScreen
    lateinit var loginScreen: LoginScreen
    lateinit var resetPasswordScreen: ResetPasswordScreen
    lateinit var registrationScreen1: RegistrationScreen1
    lateinit var registrationScreen2: RegistrationScreen2
    lateinit var registrationScreenStudent: RegistrationScreenStudent
    lateinit var registrationScreen5: RegistrationScreen5
    lateinit var mainScreen: MainFragment
    lateinit var verificationWorksScreen: VerificationWorksScreen
    lateinit var finishWorksScreen: FinishWorksScreen
    lateinit var descriptionScreen: DescriptionScreen
    lateinit var statisticsScreen: StatisticsScreen
    var coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startScreen = StartScreen()
        loginScreen = LoginScreen()
        registrationScreen1 = RegistrationScreen1()
        registrationScreen2 = RegistrationScreen2()
        registrationScreenStudent = RegistrationScreenStudent()
        registrationScreen5 = RegistrationScreen5()
        mainScreen = MainFragment()
        activeWorksScreen = ActiveWorksScreen()
        verificationWorksScreen = VerificationWorksScreen()
        finishWorksScreen = FinishWorksScreen()
        descriptionScreen = DescriptionScreen()
        statisticsScreen = StatisticsScreen()


        if (savedInstanceState != null) {
            currentFragInMain = savedInstanceState.getString("currentFragInMain")
            currentFragMain = savedInstanceState.getString("currentFragMain")

            Log.d("IN_IN", "IN")
            if (currentFragMain != null) {
                Log.d("IN_IN_IN", "IN")
                when (currentFragMain) {
                    "startScreen" -> {
                        makeCurrentFragmentMainWindow(startScreen, "startScreen")
                    }
                    "loginScreen" -> {
                        makeCurrentFragmentMainWindow(loginScreen, "loginScreen")
                    }
                    "resetPasswordScreen" -> {
                        makeCurrentFragmentMainWindow(resetPasswordScreen, "resetPasswordScreen")
                    }
                    "registrationScreen1" -> {
                        makeCurrentFragmentMainWindow(registrationScreen1, "registrationScreen1")
                    }
                    "registrationScreen2" -> {
                        makeCurrentFragmentMainWindow(registrationScreen2, "registrationScreen2")
                    }
                    "registrationScreenStudent" -> {
                        makeCurrentFragmentMainWindow(registrationScreenStudent, "registrationScreenStudent")
                    }
                    "registrationScreen5" -> {
                        makeCurrentFragmentMainWindow(registrationScreen5, "registrationScreen5")
                    }
                    "mainScreen" -> {
                        if (currentFragInMain != null) {
                            when (currentFragInMain) {
                                "activeWorksScreen" -> {
                                    makeCurrentFragmentMainWindow(mainScreen, "mainScreen")
                                    makeCurrentFragmentInMainWindow(activeWorksScreen, "activeWorksScreen")
                                }
                                "verificationWorksScreen" -> {
                                    makeCurrentFragmentMainWindow(mainScreen, "mainScreen")
                                    makeCurrentFragmentInMainWindow(verificationWorksScreen, "verificationWorksScreen")
                                }
                                "finishWorksScreen" -> {
                                    makeCurrentFragmentMainWindow(mainScreen, "mainScreen")
                                    makeCurrentFragmentInMainWindow(finishWorksScreen, "finishWorksScreen")
                                }
                                "descriptionWorksScreen" -> {
                                    makeCurrentFragmentMainWindow(mainScreen, "mainScreen")
                                    makeCurrentFragmentInMainWindow(descriptionScreen, "descriptionScreen")
                                }

                            }
                        }
                    }

                }
            }
        }else {
            if (FirebaseAuth.getInstance().currentUser == null)
                makeCurrentFragmentMainWindow(startScreen, "startScreen")
            else {
                Log.d("MAIN_ACTIVITY", "RESTORE_USER")
                coroutineScope.launch {
                    currentUserData.email = FirebaseAuth.getInstance().currentUser?.email
                    labsData = firebaseRequest.getDataLabs()
                    currentUserData =  firebaseRequest.getUserData()
                    makeCurrentFragmentMainWindow(mainScreen, "mainScreen")
                    makeCurrentFragmentInMainWindow(activeWorksScreen, "activeWorksScreen")
                }
            }
        }


    }


    private fun makeCurrentFragmentMainWindow(fragment: Fragment, name: String) {
        currentFragMain = name
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragmnet_layout, fragment)
            addToBackStack(name.toString())
            commit()
        }
    }

    private fun makeCurrentFragmentInMainWindow(fragment: Fragment, name: String) {
        currentFragInMain = name
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment, fragment)
            addToBackStack(name.toString())
            commit()
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.button_login -> makeCurrentFragmentMainWindow(loginScreen, "loginScreen")
            R.id.button_registration -> makeCurrentFragmentMainWindow(
                registrationScreen1,
                "registrationScreen1"
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState?.run {
            putString("currentFragMain", currentFragMain)
            putString("currentFragInMain", currentFragInMain)
        }
        super.onSaveInstanceState(outState, outPersistentState)
    }


}
