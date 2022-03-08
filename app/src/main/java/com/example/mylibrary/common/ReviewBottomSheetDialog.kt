package com.example.mylibrary.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mylibrary.DialogViewModel
import com.example.mylibrary.data.dto.BookInfo
import com.example.mylibrary.data.entity.firebase.book.Review
import com.example.mylibrary.data.entity.room.Book
import com.example.mylibrary.databinding.BottomsheetReviewBinding
import com.example.mylibrary.view.root.search.dto.ItemClickArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewBottomSheetDialog(private val bookInfo: BookInfo, private val viewModel: DialogViewModel) : BottomSheetDialogFragment() {

    private var _binding: BottomsheetReviewBinding? = null
    val binding get() = _binding!!

    private val reviewObserver: (List<Review?>) -> Unit = { review ->
        reviewAdapter.apply {
            content = review as MutableList<Review>
            notifyDataSetChanged()
        }
    }

    private val imageSendOnClickListener: (View) -> Unit = {
        binding.editBottomsheetReviewInput.run {
            if(this.text.toString() == ""){
                showToast(StringConstant.NO_NAME)
            } else{
                viewModel.setReview(bookInfo,this.text.toString())
                this.setText("")
            }
        }
    }

    private val reviewAdapter: ReviewAdapter by lazy{
        ReviewAdapter(reviewItemOnClickListener)
    }

    private val reviewItemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        when(args?.view?.id){

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expandFullHeight()
        initAdapter()
        initViews()
        observeData()
        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        binding.imgBottomsheetReviewInput.setOnClickListener (imageSendOnClickListener)
    }

    private fun initViews(){
        viewModel.getReview(bookInfo.isbn)
    }

    private fun observeData(){
        viewModel.review.observe(viewLifecycleOwner,reviewObserver)
    }

    private fun initAdapter(){
        binding.recycleBottomsheetReview.adapter = reviewAdapter
    }

    private fun expandFullHeight() {
        val bottomSheet = dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet) as View
        val behavior = BottomSheetBehavior.from<View>(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.skipCollapsed = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}