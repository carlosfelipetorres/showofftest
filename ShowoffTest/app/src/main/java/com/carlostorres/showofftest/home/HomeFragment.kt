package com.carlostorres.showofftest.home


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.carlostorres.client.domain.constants.INSTAGRAM_NAME
import com.carlostorres.showofftest.InstagramApplication
import com.carlostorres.showofftest.MainActivity
import com.carlostorres.showofftest.R
import com.carlostorres.showofftest.databinding.FragmentHomeBinding
import com.carlostorres.showofftest.home.adapters.PostsAdapter
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private var dialog: Dialog? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as InstagramApplication).getInstagramComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        binding.vm = ViewModelProviders.of(this, viewModelFactory)[HomeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[HomeViewModel::class.java]
        viewModel.command.observe(this, Observer {
            when (it) {
                is HomeViewModel.Command.Error -> (activity as MainActivity).showError(it.message)
                is HomeViewModel.Command.NameObtained -> setupPostsList()
                is HomeViewModel.Command.SessionClosed -> {
                    findNavController().popBackStack()
                    findNavController().navigate(R.id.splashFragment)
                }
            }
        })
        viewModel.getParam(INSTAGRAM_NAME)
        logout_button.setOnClickListener {
            LoginManager.getInstance().logOut()
            viewModel.closeSession()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupPostsList() {
        val adapter = PostsAdapter(viewModel.name)
        postsRecyclerView.layoutManager = LinearLayoutManager(context)
        postsRecyclerView.adapter = adapter
        viewModel.getPostsLists().observe(this, Observer { posts ->
            posts.let { list ->
                adapter.updateList(list)
            }
        })
    }

    override fun onDestroy() {
        dialog?.dismiss()
        super.onDestroy()
    }

}
