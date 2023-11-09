package com.example.mapin.ui.search_shop

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mapin.DataStoreApplication
import com.example.mapin.MainActivity
import com.example.mapin.R
import com.example.mapin.databinding.FragmentSearchShopBinding
import com.example.mapin.network.model.SearchShopResponse
import com.example.mapin.network.service.SearchShopService
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

        //임시 DataStore에서 토큰 가져오는 로직
        CoroutineScope(Dispatchers.Main).launch {
            token = DataStoreApplication.getInstance().getDataStore().token.first()
        }

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
            nameShop?.let { it ->
                searchShopService.search(shop = it, authorization = "Bearer ${token}")
                    .enqueue(object : Callback<SearchShopResponse> {
                        //서버 요청 성공
                        override fun onResponse(
                            call: Call<SearchShopResponse>,
                            response: Response<SearchShopResponse>
                        ) {
                            if(response.body()!=null){
                                Log.d("SearchShopService",response.body().toString())
                            }

                        }

                        override fun onFailure(call: Call<SearchShopResponse>, t: Throwable) {
                            Log.e("SearchShopService", "onFailure: error. cause: ${t.message}")

                        }
                    })
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}