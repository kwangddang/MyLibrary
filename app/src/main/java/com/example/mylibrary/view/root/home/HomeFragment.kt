package com.example.mylibrary.view.root.home

import android.animation.ValueAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.airbnb.lottie.LottieAnimationView
import com.example.mylibrary.R
import com.example.mylibrary.data.database.BookDatabase
import com.example.mylibrary.data.dto.request.BookRequest
import com.example.mylibrary.data.dto.response.BookResponse
import com.example.mylibrary.data.room.dao.BookDao
import com.example.mylibrary.data.room.entity.Book
import com.example.mylibrary.databinding.FragmentHomeBinding
import com.example.mylibrary.databinding.ItemHomeBinding
import com.example.mylibrary.view.root.home.dto.ItemClickArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private val adapter: HomeAdapter by lazy {
        HomeAdapter(itemOnClickListener)
    }

    private val btnOnClickListener: (View) -> Unit = {
        viewModel.getBook(BookRequest(query = binding.editHomeSearch.text.toString()))
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

        Log.d("Test",args?.view.toString())
        when(args?.view?.id){
            R.id.text_ihome_link -> startActivity(Intent(Intent.ACTION_VIEW,Uri.parse((args?.item as ItemHomeBinding).book?.link)))

            R.id.lottie_ihome_bookmark -> setBookMark(args.view as LottieAnimationView, args.item as ItemHomeBinding)
        }

//        val appLinkAction = requireActivity().intent.action
//        val appLinkData: Uri? = requireActivity().intent.data
//        Log.d("Test",link!!)
//
//        if(Intent.ACTION_VIEW == appLinkAction){
//            appLinkData?.lastPathSegment?.also {
//                Log.d("Test",link!!)
//                Log.d("Test",it)
//            }
//        }

//        val book = (args?.item as ItemHomeBinding).book
//        Log.d("Test", "?")
//        CoroutineScope(Dispatchers.IO).launch {
//            bookDao.insert(
//                Book(
//                    author = book?.author,
//                    description = book?.description,
//                    discount = book?.discount,
//                    image = book?.image,
//                    isbn = book?.isbn,
//                    link = book?.link,
//                    price = book?.price,
//                    pubdate = book?.pubdate,
//                    publisher = book?.publisher,
//                    title = book?.title
//                )
//            )
//        }
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
        setOnClickListener()
    }

    private fun observeData() {
        viewModel.book.observe(viewLifecycleOwner, bookObserver)
    }

    private fun setOnClickListener() {
        binding.btnHomeSearch.setOnClickListener(btnOnClickListener)
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
            //viewModel.likeAlbum(item.album!!.albumId)
        } else {
            val animator = getValueAnimator(0.5f,0.0f, view)
            animator.start()
            item.book?.isBookMark = false
            //viewModel.unlikeAlbum(item.album!!.albumId)
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