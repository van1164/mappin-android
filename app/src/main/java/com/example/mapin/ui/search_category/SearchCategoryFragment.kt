package com.example.mapin.ui.search_category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    val searchCategoryService by lazy { SearchCategoryService.create() }

    lateinit var token:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchCategoryBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).findViewById<FloatingActionButton>(R.id.fab).visibility =View.INVISIBLE

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

        //임시 DataStore에서 토큰 가져오는 로직
        CoroutineScope(Dispatchers.Main).launch {
            token = DataStoreApplication.getInstance().getDataStore().token.first()
        }

        binding.searchCategoryBtn.setOnClickListener {

            category?.let { it1 ->
                searchCategoryService.search(category = it1, authorization = "Bearer ${token}")
                    .enqueue(object : Callback<SearchCategoryResponse> {
                        //서버 요청 성공
                        override fun onResponse(
                            call: Call<SearchCategoryResponse>,
                            response: Response<SearchCategoryResponse>
                        ) {
                            if(response.body()!=null){
                                Log.d("SearchCategoryService",response.body().toString())
                            }

                        }

                        override fun onFailure(call: Call<SearchCategoryResponse>, t: Throwable) {
                            Log.e("SearchCategoryService", "onFailure: error. cause: ${t.message}")

                        }
                    })
            }
        }

    }

    private fun initializeCategory(){
        binding.selectCategory.visibility = View.INVISIBLE
        binding.searchCategoryBtn.isClickable = false

    }


    override fun onDestroyView() {
        (activity as MainActivity).findViewById<FloatingActionButton>(R.id.fab).visibility =View.VISIBLE
        super.onDestroyView()
        _binding = null
    }


}