package com.example.mapin.ui.search_shop

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mapin.DataStoreApplication
import com.example.mapin.MainActivity
import com.example.mapin.R
import com.example.mapin.databinding.FragmentSearchShopBinding
import com.example.mapin.network.model.SearchShopResponse
import com.example.mapin.network.service.SearchShopService
import com.example.mapin.ui.main_content.ContentData
import com.example.mapin.ui.main_content.MainContentAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchShopFragment : Fragment() {

    private var _binding: FragmentSearchShopBinding? = null


    private val binding get() = _binding!!
    private val viewModel: SearchShopViewModel by viewModels()
    val searchShopService by lazy { SearchShopService.create() }

    lateinit var token:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchShopBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var nameShop:String? = null
        var item:ArrayList<ContentData> = ArrayList()

        val resultRecyclerView = binding.resultShopRecyclerView
        val resultRecyclerAdapter = MainContentAdapter()
        resultRecyclerView.adapter = resultRecyclerAdapter
        resultRecyclerView.layoutManager = LinearLayoutManager(context)


        binding.searchShop.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                nameShop = newText

                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }
        })

        binding.searchShopBtn.setOnClickListener {

            //임시 DataStore에서 토큰 가져오는 로직
            CoroutineScope(Dispatchers.Default).launch {
                launch {
                    token = DataStoreApplication.getInstance().getDataStore().token.first()
                }.join()

                launch {
                    nameShop?.let { it1 ->
                        searchShopService.search(shop = it1, authorization = "Bearer ${token}")
                            .enqueue(object : Callback<SearchShopResponse> {
                                //서버 요청 성공
                                override fun onResponse(
                                    call: Call<SearchShopResponse>,
                                    response: Response<SearchShopResponse>
                                ) {
                                    onResult()
                                    if(response.body()!=null){
                                        Log.d("SearchShopService",response.body().toString())
                                        if(response.body()!!.losts.isNotEmpty()){
                                            binding.resultTv.text = "${nameShop} 에 대해,\n${response.body()!!.losts.size}개의 결과를 찾았습니다."
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
                                        else binding.resultTv.text = "${nameShop} 에 대한 결과가 존재하지 않습니다."

                                    }

                                }

                                override fun onFailure(call: Call<SearchShopResponse>, t: Throwable) {
                                    Log.e("SearchShopService", "onFailure: error. cause: ${t.message}")

                                }
                            })
                    }
                }
            }

        }

    }

    private fun onResult(){
        binding.resultTv.visibility = View.VISIBLE
        binding.resultView.visibility = View.VISIBLE
        binding.searchShopBtn.text = "다시 검색"
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}