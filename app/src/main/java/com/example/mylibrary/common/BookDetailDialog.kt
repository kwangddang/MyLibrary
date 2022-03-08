package com.example.mylibrary.common

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.airbnb.lottie.LottieAnimationView
import com.example.mylibrary.DialogViewModel
import com.example.mylibrary.data.dto.BookInfo
import com.example.mylibrary.databinding.DlgBookDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailDialog(private val bookInfo: BookInfo, private val viewModel: DialogViewModel): DialogFragment() {

    private var _binding: DlgBookDetailBinding? = null
    private val binding get() = _binding!!

    private val bookmarkStatusObserver: (Boolean?) -> Unit = { bookmarkStatus ->
        when(bookmarkStatus){
            true -> {
                binding.lottieDlgBookDetailBookmark.progress = 0.5f
                binding.book!!.bookmark = true
            }
            false -> {
                binding.lottieDlgBookDetailBookmark.progress = 0f
                binding.book!!.bookmark = false
            }
        }
    }

    private val ratingAverageObserver: (Float?) -> Unit = { ratingAverage ->
        binding.ratingDlgBookDetail.rating = ratingAverage!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DlgBookDetailBinding.inflate(inflater,container,false)
        binding.book = bookInfo
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        getBookInfo()
        setOnClickListeners()
    }

    private fun observeData(){
        if(KotPrefModel.loginMethod != StringConstant.NO_ACCOUNT) {
            viewModel.bookmarkStatus.observe(viewLifecycleOwner,bookmarkStatusObserver)
            viewModel.ratingAverage.observe(viewLifecycleOwner, ratingAverageObserver)
        }

    }

    private fun getBookInfo(){
        viewModel.getBookmarked(binding.book!!.isbn)
        viewModel.getRatingAverage(binding.book!!.isbn)
    }

    private fun setOnClickListeners(){
        binding.textDlgBookDetailLink.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(binding.book?.link))) }
        binding.lottieDlgBookDetailBookmark.apply { setOnClickListener { setBookMark(this,binding) } }
        binding.viewDlgBookRating.setOnClickListener { BookRatingDialog(viewModel, binding.book!!).show(childFragmentManager,TagConstant.BOOK_RATING_DIALOG) }
        binding.textDlgBookDetailReview.setOnClickListener { ReviewBottomSheetDialog(bookInfo,viewModel).show(childFragmentManager,TagConstant.REVIEW_BOTTOM_SHEET_DIALOG) }
    }

    override fun onResume() {
        super.onResume()
        setDialogSize()
    }

    private fun setDialogSize() {
        val windowManager =
            requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    private fun setBookMark(view: LottieAnimationView, item: DlgBookDetailBinding) {
        if (!(item.book?.bookmark)!!) {
            val animator = getValueAnimator(0f,0.5f, view)
            animator.start()
            item.book?.bookmark = true
            if(KotPrefModel.loginMethod == StringConstant.NO_ACCOUNT)
                viewModel.setMyBook(bookInfoToBook(item.book!!))
            else
                viewModel.setUserBook(item.book!!)
        } else {
            val animator = getValueAnimator(0.5f,0.0f, view)
            animator.start()
            item.book?.bookmark = false

            if(KotPrefModel.loginMethod == StringConstant.NO_ACCOUNT)
                viewModel.deleteMyBook(item.book!!.isbn)
            else
                viewModel.deleteUserBook(item.book!!.isbn)
        }
    }

    private fun getValueAnimator(start: Float, end: Float, view: LottieAnimationView): ValueAnimator {
        return ValueAnimator.ofFloat(start, end).setDuration(500).apply {
            addUpdateListener {
                view.progress = it.animatedValue as Float
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}