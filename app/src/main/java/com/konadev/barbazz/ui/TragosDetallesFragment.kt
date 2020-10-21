package com.konadev.barbazz.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.konadev.barbazz.AppDatabase
import com.konadev.barbazz.R
import com.konadev.barbazz.data.DataSource
import com.konadev.barbazz.data.model.Drink
import com.konadev.barbazz.data.model.DrinkEntity
import com.konadev.barbazz.domain.RepoImpl
import com.konadev.barbazz.ui.viewmodel.MainViewModel
import com.konadev.barbazz.ui.viewmodel.VMFactory
import kotlinx.android.synthetic.main.fragment_tragos_detalles.*

class TragosDetallesFragment : Fragment() {

    private val viewModel by activityViewModels<MainViewModel> { VMFactory(RepoImpl(DataSource(
        AppDatabase.getDataBase(requireActivity().applicationContext)))) }

    private lateinit var drink: Drink

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // recibimos el objeto drink desde el main fragment
        requireArguments().let {
            drink = it.getParcelable("drink")!!
            Log.d("Detalle_frag", "$drink")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tragos_detalles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireContext()).load(drink.img).centerCrop().into(img_desc)
        trago_tittle.text = drink.nombre
        trago_hasAlcohol.text = drink.hasAlcohol
        trago_desc.text = drink.descripcion

        btn_add_favorito.setOnClickListener {
            viewModel.guardarTrago(DrinkEntity(drink.tragoId, drink.img, drink.nombre, drink.descripcion, drink.hasAlcohol))
            Toast.makeText(requireContext(), "Se guardó trago en favoritos", Toast.LENGTH_SHORT).show()
        }
    }
}