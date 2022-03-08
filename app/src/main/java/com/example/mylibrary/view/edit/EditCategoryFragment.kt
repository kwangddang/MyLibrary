package com.example.mylibrary.view.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.mylibrary.common.KotPrefModel
import com.example.mylibrary.common.LoginMethodConstant
import com.example.mylibrary.common.TagConstant
import com.example.mylibrary.data.entity.room.Category
import com.example.mylibrary.databinding.FragmentEditCategoryBinding
import com.example.mylibrary.databinding.ItemUserCategoryBinding
import com.example.mylibrary.view.common.CreateCategoryDialog
import com.example.mylibrary.view.root.search.dto.ItemClickArgs
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditCategoryFragment: Fragment() {
    private var _binding: FragmentEditCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditCategoryViewModel by viewModels()

    private val navArgs: EditCategoryFragmentArgs by navArgs()

    private val adapter: EditCategoryAdapter by lazy {
        EditCategoryAdapter(itemOnClickListener)
    }

    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        if(KotPrefModel.loginMethod == LoginMethodConstant.NO_ACCOUNT)
            viewModel.setMyBookCategory((args?.item as ItemUserCategoryBinding).category!!.category, navArgs.book.isbn)
        else
            viewModel.setUserBookCategory((args?.item as ItemUserCategoryBinding).category!!.category, navArgs.book)

        Navigation.findNavController(binding.root).popBackStack()
    }

    private val categoryObserver: (List<Category>) -> Unit = { category ->
        showProperViews(category)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditCategoryBinding.inflate(inflater,container,false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        initViews()
        initAdapter()
        setOnClickListener()
    }

    private fun setOnClickListener(){
        binding.btnEditCategoryNocontent.setOnClickListener { CreateCategoryDialog(viewModel).show(childFragmentManager,TagConstant.CREATE_CATEGORY_DIALOG) }
        binding.textEditCategoryAdd.setOnClickListener { CreateCategoryDialog(viewModel).show(childFragmentManager,TagConstant.CREATE_CATEGORY_DIALOG) }
    }

    private fun observeData() {
        viewModel.category.observe(viewLifecycleOwner, categoryObserver)
    }

    private fun initViews() {
        if(KotPrefModel.loginMethod == LoginMethodConstant.NO_ACCOUNT)
            viewModel.getMyCategory()
        else
            viewModel.getUserCategory()
    }

    private fun initAdapter() {
        binding.recyclerEditCategoryCategory.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun showProperViews(category: List<Category>) {
        if (category.isEmpty()) {
            binding.textEditCategoryNocontentHead.visibility = View.VISIBLE
            binding.textEditCategoryNocontentSubhead.visibility = View.VISIBLE
            binding.btnEditCategoryNocontent.visibility = View.VISIBLE
        } else {
            binding.textEditCategoryNocontentHead.visibility = View.INVISIBLE
            binding.textEditCategoryNocontentSubhead.visibility = View.INVISIBLE
            binding.btnEditCategoryNocontent.visibility = View.INVISIBLE
            adapter.apply {
                content = category as MutableList<Category>
                notifyDataSetChanged()
            }
        }
    }
}