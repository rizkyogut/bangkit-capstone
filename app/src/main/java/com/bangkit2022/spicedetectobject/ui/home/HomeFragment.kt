package com.bangkit2022.spicedetectobject.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2022.spicedetectobject.SpicesAdapter
import com.bangkit2022.spicedetectobject.api.ItemSpices
import com.bangkit2022.spicedetectobject.databinding.FragmentHomeBinding
import com.bangkit2022.spicedetectobject.ui.camera.DetailActivityResult
import com.bangkit2022.spicedetectobject.ui.camera.MainCameraActivity

class HomeFragment : Fragment() {

    private lateinit var bindingHome: FragmentHomeBinding
    private val viewModel: HomeFragmentViewModel by activityViewModels()
    private lateinit var usernameFollowers: String
    private var listSpices = ArrayList<ItemSpices>()
    private lateinit var spicesAdapter: SpicesAdapter
//    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
//    private val binding get() = _binding!!

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingHome = FragmentHomeBinding.inflate(inflater, container, false)
        return bindingHome.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val argument = arguments
        usernameFollowers = argument?.getString(DetailActivityResult.EXTRA_USERNAME).toString()
        showRecyclerList()

        viewModel.listSpice.observe(viewLifecycleOwner) { listSpices ->
            setListSpices(listSpices)
//            bindingHome.pbSpices.visibility = View.GONE
        }

        viewModel.isShowLoading.observe(viewLifecycleOwner) {
            showLoading(it)


        }
        viewModel.setListSpice()


        bindingHome.fabAddStory.setOnClickListener() {
            val intent = Intent(requireActivity(), MainCameraActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setListSpices(list: ArrayList<ItemSpices>) {
        bindingHome.rvListSpice.adapter = spicesAdapter
        spicesAdapter.setList(list)
    }

    private fun showRecyclerList() {
        spicesAdapter = SpicesAdapter(listSpices)
        bindingHome.rvListSpice.setHasFixedSize(true)
        bindingHome.rvListSpice.layoutManager = LinearLayoutManager(activity)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            bindingHome.pbSpices.visibility = View.VISIBLE
        } else {
            bindingHome.pbSpices.visibility = View.GONE
        }
    }

}



