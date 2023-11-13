package com.delivery.quickie.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.delivery.quickie.R
import com.delivery.quickie.databinding.FragmentContentBinding
import com.delivery.quickie.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContentFragment : Fragment() {

    lateinit var binding: FragmentContentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.feedRecyclerview.layoutManager = LinearLayoutManager(binding.root.context)

//        lifecycleScope.launch(Dispatchers.IO) {
//            val response = RetrofitClient.api.getPosts()
//            if(response.isSuccessful){
//                withContext(Dispatchers.Main){
//                    response.body()?.let {
//                    }
//                }
//            }
//        }

    }
}