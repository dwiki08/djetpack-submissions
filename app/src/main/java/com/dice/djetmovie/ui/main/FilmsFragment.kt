package com.dice.djetmovie.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dice.djetmovie.adapter.FilmAdapter
import com.dice.djetmovie.data.Constants
import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.data.remote.utils.Resource
import com.dice.djetmovie.databinding.FragmentFilmsBinding
import com.dice.djetmovie.ui.detail.DetailFilmActivity
import com.dice.djetmovie.viewmodel.DataViewModel
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.koin.android.viewmodel.ext.android.viewModel

private const val FILM_TYPE = "film_type"

class FilmsFragment : Fragment(), FilmAdapter.ClickListener {

    private var _binding: FragmentFilmsBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter: FilmAdapter
    private var filmType: String? = null
    private val dataViewModel: DataViewModel by viewModel()

    companion object {
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
        observe()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun observe() {
        if (filmType == Constants.FILM_TYPE_MOVIE) {
            dataViewModel.listMovie
        } else {
            dataViewModel.listTvShow
        }.observe(viewLifecycleOwner, {
            val listFilm = it.data
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    setRv(listFilm)
                }
                Resource.Status.ERROR -> {
                    it.message?.getContentIfNotHandled()?.let { msg -> toast(msg) }
                    setRv(listFilm)

                    binding.tvMsgError.isVisible = listFilm.isNullOrEmpty()
                    binding.animError.isVisible = listFilm.isNullOrEmpty()
                    binding.rvFilms.isVisible = !listFilm.isNullOrEmpty()
                }
            }
        })

        dataViewModel.isLoading.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { loading ->
                binding.progressLoading.isVisible = loading
                binding.rvFilms.isVisible = !loading
            }
        })
    }

    private fun initRv() {
        rvAdapter.setOnItemClickListener(this)
        binding.rvFilms.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = rvAdapter
            tag = filmType
        }

        when (filmType) {
            Constants.FILM_TYPE_MOVIE -> {
                dataViewModel.getMovies()
            }
            Constants.FILM_TYPE_TV_SHOW -> {
                dataViewModel.getTvShows()
            }
        }
    }

    private fun setRv(listFilm: List<Film>?) {
        listFilm?.let { it ->
            rvAdapter.setData(it)
        }
    }

    override fun onItemClick(data: Film) {
        startActivity<DetailFilmActivity>(DetailFilmActivity.EXTRAS_FILM to data)
    }
}