package com.example.mylibrary.common

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.airbnb.lottie.LottieAnimationView
import com.example.mylibrary.BaseViewModel
import com.example.mylibrary.data.dto.response.BookInfo
import com.example.mylibrary.databinding.DlgBookDetailBinding
import com.example.mylibrary.databinding.ItemSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailDialog(private val bookInfo: BookInfo, private val viewModel: BaseViewModel): DialogFragment() {

    private var _binding: DlgBookDetailBinding? = null
    private val binding get() = _binding!!

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

        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        binding.textDlgBookDetailLink.setOnClickListener {  }
        binding.lottieDlgBookDetailBookmark.apply {
            setOnClickListener { setBookMark(this,binding) }
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}