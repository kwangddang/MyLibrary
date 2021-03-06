package com.example.mylibrary.view.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.mylibrary.R
import com.example.mylibrary.util.KotPrefModel
import com.example.mylibrary.util.LoginMethodConstant
import com.example.mylibrary.util.ToastConstant
import com.example.mylibrary.util.showToast
import com.example.mylibrary.data.dto.BookInfo
import com.example.mylibrary.data.entity.firebase.Review
import com.example.mylibrary.databinding.BottomsheetReviewBinding
import com.example.mylibrary.databinding.ItemReviewBinding
import com.example.mylibrary.view.common.dto.ItemClickArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewBottomSheetDialog(private val bookInfo: BookInfo, private val viewModel: DialogViewModel) : BottomSheetDialogFragment() {

    private var _binding: BottomsheetReviewBinding? = null
    val binding get() = _binding!!

    private var reviewId = ""

    private val reviewAdapter: ReviewAdapter by lazy{
        ReviewAdapter(reviewItemOnClickListener)
    }

    private val reviewObserver: (List<Review?>) -> Unit = { review ->
        reviewAdapter.apply {
            content = review as MutableList<Review>
            notifyDataSetChanged()
        }
    }

    private val imageSendOnClickListener: (View) -> Unit = {
        binding.editBottomsheetReviewInput.run {
            when {
                KotPrefModel.loginMethod == LoginMethodConstant.NO_ACCOUNT -> showToast(ToastConstant.NO_ACCOUNT_REVIEW)

                this.text.toString() == "" -> {
                    showToast(ToastConstant.NO_NAME)
                }

                else -> {
                    viewModel.setReview(bookInfo,this.text.toString())
                    this.setText("")
                }
            }
        }
    }

    private val reviewItemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        when(args?.view?.id){
            R.id.img_ireview_more -> {
                reviewId = (args.item as ItemReviewBinding).review!!.reviewId
                if(args.item.review?.userId == viewModel.uid)
                   MorePopupMenu(requireContext(), args.view, menuItemClickListener).show()
                else
                    showToast(ToastConstant.CANT_ERASE)
            }
        }
    }

    private val menuItemClickListener: (item: MenuItem) -> Boolean = { item ->
        when (item.itemId) {
            R.id.popupmenu_more -> viewModel.deleteReview(bookInfo.isbn,reviewId)
        }
        true
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