package com.dice.djetmovie.ui.favorite

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
import com.dice.djetmovie.data.Constants
import com.dice.djetmovie.databinding.FragmentFavoriteFilmsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFilmsFragment : Fragment() {

    private var _binding: FragmentFavoriteFilmsBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter: FilmAdapter
    private var filmType: String? = null
    private val viewModel: FavoriteViewModel by viewModel()

    companion object {
        private const val FILM_TYPE = "film_type"

        @JvmStatic
        fun newInstance(filmType: String): Fragment {
            return FavoriteFilmsFragment().apply {
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
        _binding = FragmentFavoriteFilmsBinding.inflate(inflater, container, false)
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
        binding.viewError.setOnClickListener {
            setData()
        }
        binding.viewEmpty.tag = filmType
    }

    private fun initRv() {
        binding.rvFilms.apply {
            layoutManager = GridLayoutManager(context, 2)
            tag = filmType
            adapter = rvAdapter
        }
        rvAdapter.addLoadStateListener { loadState ->
            binding.rvFilms.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressLoading.isVisible = loadState.source.refresh is LoadState.Loading
            binding.viewError.isVisible = loadState.source.refresh is LoadState.Error
            if (loadState.source.refresh is LoadState.NotLoading) {
                binding.viewEmpty.isVisible = rvAdapter.itemCount < 1
            }
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