package com.dice.djetmovie.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.dice.djetmovie.adapter.FilmAdapter
import com.dice.djetmovie.databinding.FragmentFilmsBinding
import com.dice.djetmovie.repository.model.Film
import com.dice.djetmovie.ui.detail.DetailFilmActivity
import com.dice.djetmovie.viewmodel.DataViewModel
import org.jetbrains.anko.support.v4.startActivity

private const val FILM_TYPE = "film_type"

class FilmsFragment : Fragment(), FilmAdapter.ClickListener {

    private lateinit var binding: FragmentFilmsBinding
    private lateinit var rvAdapter: FilmAdapter
    private var filmType: String? = null
    private val dataViewModel: DataViewModel by viewModels()

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param filmType Parameter 1.
         * @return A new instance of fragment BlankFragment.
         */
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
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvAdapter = FilmAdapter()

        setList()
        observe()
    }

    private fun observe() {
        dataViewModel.listFilms.observe(viewLifecycleOwner, Observer { list ->
            if (list.isNotEmpty()) {
                rvAdapter.setData(list)
            }
        })
    }

    private fun setList() {
        rvAdapter.setOnItemClickListener(this)
        binding.rvFilms.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = rvAdapter
            tag = filmType
        }

        dataViewModel.getFilms(filmType)
    }

    override fun onItemClick(data: Film) {
        startActivity<DetailFilmActivity>(DetailFilmActivity.EXTRAS_FILM to data)
    }
}