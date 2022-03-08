package com.example.mylibrary.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mylibrary.DialogViewModel
import com.example.mylibrary.data.dto.BookInfo
import com.example.mylibrary.databinding.DlgBookRatingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookRatingDialog(private val viewModel: DialogViewModel, private val book: BookInfo): DialogFragment() {

    private var _binding: DlgBookRatingBinding? = null
    private val binding get() = _binding!!

    private val btnConfirmOnClickListener: (View) -> Unit = {
        viewModel.setBookRating(binding.ratingDlgBookRating.rating, book)
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DlgBookRatingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        binding.textDlgRatingCancel.setOnClickListener { dismiss() }
        binding.textDlgRatingConfirm.setOnClickListener(btnConfirmOnClickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}