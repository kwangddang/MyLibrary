package com.example.mylibrary.view.root.user

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.example.mylibrary.R
import com.example.mylibrary.data.room.entity.Book
import com.example.mylibrary.databinding.FragmentUserBinding
import com.example.mylibrary.databinding.ItemUserBinding
import com.example.mylibrary.view.root.home.dto.ItemClickArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()

    private val adapter: UserAdapter by lazy {
        UserAdapter(itemOnClickListener)
    }

    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        when (args?.view?.id) {
            R.id.lottie_ihome_bookmark -> setBookMark(args.view as LottieAnimationView, args.item as ItemUserBinding, args.position)
        }
    }

    private val swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        refresh()
        binding.swipeUserContainer.isRefreshing = false
    }

    private val bookObserver: (List<Book>) -> Unit = { book ->
        adapter.apply {
            content = book as MutableList<Book>
            notifyDataSetChanged()
        }
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
        setOnRefreshListener()
    }

    private fun setOnRefreshListener() {
        binding.swipeUserContainer.setOnRefreshListener(swipeRefreshListener)
    }

    private fun refresh() {
        viewModel.getMyBook()
    }

    private fun observeData() {
        viewModel.book.observe(viewLifecycleOwner, bookObserver)
    }

    private fun initAdapter() {
        binding.recyclerUser.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setBookMark(view: LottieAnimationView, item: ItemUserBinding, position: Int) {
        val animator = getValueAnimator(0.5f, 0.0f, view)
        animator.start()
        viewModel.delete(item.book!!.isbn)
        adapter.apply {
            content.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,content.size)
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

