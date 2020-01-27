package com.carlostorres.showofftest.login


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.carlostorres.client.domain.constants.ACCESS_TOKEN
import com.carlostorres.client.domain.constants.FACEBOOK_ID
import com.carlostorres.showofftest.InstagramApplication
import com.carlostorres.showofftest.MainActivity
import com.carlostorres.showofftest.R
import com.carlostorres.showofftest.databinding.FragmentLoginBinding
import com.facebook.CallbackManager
import kotlinx.android.synthetic.main.fragment_login.*


import javax.inject.Inject
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import com.facebook.login.LoginManager


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private val callbackManager: CallbackManager = CallbackManager.Factory.create()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as InstagramApplication).getInstagramComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = this
        binding.vm = ViewModelProviders.of(this, viewModelFactory)[LoginViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[LoginViewModel::class.java]
        viewModel.command.observe(this, Observer {
            when (it) {
                is LoginViewModel.Command.Authenticated -> navigateToMain()
                is LoginViewModel.Command.Back -> findNavController().popBackStack()
                is LoginViewModel.Command.Error -> (activity as MainActivity).showError(it.message)
            }
        })

        viewModel.cleanPreferences()

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) { }

                override fun onCancel() {}

                override fun onError(exception: FacebookException) {}
            })

        login_button.setPermissions(listOf("email","instagram_basic","pages_show_list"))
        login_button.fragment = this

        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val token = loginResult.accessToken.token
                viewModel.saveParam(ACCESS_TOKEN, token)
                viewModel.saveParam(FACEBOOK_ID, loginResult.accessToken.userId)
            }

            override fun onCancel() {}

            override fun onError(exception: FacebookException) {
                Log.e("ERROR", exception.message.toString())
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }

    private fun navigateToMain() {
        findNavController().popBackStack()
        findNavController().navigate(R.id.homeFragment)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data)
    }

}