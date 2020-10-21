package com.konadev.barbazz.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.konadev.barbazz.R
import com.konadev.barbazz.base.BaseViewHolder
import com.konadev.barbazz.data.model.Drink
import kotlinx.android.synthetic.main.tragos_row.view.*

class MainAdapter(private val context: Context, private val tragosList: List<Drink>,
                    private val itemClickListener: OnTragoClickListener) : RecyclerView.Adapter<BaseViewHolder<*>>(){

    // interfaz para click item
    interface OnTragoClickListener {
        fun onTragoClick(drink:Drink, position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MainViewHolder(LayoutInflater.from(context).inflate(R.layout.tragos_row,parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is MainViewHolder -> holder.bind(tragosList[position],position)
        }
    }

    override fun getItemCount(): Int {
        return tragosList.size
    }

    inner class MainViewHolder(itemView: View) : BaseViewHolder<Drink>(itemView){
        override fun bind(item: Drink, position: Int) {
            // center crop expande la imagen para ocupar el 100% del imageview
            Glide.with(context).load(item.img).centerCrop().into(itemView.img_trago)
            itemView.txt_title.text = item.nombre
            itemView.txt_description.text = item.descripcion
            // Click item
            itemView.setOnClickListener { itemClickListener.onTragoClick(item, position) }
        }
    }
}