package com.bangkit2022.boemboe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2022.boemboe.databinding.ItemListBinding


class ListTextAdapter(private val listText: ArrayList<String>) : RecyclerView.Adapter<ListTextAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListTextAdapter.ViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListTextAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListTextAdapter.ViewHolder, position: Int) {
        val text = listText[position]

        holder.binding.tvTextItem.text = "- %s".format(text)
    }

    override fun getItemCount(): Int = listText.size




}