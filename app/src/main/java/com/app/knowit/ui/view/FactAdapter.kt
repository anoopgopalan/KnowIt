package com.app.knowit.ui.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.app.knowit.data.Info

/**
 * Adapter which loads the [Info] to display the contents
 */

class FactAdapter : RecyclerView.Adapter<FactAdapter.FactItemViewHolder>() {

    var adapterItem: List<Info> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(contrainer: ViewGroup, viewType: Int): FactItemViewHolder {
        return FactItemViewHolder(FactItemView(contrainer.context))
    }

    override fun getItemCount(): Int {
        return adapterItem.size
    }

    override fun onBindViewHolder(holder: FactItemViewHolder, position: Int) {
        (holder.view as FactItemView).bind(adapterItem[position])
    }

    data class FactItemViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}