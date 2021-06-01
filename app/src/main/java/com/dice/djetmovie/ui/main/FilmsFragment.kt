package com.dice.djetmovie.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.dice.djetmovie.adapter.FilmAdapter
import com.dice.djetmovie.adapter.FilmLoadStateAdapter
import com.dice.djetmovie.data.Constants
import com.dice.djetmovie.databinding.FragmentFilmsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class FilmsFragment : Fragment() {

    private var _binding: FragmentFilmsBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter: FilmAdapter
    private var filmType: String? = null
    private val viewModel: MainViewModel by viewModel()

    companion object {
        private const val FILM_TYPE = "film_type"

        @JvmStatic
        fun newInstance(filmType: String): Fragment {
            return FilmsFragment().apply {
                arguments = Bundle().apply {
                    putString(FILM_TYPE, filmType)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filmType = it.getString(FILM_TYPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvAdapter = FilmAdapter()

        initRv()
        initView()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initView() {
        binding.viewError.root.setOnClickListener {
            setData()
        }
        binding.viewError.root.tag = filmType
    }

    private fun initRv() {
        rvAdapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.rvFilms.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.progressLoading.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.viewError.root.isVisible = loadState.source.refresh is LoadState.Error
            if (loadState.source.refresh is LoadState.Error) {
                binding.viewError.tvMsgError.text =
                    (loadState.source.refresh as LoadState.Error).error.localizedMessage
            }
        }
        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == rvAdapter.itemCount && rvAdapter.itemCount > 0) {
                    2
                } else {
                    1
                }
            }
        }
        binding.rvFilms.apply {
            layoutManager = gridLayoutManager
            tag = filmType
            adapter = rvAdapter.withLoadStateHeaderAndFooter(
                header = FilmLoadStateAdapter { rvAdapter.retry() },
                footer = FilmLoadStateAdapter { rvAdapter.retry() }
            )
        }
        setData()
    }

    private fun setData() {
        viewLifecycleOwner.lifecycleScope.launch {
            when (filmType) {
                Constants.FILM_TYPE_MOVIE -> {
                    viewModel.getMoviesPaging().collect {
                        rvAdapter.submitData(it)
                    }
                }

                Constants.FILM_TYPE_TV_SHOW -> {
                    viewModel.getTvShowPaging().collect {
                        rvAdapter.submitData(it)
                    }
                }
            }
        }
    }
}