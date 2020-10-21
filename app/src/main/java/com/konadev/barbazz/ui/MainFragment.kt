package com.konadev.barbazz.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.konadev.barbazz.AppDatabase
import com.konadev.barbazz.R
import com.konadev.barbazz.data.DataSource
import com.konadev.barbazz.data.model.Drink
import com.konadev.barbazz.domain.RepoImpl
import com.konadev.barbazz.ui.viewmodel.MainViewModel
import com.konadev.barbazz.ui.viewmodel.VMFactory
import com.konadev.barbazz.vo.Resource
import kotlinx.android.synthetic.main.fragment_main.*
import java.text.FieldPosition

class MainFragment : Fragment() , MainAdapter.OnTragoClickListener {

    // viewModel
    private val viewModel by viewModels<MainViewModel> { VMFactory(RepoImpl(DataSource(AppDatabase.getDataBase(requireActivity().applicationContext)))) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchView()
        setupObservers()

        btn_favoritos.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_favoritosFragment)
        }

    }

    private fun setupObservers() {
        viewModel.fetchTragosList.observe(viewLifecycleOwner, Observer { result ->
            when(result) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    rv_tragos.adapter = MainAdapter(requireContext(),result.data,this)
                }
                is Resource.Failure -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),"Ocurrío un problema ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setupSearchView() {
        // Setup Search View
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setTrago(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun setupRecyclerView() {
        // Setup RecyclerView
        rv_tragos.layoutManager = LinearLayoutManager(requireContext())
        // lineas verticales del recycler
        rv_tragos.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))

    }

    override fun onTragoClick(drink: Drink, position: Int) {
        // Pasamos el objeto drink
        val bundle = Bundle()
        bundle.putParcelable("drink", drink)
        // navegamos hacia el fragment detalle y enviamos el objeto drink
        findNavController().navigate(R.id.action_mainFragment_to_tragosDetallesFragment,bundle)
    }

}