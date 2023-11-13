package com.example.mapin.ui.search_location

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
import com.example.mapin.R
import com.example.mapin.databinding.FragmentResultLocationBinding
import com.example.mapin.network.model.SearchCategoryResponse
import com.example.mapin.network.model.SearchLocationResponse
import com.example.mapin.network.service.SearchCategoryService
import com.example.mapin.network.service.SearchLocationService
import com.example.mapin.ui.main_content.ContentData
import com.example.mapin.ui.main_content.MainContentAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultLocationFragment : Fragment() {

    private var _binding: FragmentResultLocationBinding? = null


    private val binding get() = _binding!!
    private val viewModel: SearchLocationViewModel by viewModels()
    val searchLocationService by lazy { SearchLocationService.create() }

    lateinit var token:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultLocationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val resultRecyclerView = binding.resultLocationRecyclerView
        val resultRecyclerAdapter = MainContentAdapter()
        resultRecyclerView.adapter = resultRecyclerAdapter
        resultRecyclerView.layoutManager = LinearLayoutManager(context)

        val dong = requireArguments().getString("dong")

        var item:ArrayList<ContentData> = ArrayList()


        //임시 DataStore에서 토큰 가져오는 로직
        CoroutineScope(Dispatchers.Default).launch {
            launch {
                token = DataStoreApplication.getInstance().getDataStore().token.first()
            }.join()

            launch {
                dong?.let { it ->
                    searchLocationService.search(dong_name = it, authorization = "Bearer ${token}")
                        .enqueue(object : Callback<SearchLocationResponse> {
                            //서버 요청 성공
                            override fun onResponse(
                                call: Call<SearchLocationResponse>,
                                response: Response<SearchLocationResponse>
                            ) {
                                if(response.body()!=null){
                                    Log.d("ResultLocationService",response.body().toString())
                                    binding.resultTv.text="${dong} 에 대해,\n${response.body()!!.losts.size}개의 결과를 찾았습니다."
                                    for(i:Int in 0 until response.body()!!.losts.size){
                                        item.add(ContentData(
                                            id = response.body()!!.losts[i].id,
                                            imageUrl = response.body()!!.losts[i].imageUrl,
                                            title = response.body()!!.losts[i].title,
                                            time = "time?",
                                            location = "locatoin?"))
                                        resultRecyclerAdapter.submitList(item)
                                    }
                                }

                            }

                            override fun onFailure(call: Call<SearchLocationResponse>, t: Throwable) {
                                Log.e("ResultLocationService", "onFailure: error. cause: ${t.message}")

                            }
                        })
                }
            }

        }

        binding.searchCategoryBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_resultFragment_to_searchLocation)
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}