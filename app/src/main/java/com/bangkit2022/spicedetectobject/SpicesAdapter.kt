package com.bangkit2022.spicedetectobject

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2022.spicedetectobject.api.ItemSpices
import com.bangkit2022.spicedetectobject.databinding.ItemSpiceBinding
import com.bangkit2022.spicedetectobject.ui.camera.DetailActivityResult
import com.bumptech.glide.Glide

class SpicesAdapter(private val listSpices: ArrayList<ItemSpices>) : RecyclerView.Adapter<SpicesAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemSpiceBinding) : RecyclerView.ViewHolder(binding.root)

    fun setList(users: ArrayList<ItemSpices>) {
        listSpices.clear()
        listSpices.addAll(users)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSpiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val spice = listSpices[position]

        viewHolder.binding.tvSpices.text = spice.name
//        viewHolder.binding.tvItemDescription.text = spice.description


        Glide.with(viewHolder.itemView.context)
            .load(spice.photoUrl)
            .into(viewHolder.binding.ivPhoto)

        viewHolder.itemView.setOnClickListener {
            val moveWithObjectIntent =
                Intent(viewHolder.itemView.context, DetailActivityResult::class.java)

            moveWithObjectIntent.putExtra(DetailActivityResult.EXTRA_STORY, spice)
            viewHolder.itemView.context.startActivity(moveWithObjectIntent)
        }
    }

    override fun getItemCount() = listSpices.size

}
