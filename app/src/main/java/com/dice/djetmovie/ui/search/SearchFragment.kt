package com.dice.djetmovie.ui.search

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
import com.dice.djetmovie.databinding.FragmentSearchBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.koin.android.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter: FilmAdapter
    private var filmType: String? = null
    private var searchData: SearchData? = null
    private val viewModel: SearchViewModel by viewModel()

    companion object {
        private const val FILM_TYPE = "film_type"

        @JvmStatic
        fun newInstance(filmType: String): Fragment {
            return SearchFragment().apply {
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
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
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
            setData(searchData?.query)
        }
        binding.viewError.root.tag = filmType
    }

    private fun initRv() {
        rvAdapter.addLoadStateListener { loadState ->
            binding.rvFilms.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressLoading.isVisible = loadState.source.refresh is LoadState.Loading
            binding.viewError.root.isVisible = loadState.source.refresh is LoadState.Error
            if (loadState.source.refresh is LoadState.Error) {
                binding.viewError.tvMsgError.text =
                    (loadState.source.refresh as LoadState.Error).error.localizedMessage
            }
        }
        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == rvAdapter.itemCount) {
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
    }

    private fun setData(query: String? = null) {
        viewLifecycleOwner.lifecycleScope.launch {
            when (filmType) {
                Constants.FILM_TYPE_MOVIE -> {
                    viewModel.getMoviesPaging(query).collect { rvAdapter.submitData(it) }
                }

                Constants.FILM_TYPE_TV_SHOW -> {
                    viewModel.getTvShowPaging(query).collect { rvAdapter.submitData(it) }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onSearchData(searchData: SearchData) {
        this.searchData = searchData
        setData(searchData.query)
    }
}