package com.carlostorres.showofftest.splash


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.carlostorres.client.domain.constants.ACCESS_TOKEN
import com.carlostorres.showofftest.InstagramApplication
import com.carlostorres.showofftest.MainActivity
import com.carlostorres.showofftest.R
import com.carlostorres.showofftest.databinding.FragmentSplashBinding
import kotlinx.android.synthetic.main.fragment_splash.*
import javax.inject.Inject


class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private lateinit var viewModel: SplashViewModel
    private var versionCode: Int? =  null
    private var dialog: Dialog? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as InstagramApplication).getInstagramComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        binding.setLifecycleOwner(this)
        binding.vm = ViewModelProviders.of(this, viewModelFactory)[SplashViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[SplashViewModel::class.java]
        viewModel.command.observe(this, Observer {
            when (it) {
                is SplashViewModel.Command.UserToken -> navigateToNextScreen()
                is SplashViewModel.Command.Error -> (activity as MainActivity).showError(it.message)
            }
        })

        try {
            val pInfo = context?.packageManager?.getPackageInfo(activity?.packageName, 0)
            val versionName = pInfo?.versionName
            versionCode = pInfo?.versionCode
            version.text = "v.$versionName ($versionCode)"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        viewModel.getParam(ACCESS_TOKEN)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        dialog?.dismiss()
        super.onDestroy()
    }

    private fun navigateToNextScreen() {
        try {
            when {
                viewModel.token.isEmpty() -> init()
                else -> Handler().postDelayed({
                    findNavController().popBackStack()
                    findNavController().navigate(R.id.homeFragment) }, SPLASH_DELAY.toLong())
            }
        } catch (e: Exception) {}
    }

    private fun init() {
        logo?.alpha = 0.0f
        Handler().postDelayed({ animateLogo() }, ANIM_DELAY.toLong())
        Handler().postDelayed({ redirectFragment() }, SPLASH_DELAY.toLong())

    }

    private fun redirectFragment() {
        try {
            val extras = FragmentNavigatorExtras(
                logo to "logo"
            )
            findNavController().popBackStack()
            findNavController().navigate(R.id.loginFragment, null, null, extras)
        } catch (e: java.lang.Exception) {}
    }

    private fun animateLogo() {
        val scaleXAnimation = createObjectAnimator(
            logo, "scaleX", 0.0f, 1.0f,
            ANIM_DURATION.toLong()
        )
        val scaleYAnimation = createObjectAnimator(
            logo, "scaleY", 0.0f, 1.0f,
            ANIM_DURATION.toLong()
        )
        val alphaAnimation = createObjectAnimator(
            logo, "alpha", 0.0f, 1.0f,
            ANIM_DURATION.toLong()
        )
        val animatorSet = AnimatorSet()
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation)
        try {
            animatorSet.start()
        } catch (e: Exception) {
        }
    }

    companion object {
        private val ANIM_DELAY = 500
        private val ANIM_DURATION = 1000
        private val SPLASH_DELAY = 2500


        fun createObjectAnimator(
            view: View?, property: String, init: Float,
            end: Float, duration: Long
        ): ObjectAnimator {
            val scaleXAnimation = ObjectAnimator.ofFloat(view, property, init, end)
            scaleXAnimation.interpolator = AccelerateDecelerateInterpolator() as TimeInterpolator?
            scaleXAnimation.duration = duration
            return scaleXAnimation
        }
    }

}
