package com.example.mapin.ui.search_category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.mapin.DataStoreApplication
import com.example.mapin.MainActivity
import com.example.mapin.R
import com.example.mapin.databinding.FragmentSearchCategoryBinding
import com.example.mapin.network.model.SearchCategoryResponse
import com.example.mapin.network.service.SearchCategoryService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchCategoryFragment : Fragment() {

    private var _binding: FragmentSearchCategoryBinding? = null


    private val binding get() = _binding!!
    private val viewModel: SearchCategoryViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchCategoryBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var category:String? = null
        binding.searchCategoryBtn.isClickable = false

        binding.categoryClothes.setOnClickListener {
            binding.selectCategory.visibility = View.VISIBLE
            binding.searchCategoryBtn.isClickable = true
            category = "의류"
            binding.selectTv.text = category
        }
        binding.categoryJewelry.setOnClickListener {
            binding.selectCategory.visibility = View.VISIBLE
            binding.searchCategoryBtn.isClickable = true
            category = "귀금속"
            binding.selectTv.text = category
        }
        binding.categoryElec.setOnClickListener {
            binding.selectCategory.visibility = View.VISIBLE
            binding.searchCategoryBtn.isClickable = true
            category = "전자기기"
            binding.selectTv.text = category
        }
        binding.categoryOffice.setOnClickListener {
            binding.selectCategory.visibility = View.VISIBLE
            binding.searchCategoryBtn.isClickable = true
            category = "사무용품"
            binding.selectTv.text = category
        }
        binding.categoryBook.setOnClickListener {
            binding.selectCategory.visibility = View.VISIBLE
            binding.searchCategoryBtn.isClickable = true
            category = "도서용품"
            binding.selectTv.text = category
        }
        binding.categoryEtc.setOnClickListener {
            binding.selectCategory.visibility = View.VISIBLE
            binding.searchCategoryBtn.isClickable = true
            category = "기타"
            binding.selectTv.text = category
        }

        binding.clearBtn.setOnClickListener {

            initializeCategory()
        }


        binding.searchCategoryBtn.setOnClickListener {
            val bundle = bundleOf("category" to category)
            it.findNavController().navigate(R.id.action_searchFragment_to_resultCategory, bundle)
        }

    }

    private fun initializeCategory(){
        binding.selectCategory.visibility = View.INVISIBLE
        binding.searchCategoryBtn.isClickable = false

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}