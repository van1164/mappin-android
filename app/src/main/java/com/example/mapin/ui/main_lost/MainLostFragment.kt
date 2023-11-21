package com.example.mapin.ui.main_lost

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mapin.DataStoreApplication
import com.example.mapin.MainActivity
import com.example.mapin.R
import com.example.mapin.databinding.FragmentFirstBinding
import com.example.mapin.databinding.FragmentMainLostBinding
import com.example.mapin.network.model.LoginResponse
import com.example.mapin.network.model.MainListResponse
import com.example.mapin.network.service.LoginRequest
import com.example.mapin.network.service.LoginService
import com.example.mapin.network.service.SearchMainListInterface
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

class MainLostFragment : Fragment() {

    private var _binding: FragmentMainLostBinding? = null


    private val binding get() = _binding!!
    private val viewModel: MainLostViewModel by viewModels()
    private lateinit var token : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainLostBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val mainRecyclerView = binding.mainContentRecyclerView
        //val mainRecyclerView2 = binding.mainContentRecyclerView2
        token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2Rmc2FmIiwicm9sZSI6IkFETUlOIiwiZXhwIjoxNzAwNjQ3Nzk4fQ.eNsDDuwUT5M-vKnmD417nAfoDC--jlBRevDjipd7_D8ISrBW05xwAK0Qu1xciSvHrfIDe7dus5xWKjiEIGoX4A"

        val item = listOf<ContentData>()
        val mainRecyclerAdapter = MainContentAdapter()
        mainRecyclerView.adapter = mainRecyclerAdapter

        //mainRecyclerView2.adapter = mainRecyclerAdapter
        mainRecyclerView.layoutManager = LinearLayoutManager(context)
        //mainRecyclerView2.layoutManager = GridLayoutManager(requireActivity(),5, GridLayoutManager.HORIZONTAL, false)
        SearchMainListInterface.create()
            .searchList("Bearer ${token}","127.047377408384","37.517331925853")
            .enqueue(object : Callback<MainListResponse> {
                override fun onResponse(
                    call: Call<MainListResponse>,
                    response: Response<MainListResponse>
                ) {
                    Log.d("SearchMainListInterface", response.body().toString())
                    val itemList = convertMainListToContentData(response.body())
                    mainRecyclerAdapter.submitList(itemList)

                }
                override fun onFailure(call: Call<MainListResponse>, t: Throwable) {
                    Log.d("XXXXXXXXXXXXXXXXXXXXXXX", "response.body().toString()")
                }
            })



    }
    private fun convertMainListToContentData(mainListResponse: MainListResponse?): List<ContentData> {
        return mainListResponse?.losts?.map{ mainListItem->
            ContentData(
                imageUrl = mainListItem.imageUrl,
                title = mainListItem.title,
                time = mainListItem.createdAt,
                id = mainListItem.id,
                location = "null"
                //location = mainListItem.dong -->현재 null 오류 뜸.
            )
        }
            ?: listOf<ContentData>()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}