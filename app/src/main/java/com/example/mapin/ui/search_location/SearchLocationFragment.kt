package com.example.mapin.ui.search_location

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mapin.MainActivity
import com.example.mapin.R
import com.example.mapin.databinding.FragmentSearchLocationBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SearchLocationFragment : Fragment() {

    private var _binding: FragmentSearchLocationBinding? = null


    private val binding get() = _binding!!
    private val viewModel: SearchLocationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchLocationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).findViewById<FloatingActionButton>(R.id.fab).visibility =View.INVISIBLE


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
            binding.selectTv.text = "$si $gu $dong"
        }

        //선택지역 초기화 버튼
        binding.clearRegionBtn.setOnClickListener {

            initializeRegion()
        }

        binding.searchLocationBtn.setOnClickListener {
            //TODO: POST
            Log.d("SEARCHLOATION",dong.toString())
        }

    }

    private fun initializeRegion(){
        binding.autoCompleteTextView.text = null
        binding.autoCompleteTextView2.text = null
        binding.autoCompleteTextView3.text = null

        binding.selectRegion.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        (activity as MainActivity).findViewById<FloatingActionButton>(R.id.fab).visibility =View.VISIBLE
        super.onDestroyView()
        _binding = null
    }


}