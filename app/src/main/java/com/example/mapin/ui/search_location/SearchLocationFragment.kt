package com.example.mapin.ui.search_location

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.mapin.DataStoreApplication
import com.example.mapin.MainActivity
import com.example.mapin.R
import com.example.mapin.databinding.FragmentSearchLocationBinding
import com.example.mapin.network.model.SearchLocationResponse
import com.example.mapin.network.service.SearchLocationService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchLocationFragment : Fragment() {

    private var _binding: FragmentSearchLocationBinding? = null


    private val binding get() = _binding!!
    private val viewModel: SearchLocationViewModel by viewModels()
    val searchLocationService by lazy { SearchLocationService.create() }

    lateinit var token:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchLocationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchLocationBtn.isClickable = false

        //지역 선택 임시
        var regionArray = resources.getStringArray(R.array.select_region_si)
        var arrayAdapter = ArrayAdapter(requireContext(), R.layout.region_dropdown_item, regionArray)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        var si: String? = null
        var gu:String? = null
        var dong:String? = null

        binding.autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->

            regionArray = resources.getStringArray(R.array.select_region_si)
            arrayAdapter = ArrayAdapter(requireContext(), R.layout.region_dropdown_item, regionArray)
            binding.autoCompleteTextView.setAdapter(arrayAdapter)

            si = null
            gu = null
            dong = null

            si = parent.getItemAtPosition(position).toString()

            if(parent.getItemAtPosition(position).toString() == "서울특별시"){
                val guArray = resources.getStringArray(R.array.seoul_gu)
                val arrayAdapter = ArrayAdapter(requireContext(), R.layout.region_dropdown_item, guArray)
                binding.autoCompleteTextView2.setAdapter(arrayAdapter)
            }
            else{
                val guArray = resources.getStringArray(R.array.suwon_gu)
                val arrayAdapter = ArrayAdapter(requireContext(), R.layout.region_dropdown_item, guArray)
                binding.autoCompleteTextView2.setAdapter(arrayAdapter)
            }


        }

        binding.autoCompleteTextView2.setOnItemClickListener { parent, view, position, id ->

            gu = parent.getItemAtPosition(position).toString()

            if (parent.getItemAtPosition(position).toString() == "강남구") {
                val guArray = resources.getStringArray(R.array.gangnam_dong)
                val arrayAdapter =
                    ArrayAdapter(requireContext(), R.layout.region_dropdown_item, guArray)
                binding.autoCompleteTextView3.setAdapter(arrayAdapter)
            } else {
                val guArray = resources.getStringArray(R.array.suwon_dong)
                val arrayAdapter =
                    ArrayAdapter(requireContext(), R.layout.region_dropdown_item, guArray)
                binding.autoCompleteTextView3.setAdapter(arrayAdapter)
            }
        }

        binding.autoCompleteTextView3.setOnItemClickListener { parent, view, position, id ->
            //최종적으로 POST 넘겨줄건 dong 변수
            dong = parent.getItemAtPosition(position).toString()
            binding.selectRegion.visibility = View.VISIBLE
            binding.searchLocationBtn.isClickable = true
            binding.selectTv.text = "$si $gu $dong"
        }

        //선택지역 초기화 버튼
        binding.clearRegionBtn.setOnClickListener {

            initializeRegion()
        }

        //임시 DataStore에서 토큰 가져오는 로직
        CoroutineScope(Dispatchers.Main).launch {
            token = DataStoreApplication.getInstance().getDataStore().token.first()
        }

        binding.searchLocationBtn.setOnClickListener {

            dong?.let { it ->
                searchLocationService.search(dong_name = it, authorization = "Bearer ${token}")
                    .enqueue(object : Callback<SearchLocationResponse> {
                        //서버 요청 성공
                        override fun onResponse(
                            call: Call<SearchLocationResponse>,
                            response: Response<SearchLocationResponse>
                        ) {
                            if(response.body()!=null){
                                Log.d("SearchLocationService",response.body().toString())
                            }

                        }

                        override fun onFailure(call: Call<SearchLocationResponse>, t: Throwable) {
                            Log.e("SearchLocationService", "onFailure: error. cause: ${t.message}")

                        }
                    })
            }
        }




    }

    private fun initializeRegion(){
        binding.autoCompleteTextView.text = null
        binding.autoCompleteTextView2.text = null
        binding.autoCompleteTextView3.text = null

        binding.selectRegion.visibility = View.INVISIBLE
        binding.searchLocationBtn.isClickable = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}