package com.example.mylibrary.view.root.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.mylibrary.R
import com.example.mylibrary.common.*
import com.example.mylibrary.data.entity.firebase.User
import com.example.mylibrary.data.entity.room.Book
import com.example.mylibrary.data.entity.room.Category
import com.example.mylibrary.databinding.FragmentUserBinding
import com.example.mylibrary.databinding.ItemSearchBinding
import com.example.mylibrary.databinding.ItemUserBookBinding
import com.example.mylibrary.databinding.ItemUserCategoryBinding
import com.example.mylibrary.view.root.RootFragmentDirections
import com.example.mylibrary.view.root.search.dto.ItemClickArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()

    private val bookAdapter: UserBookAdapter by lazy {
        UserBookAdapter(bookItemOnClickListener, bookItemOnLongClickListener)
    }

    private val categoryAdapter: UserCategoryAdapter by lazy {
        UserCategoryAdapter(categoryItemOnClickListener, categoryItemOnLongClickListener)
    }

    private val bookItemOnLongClickListener: (ItemClickArgs?) -> Boolean = { args ->
        Navigation.findNavController(rootFrom1Depth().binding.root)
            .navigate(RootFragmentDirections.actionRootFragmentToEditCategoryFragment((args?.item as ItemUserBookBinding).book!!))
        true
    }

    private val bookItemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        BookDetailDialog(bookToBookInfo((args?.item as ItemUserBookBinding).book!!), viewModel).show(childFragmentManager,TagConstant.BOOK_DETAIL_DIALOG)
    }

    private val categoryItemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        when(args?.view?.id){
            R.id.constraint_iusercategory_container -> {
                if(KotPrefModel.loginMethod == "noAccount") {
                    if(args.position == 0) viewModel.getMyBook()
                    else viewModel.getMyCategoryBook((args.item as ItemUserCategoryBinding).category!!.category)
                }
                else{
                    if(args.position == 0) viewModel.getUserBook()
                    else viewModel.getUserCategoryBook((args.item as ItemUserCategoryBinding).category!!.category)
                }
            }
        }
    }

    private val categoryItemOnLongClickListener: (ItemClickArgs?) -> Boolean = { args ->
        DeleteCategoryDialog((args?.item as ItemUserCategoryBinding).category!!.category).show(childFragmentManager,TagConstant.DELETE_CATEGORY_DIALOG)
        true
    }

    private val bookObserver: (List<Book>) -> Unit = { book ->
        bookAdapter.apply {
            content = book as MutableList<Book>
            notifyDataSetChanged()
        }
    }

    private val categoryObserver: (List<Category>) -> Unit = { category ->
        categoryAdapter.apply {
            content = category
            notifyDataSetChanged()
        }
    }

    private val categoryAddOnClickListener: (View) -> Unit = {
        CreateCategoryDialog(viewModel).show(childFragmentManager, TagConstant.CREATE_CATEGORY_DIALOG)
    }

    private val userObserver: (User) -> Unit = { user ->
        binding.user = user
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        refresh()
        initAdapter()
        setOnClickListener()
    }

    private fun setOnClickListener(){
        binding.textUserAdd.setOnClickListener(categoryAddOnClickListener)
        binding.imgUserSetting.setOnClickListener { Navigation.findNavController(rootFrom1Depth().binding.root)
            .navigate(RootFragmentDirections.actionRootFragmentToSettingFragment()) }
    }

    fun refresh() {
        if(KotPrefModel.loginMethod == "noAccount"){
            viewModel.getMyBook()
            viewModel.getMyCategory()
        } else{
            viewModel.getUserInfo()
            viewModel.getUserCategory()
            viewModel.getUserBook()
        }
    }

    private fun observeData() {
        viewModel.user.observe(viewLifecycleOwner,userObserver)
        viewModel.book.observe(viewLifecycleOwner, bookObserver)
        viewModel.category.observe(viewLifecycleOwner, categoryObserver)
    }

    private fun initAdapter() {
        binding.recyclerUserBook.adapter = bookAdapter
        binding.recyclerUserCategory.adapter = categoryAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


//    private fun deleteBookMark(item: ItemUserBookBinding, position: Int) {
//        viewModel.deleteBook(item.book!!.isbn)
//        bookAdapter.apply {
//            content.removeAt(position)
//            notifyItemRemoved(position)
//            notifyItemRangeChanged(position,content.size)
//            if(content.isEmpty()) hideBookDetail()
//        }
//    }

//    private fun showBookDetail(args: ItemClickArgs) {
//        binding.textUserDesc.visibility = View.VISIBLE
//        binding.textUserTitle.visibility = View.VISIBLE
//        val book = (args.item as ItemUserBookBinding).book
//        binding.textUserTitle.text = filteringText(book!!.title)
//        binding.textUserDesc.text = filteringText(book.description)
//    }

}

