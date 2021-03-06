package com.bangkit2022.boemboe.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2022.boemboe.api.ItemSpices
import com.bangkit2022.boemboe.databinding.ActivityDetailResultBinding
import com.bangkit2022.boemboe.ui.utils.ListTextAdapter
import com.bumptech.glide.Glide

class DetailActivityResult : AppCompatActivity() {

    private lateinit var binding: ActivityDetailResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spice = intent.getParcelableExtra<ItemSpices>(EXTRA_STORY) as ItemSpices
        binding.tvDetailName.text = spice.name
        Glide.with(this@DetailActivityResult)
            .load(spice.photoUrl)
            .into(binding.imgDetailPhoto)
        binding.tvDetailDescription.text = spice.description
        showRecyclerList(spice.cooking, binding.rvListCooking)
        showRecyclerList(spice.benefit, binding.rvListBenefit)
    }

    private fun showRecyclerList(list: ArrayList<String>, recyclerView: RecyclerView) {
        val textAdapter = ListTextAdapter(list)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = textAdapter
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
        const val EXTRA_USERNAME = "extra_username"
    }

}