package com.konadev.barbazz.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.konadev.barbazz.AppDatabase
import com.konadev.barbazz.R
import com.konadev.barbazz.data.DataSource
import com.konadev.barbazz.data.model.Drink
import com.konadev.barbazz.data.model.DrinkEntity
import com.konadev.barbazz.domain.RepoImpl
import com.konadev.barbazz.ui.viewmodel.MainViewModel
import com.konadev.barbazz.ui.viewmodel.VMFactory
import com.konadev.barbazz.vo.Resource
import kotlinx.android.synthetic.main.fragment_favoritos.*

class FavoritosFragment : Fragment() , MainAdapter.OnTragoClickListener {

    private val viewModel by activityViewModels<MainViewModel> {VMFactory(RepoImpl(DataSource(
        AppDatabase.getDataBase(requireActivity().applicationContext))))}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getTragosFavoritos().observe(viewLifecycleOwner, Observer { result ->
            when(result) {
                is Resource.Loading -> { }

                is Resource.Success -> {
                    // mapeamos el objeto DrinkEntity a Drink para pasarlo al MainAdapter
                    val listaFavoritos: List<Drink> = result.data.map {
                        Drink(it.tragoId,it.img,it.nombre,it.descripcion,it.hasAlcohol)
                    }
                    rv_fragment_favoritos.adapter = MainAdapter(requireContext(),listaFavoritos,this)
                    Log.d("LISTA_FAVORITOS", "${result.data}")
                }

                is Resource.Failure -> {}
            }
        })
    }

    private fun setupRecyclerView() {
        rv_fragment_favoritos.layoutManager = LinearLayoutManager(requireContext())
        rv_fragment_favoritos.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
    }

    override fun onTragoClick(drink: Drink, position: Int) {
        val trago: DrinkEntity = DrinkEntity(drink.tragoId, drink.img, drink.nombre, drink.descripcion, drink.hasAlcohol)
        viewModel.deleteTrago(trago)
        // Actualizar recyclerView
        rv_fragment_favoritos.adapter?.notifyItemRemoved(position)
        rv_fragment_favoritos.adapter?.notifyItemRangeRemoved(position,rv_fragment_favoritos.adapter?.itemCount!!)
    }
}