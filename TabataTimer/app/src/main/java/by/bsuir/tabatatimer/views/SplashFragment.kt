package by.bsuir.tabatatimer.views

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.bsuir.tabatatimer.R

class SplashFragment: Fragment(R.layout.fragment_splash) {
    private val SPLASH_TIME_OUT: Long = 3000
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed ({
            val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
            findNavController().navigate(action)
        }, SPLASH_TIME_OUT)
    }
}