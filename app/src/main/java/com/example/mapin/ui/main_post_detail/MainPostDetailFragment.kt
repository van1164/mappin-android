package com.example.mapin.ui.main_post_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mapin.DataStoreApplication
import com.example.mapin.R
import com.example.mapin.databinding.FragmentMainContentDetailBinding
import com.example.mapin.databinding.FragmentMainPostDetailBinding
import com.example.mapin.network.model.DetailResponse
import com.example.mapin.network.model.PostDetailResponse
import com.example.mapin.network.model.SearchCategoryResponse
import com.example.mapin.network.service.DetailService
import com.example.mapin.network.service.PostService
import com.example.mapin.ui.main_content.ContentData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainPostDetailFragment : Fragment() {

    companion object {
        fun newInstance() = MainPostDetailFragment()
    }

    private lateinit var viewModel: MainPostDetailViewModel
    private var _binding: FragmentMainPostDetailBinding? = null

    val detailService by lazy { PostService.create() }
    lateinit var token:String


    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainPostDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainPostDetailViewModel::class.java]

        val id = requireArguments().getString("id")
        Log.e("MainContentDetailFragment",id.toString())

        val mapView = MapView(requireContext())
        val marker = MapPOIItem()

        //임시 DataStore에서 토큰 가져오는 로직
        CoroutineScope(Dispatchers.Default).launch {
            launch {
                token = DataStoreApplication.getInstance().getDataStore().token.first()
            }.join()

            launch {
                id?.let { it ->
                    detailService.search(id = it, authorization = "Bearer ${token}")
                        .enqueue(object : Callback<PostDetailResponse> {
                            //서버 요청 성공
                            override fun onResponse(
                                call: Call<PostDetailResponse>,
                                response: Response<PostDetailResponse>
                            ) {
                                if(response.body()!=null){
                                    Log.d("MainContentDetailFragment",response.body().toString())
                                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(response.body()!!.y, response.body()!!.x), false)
                                    marker.itemName = response.body()!!.dong
                                    marker.tag = 0
                                    marker.mapPoint = MapPoint.mapPointWithGeoCoord(response.body()!!.y, response.body()!!.x)
                                    marker.markerType = MapPOIItem.MarkerType.BluePin

                                    mapView.addPOIItem(marker)
                                    binding.mapView.addView(mapView)

                                    binding.detailContentText.text = response.body()!!.content
                                    binding.DetailTitleText.text = response.body()!!.title
                                    binding.detailTimeText.text = response.body()!!.createdAt
                                    Glide.with(this@MainPostDetailFragment)
                                        .load(response.body()!!.image)
                                        .into(binding.detailImage)

                                }

                            }

                            override fun onFailure(call: Call<PostDetailResponse>, t: Throwable) {

                                Log.e("MainContentDetailFragment", "onFailure: error. cause: ${t.message}")

                            }
                        })
                }
            }

        }

        binding.detailContactBtn.setOnClickListener{
            startActivity(Intent("android.intent.action.DIAL", Uri.parse("tel:01029321164")))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}