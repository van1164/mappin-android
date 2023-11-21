package com.example.mapin.ui.main_content

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
import com.example.mapin.network.model.LoginResponse
import com.example.mapin.network.model.MainListResponse
import com.example.mapin.network.service.LoginRequest
import com.example.mapin.network.service.LoginService
import com.example.mapin.network.service.SearchMainListInterface
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainContentFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null


    private val binding get() = _binding!!
    private val viewModel: MainContentViewModel by viewModels()
    private lateinit var token : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            if (View.GONE == binding.fabBGLayout.visibility) {
                showFABMenu()
            } else {
                closeFABMenu()
            }
        }

        binding.fabBGLayout.setOnClickListener { closeFABMenu() }

        binding.fab1.setOnClickListener {
            //TODO:가게 이름으로 검색
            findNavController().navigate(R.id.action_FirstFragment_to_searchShop)

        }
        binding.fab2.setOnClickListener {
            //TODO:지역별 검색
            findNavController().navigate(R.id.action_FirstFragment_to_searchLocation)

        }
        binding.fab3.setOnClickListener {
            //TODO:제품별 검색
            findNavController().navigate(R.id.action_FirstFragment_to_searchCategory)

        }
        binding.fab4.setOnClickListener {
            //TODO:게시물 작성
            findNavController().navigate(R.id.action_FirstFragment_to_createPostFragment)

        }
        binding.fab5.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_createContentFragment)
            //TODO:분실물 등록
        }

        val mainRecyclerView = binding.mainContentRecyclerView
        val mainRecyclerView2 = binding.mainContentRecyclerView2
        token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2Rmc2FmIiwicm9sZSI6IkFETUlOIiwiZXhwIjoxNzAwNjQ3Nzk4fQ.eNsDDuwUT5M-vKnmD417nAfoDC--jlBRevDjipd7_D8ISrBW05xwAK0Qu1xciSvHrfIDe7dus5xWKjiEIGoX4A"

        val item = listOf<ContentData>()
        val mainRecyclerAdapter = MainContentAdapter()
        mainRecyclerView.adapter = mainRecyclerAdapter

        mainRecyclerView2.adapter = mainRecyclerAdapter
        //mainRecyclerView.layoutManager = LinearLayoutManager(context)
        mainRecyclerView.layoutManager = GridLayoutManager(requireActivity(),5, GridLayoutManager.HORIZONTAL, false)
        mainRecyclerView2.layoutManager = GridLayoutManager(requireActivity(),5, GridLayoutManager.HORIZONTAL, false)
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

    private fun showFABMenu() {
        binding.fabLayout1.visibility = View.VISIBLE
        binding.fabLayout2.visibility = View.VISIBLE
        binding.fabLayout3.visibility = View.VISIBLE
        binding.fabLayout4.visibility = View.VISIBLE
        binding.fabLayout5.visibility = View.VISIBLE
        binding.fabBGLayout.visibility = View.VISIBLE
        binding.fab.animate().rotationBy(45F)
        binding.fabLayout1.animate().translationY(-resources.getDimension(R.dimen.standard_75))
        binding.fabLayout2.animate().translationY(-resources.getDimension(R.dimen.standard_120))
        binding.fabLayout3.animate().translationY(-resources.getDimension(R.dimen.standard_165))
        binding.fabLayout4.animate().translationY(-resources.getDimension(R.dimen.standard_210))
        binding.fabLayout5.animate().translationY(-resources.getDimension(R.dimen.standard_255))
    }

    private fun closeFABMenu() {
        binding.fabBGLayout.visibility = View.GONE
        binding.fab.animate().rotation(0F)
        binding.fabLayout1.animate().translationY(0f)
        binding.fabLayout2.animate().translationY(0f)
        binding.fabLayout3.animate().translationY(0f)
        binding.fabLayout3.animate().translationY(0f)
        binding.fabLayout4.animate().translationY(0f)
        binding.fabLayout5.animate().translationY(0f)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {}
                override fun onAnimationEnd(animator: Animator) {
                    if (View.GONE == binding.fabBGLayout.visibility) {
                        binding.fabLayout1.visibility = View.GONE
                        binding.fabLayout2.visibility = View.GONE
                        binding.fabLayout3.visibility = View.GONE
                        binding.fabLayout4.visibility = View.GONE
                        binding.fabLayout5.visibility = View.GONE
                    }
                }

                override fun onAnimationCancel(animator: Animator) {}
                override fun onAnimationRepeat(animator: Animator) {}
            })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}