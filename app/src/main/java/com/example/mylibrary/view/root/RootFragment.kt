package com.example.mylibrary.view.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import com.example.mylibrary.R
import com.example.mylibrary.common.TagConstant
import com.example.mylibrary.common.getColor
import com.example.mylibrary.databinding.FragmentRootBinding
import com.example.mylibrary.view.root.home.HomeFragment
import com.example.mylibrary.view.root.user.UserFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootFragment: Fragment() {
    private var _binding: FragmentRootBinding? = null
    val binding get() = _binding!!

    private lateinit var userFragment: UserFragment
    private lateinit var homeFragment: HomeFragment

    private val homeSetOnClickListener: (View) -> Unit = {
        SELECT = SELECTED_HOME
        setSelected()
        showHomeFragment()
    }

    private val userSetOnClickListener: (View) -> Unit = {
        SELECT = SELECTED_USER
        setSelected()
        userFragment.refresh()
        showUserFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initChildFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRootBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        setSelected()
    }

    private fun initChildFragment(){
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

    private fun setOnClickListeners(){
        binding.imgRootHome.setOnClickListener (homeSetOnClickListener)
        binding.imgRootUser.setOnClickListener (userSetOnClickListener)
    }

    private fun showHomeFragment() {
        childFragmentManager.beginTransaction().apply {
            show(homeFragment)
            hide(userFragment)
        }.commit()
    }

    private fun showUserFragment() {
        childFragmentManager.beginTransaction().apply {
            show(userFragment)
            hide(homeFragment)
        }.commit()
    }

    private fun setSelected(){
        when(SELECT){
            SELECTED_HOME ->{
                setImageTint(binding.imgRootHome, getColor(R.color.selected))
                setImageTint(binding.imgRootUser, getColor(R.color.black))
            }

            SELECTED_USER ->{
                setImageTint(binding.imgRootUser, getColor(R.color.selected))
                setImageTint(binding.imgRootHome, getColor(R.color.black))
            }
        }
    }

    private fun setImageTint(view: ImageView, colorId: Int) {
        DrawableCompat.setTint(
            DrawableCompat.wrap(view.drawable),
            colorId
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }


    companion object{
        const val SELECTED_HOME = 1
        const val SELECTED_USER = 2
        var SELECT = SELECTED_HOME
    }

}