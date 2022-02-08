package com.example.mylibrary.view.root.home

import android.animation.ValueAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.airbnb.lottie.LottieAnimationView
import com.example.mylibrary.R
import com.example.mylibrary.common.bookInfoToBook
import com.example.mylibrary.common.hideKeyboard
import com.example.mylibrary.data.dto.request.BookRequest
import com.example.mylibrary.data.dto.response.BookResponse
import com.example.mylibrary.data.room.dao.BookDao
import com.example.mylibrary.databinding.FragmentHomeBinding
import com.example.mylibrary.databinding.ItemHomeBinding
import com.example.mylibrary.view.root.home.dto.ItemClickArgs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private val adapter: HomeAdapter by lazy {
        HomeAdapter(itemOnClickListener)
    }

    private val searchEditActionListener: (TextView, Int, KeyEvent?) -> Boolean = { view, actionId, event ->
        when(actionId){
            EditorInfo.IME_ACTION_SEARCH -> {
                view.text.toString().run {
                    if(isNullOrBlank()) Toast.makeText(requireContext(),"검색어를 입력해 주세요.",Toast.LENGTH_SHORT).show()
                    else viewModel.getBook(BookRequest(query = this))
                }
                requireActivity().hideKeyboard(view as EditText)
                true
            }
            else -> false
        }
    }

    private val bookObserver: (BookResponse?) -> Unit = { response ->
        adapter.apply {
            item = response
            notifyDataSetChanged()
        }
    }

    @Inject
    lateinit var bookDao: BookDao

    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        when(args?.view?.id){
            R.id.text_ihome_link -> startActivity(Intent(Intent.ACTION_VIEW,Uri.parse((args?.item as ItemHomeBinding).book?.link)))

            R.id.lottie_ihome_bookmark -> setBookMark(args.view as LottieAnimationView, args.item as ItemHomeBinding)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        initAdapter()
        setOnEditorActionListener()
    }

    private fun observeData() {
        viewModel.book.observe(viewLifecycleOwner, bookObserver)
    }

    private fun setOnEditorActionListener() {
        binding.editHomeSearch.setOnEditorActionListener(searchEditActionListener)
    }

    private fun initAdapter() {
        binding.recyclerHome.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setBookMark(view: LottieAnimationView, item: ItemHomeBinding) {
        if (!(item.book?.isBookMark)!!) {
            val animator = getValueAnimator(0f,0.5f, view)
            animator.start()
            item.book?.isBookMark = true
            viewModel.insert(bookInfoToBook(item.book!!))
        } else {
            val animator = getValueAnimator(0.5f,0.0f, view)
            animator.start()
            item.book?.isBookMark = false
            viewModel.delete(item.book!!.isbn)
        }
    }

    private fun getValueAnimator(start: Float, end: Float, view: LottieAnimationView): ValueAnimator {
        return ValueAnimator.ofFloat(start, end).setDuration(500).apply {
            addUpdateListener {
                view.progress = it.animatedValue as Float
            }
        }
    }
}