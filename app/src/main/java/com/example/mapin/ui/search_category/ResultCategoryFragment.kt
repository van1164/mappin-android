package com.example.mapin.ui.search_category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mapin.DataStoreApplication
import com.example.mapin.MainActivity
import com.example.mapin.R
import com.example.mapin.databinding.FragmentResultCategoryBinding
import com.example.mapin.databinding.FragmentSearchCategoryBinding
import com.example.mapin.network.model.SearchCategoryResponse
import com.example.mapin.network.service.SearchCategoryService
import com.example.mapin.ui.main_content.ContentData
import com.example.mapin.ui.main_content.MainContentAdapter
import com.example.mapin.util.DateUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultCategoryFragment : Fragment() {

    private var _binding: FragmentResultCategoryBinding? = null


    private val binding get() = _binding!!
    private val viewModel: SearchCategoryViewModel by viewModels()
    val searchCategoryService by lazy { SearchCategoryService.create() }

    lateinit var token:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultCategoryBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val resultRecyclerView = binding.resultCategoryRecyclerView
        val resultRecyclerAdapter = MainContentAdapter()
        resultRecyclerView.adapter = resultRecyclerAdapter
        resultRecyclerView.layoutManager = LinearLayoutManager(context)

        val category = requireArguments().getString("category")
        var item:ArrayList<ContentData> = ArrayList()

        //임시 DataStore에서 토큰 가져오는 로직
        CoroutineScope(Dispatchers.Default).launch {
            launch {
                token = DataStoreApplication.getInstance().getDataStore().token.first()
            }.join()

            launch {
                category?.let { it ->
                    searchCategoryService.search(category = it, authorization = "Bearer ${token}")
                        .enqueue(object : Callback<SearchCategoryResponse> {
                            //서버 요청 성공
                            override fun onResponse(
                                call: Call<SearchCategoryResponse>,
                                response: Response<SearchCategoryResponse>
                            ) {
                                if(response.body()!=null){
                                    Log.d("ResultCategoryService",response.body().toString())
                                    binding.resultTv.text="${category} 카테고리에 대해,\n${response.body()!!.losts.size}개의 결과를 찾았습니다."
                                    for(i:Int in 0 until response.body()!!.losts.size){
                                        val formattedDateTime = DateUtils.formatDateTime(response.body()!!.losts[i].createdAt)
                                        item.add(ContentData(
                                            id = response.body()!!.losts[i].id,
                                            imageUrl = response.body()!!.losts[i].imageUrl,
                                            title = response.body()!!.losts[i].title,
                                            time = formattedDateTime,
                                            location = response.body()!!.losts[i].dong))
                                        resultRecyclerAdapter.submitList(item)
                                    }
                                }

                            }

                            override fun onFailure(call: Call<SearchCategoryResponse>, t: Throwable) {

                                Log.e("ResultCategoryService", "onFailure: error. cause: ${t.message}")

                            }
                        })
                }
            }

        }



        binding.searchCategoryBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_resultFragment_to_searchCategory)
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}