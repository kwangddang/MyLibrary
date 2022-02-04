package com.example.mylibrary.view.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mylibrary.R
import com.example.mylibrary.common.TagConstant
import com.example.mylibrary.databinding.FragmentRootBinding
import com.example.mylibrary.view.root.home.HomeFragment
import com.example.mylibrary.view.root.user.UserFragment

class RootFragment: Fragment() {
    private var _binding: FragmentRootBinding? = null
    private val binding get() = _binding!!

    private lateinit var userFragment: UserFragment
    private lateinit var homeFragment: HomeFragment

    private val homeSetOnClickListener: (View) -> Unit = {
        childFragmentManager.beginTransaction().apply {
            show(homeFragment)
            hide(userFragment)
        }.commit()
    }

    private val userSetOnClickListener: (View) -> Unit = {
        childFragmentManager.beginTransaction().apply {
            show(userFragment)
            hide(homeFragment)
        }.commit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRootBinding.inflate(inflater,container,false)

        setChildFragmentTransaction()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        binding.imgRootHome.setOnClickListener (homeSetOnClickListener)
        binding.imgRootUser.setOnClickListener (userSetOnClickListener)
    }

    private fun setChildFragmentTransaction(){
        homeFragment = HomeFragment()
        userFragment = UserFragment()

        childFragmentManager.beginTransaction().apply {
            add(
                R.id.fragment_container_root,
                homeFragment,
                TagConstant.HOME_FRAGMENT
            )
            add(
                R.id.fragment_container_root,
                userFragment,
                TagConstant.USER_FRAGMENT
            )
            hide(userFragment)
        }.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}