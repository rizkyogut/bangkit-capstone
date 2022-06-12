package com.bangkit2022.boemboe.ui.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2022.boemboe.databinding.ItemListBinding


class ListTextAdapter(private val listText: ArrayList<String>) : RecyclerView.Adapter<ListTextAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text = listText[position]

        holder.binding.tvTextItem.text = "- %s".format(text)
    }

    override fun getItemCount(): Int = listText.size




}