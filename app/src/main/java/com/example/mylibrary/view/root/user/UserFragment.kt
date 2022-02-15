package com.example.mylibrary.view.root.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.mylibrary.R
import com.example.mylibrary.common.CreateCategoryDialog
import com.example.mylibrary.common.TagConstant
import com.example.mylibrary.common.filteringText
import com.example.mylibrary.data.room.entity.Book
import com.example.mylibrary.data.room.entity.Category
import com.example.mylibrary.databinding.FragmentUserBinding
import com.example.mylibrary.databinding.ItemUserBookBinding
import com.example.mylibrary.databinding.ItemUserCategoryBinding
import com.example.mylibrary.view.root.RootFragment
import com.example.mylibrary.view.root.RootFragmentDirections
import com.example.mylibrary.view.root.home.dto.ItemClickArgs
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
        Navigation.findNavController((parentFragment as RootFragment).binding.root)
            .navigate(RootFragmentDirections.actionRootFragmentToEditCategoryFragment((args?.item as ItemUserBookBinding).book!!.isbn))
        true
    }

    private val bookItemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        when (args?.view?.id) {
            R.id.lottie_iuserbook_bookmark -> deleteBookMark(args.item as ItemUserBookBinding, args.position)
            R.id.card_iuserbook_innercontainer -> showBookDetail(args)
        }
    }

    private val categoryItemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        hideBookDetail()
        when(args?.view?.id){
            R.id.constraint_iusercategory_container -> {
                if(args.position == 0) viewModel.getMyBook()
                else viewModel.getCategoryBook((args.item as ItemUserCategoryBinding).category!!.category)
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
        val list = setDefaultItem(category)
        categoryAdapter.apply {
            content = list
            notifyDataSetChanged()
        }
    }

    private val categoryAddOnClickListener: (View) -> Unit = {
        CreateCategoryDialog().show(childFragmentManager, TagConstant.CREATE_CATEGORY_DIALOG)
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
    }

    fun refresh() {
        viewModel.getMyBook()
        viewModel.getCategory()
    }

    private fun observeData() {
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

    private fun hideBookDetail(){
        binding.textUserDesc.visibility = View.INVISIBLE
        binding.textUserTitle.visibility = View.INVISIBLE
    }

    private fun deleteBookMark(item: ItemUserBookBinding, position: Int) {
        viewModel.deleteBook(item.book!!.isbn)
        bookAdapter.apply {
            content.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,content.size)
            if(content.isEmpty()) hideBookDetail()
        }
    }

    private fun showBookDetail(args: ItemClickArgs) {
        binding.textUserDesc.visibility = View.VISIBLE
        binding.textUserTitle.visibility = View.VISIBLE
        val book = (args.item as ItemUserBookBinding).book
        binding.textUserTitle.text = filteringText(book!!.title)
        binding.textUserDesc.text = filteringText(book.description)
    }

    private fun setDefaultItem(category: List<Category>): MutableList<Category> {
        val list = category as MutableList<Category>
        list.add(0, Category("전체"))
        return list
    }
}

