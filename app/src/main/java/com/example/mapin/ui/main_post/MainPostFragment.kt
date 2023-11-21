package com.example.mapin.ui.main_post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mapin.databinding.FragmentMainPostBinding
import com.example.mapin.network.model.Post
import com.example.mapin.network.model.PostAllResponse
import com.example.mapin.network.service.PostService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPostFragment : Fragment() {

    private var _binding: FragmentMainPostBinding? = null


    private val binding get() = _binding!!
    private val viewModel: MainLostViewModel by viewModels()
    private lateinit var token : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainPostBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainRecyclerView = binding.mainPostRecyclerView
        //val mainRecyclerView2 = binding.mainContentRecyclerView2
        token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2Rmc2FmIiwicm9sZSI6IkFETUlOIiwiZXhwIjoxNzAwNjQ3Nzk4fQ.eNsDDuwUT5M-vKnmD417nAfoDC--jlBRevDjipd7_D8ISrBW05xwAK0Qu1xciSvHrfIDe7dus5xWKjiEIGoX4A"

        val item = listOf<Post>()
        val mainRecyclerAdapter = MainPostAdapter()
        mainRecyclerView.adapter = mainRecyclerAdapter

        //mainRecyclerView2.adapter = mainRecyclerAdapter
        mainRecyclerView.layoutManager = LinearLayoutManager(context)
        //mainRecyclerView2.layoutManager = GridLayoutManager(requireActivity(),5, GridLayoutManager.HORIZONTAL, false)
        PostService.create()
            .searchAll("Bearer ${token}")
            .enqueue(object : Callback<PostAllResponse> {
                override fun onResponse(
                    call: Call<PostAllResponse>,
                    response: Response<PostAllResponse>
                ) {
                    Log.d("SearchMainListInterface", response.body().toString())
                    val itemList = response.body()!!.posts
                    mainRecyclerAdapter.submitList(itemList)

                }
                override fun onFailure(call: Call<PostAllResponse>, t: Throwable) {
                    Log.d("XXXXXXXXXXXXXXXXXXXXXXX", "response.body().toString()")
                }
            })



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}