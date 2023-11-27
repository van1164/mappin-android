package com.example.mapin.ui.create_content

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mapin.DataStoreApplication
import com.example.mapin.R
import com.example.mapin.databinding.FragmentCreateContentBinding
import com.example.mapin.network.model.Info
import com.example.mapin.network.service.BitmapRequestBody
import com.example.mapin.network.service.CreateService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CreateContentFragment : Fragment() {

    companion object {
        fun newInstance() = CreateContentFragment()
    }

    private lateinit var viewModel: CreateContentViewModel
    private var _binding: FragmentCreateContentBinding? = null
    lateinit var token: String
    var image: Bitmap? = null
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            var bitmap: Bitmap
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(requireActivity().getContentResolver(), uri)
                bitmap = ImageDecoder.decodeBitmap(source)
            } else {
                bitmap =
                    MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri)
            }
            binding.imageSelect.setImageBitmap(bitmap)
            image = bitmap
        }

    }
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        CoroutineScope(Dispatchers.Default).launch {
            launch {
                token = DataStoreApplication.getInstance().getDataStore().token.first()
            }
        }
        _binding = FragmentCreateContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateContentViewModel::class.java)
        val mapView = MapView(requireContext())
        mapView.setMapCenterPoint(
            MapPoint.mapPointWithGeoCoord(
                37.240995,
                127.079732
            ), false
        )
        binding.createMapView.addView(mapView)

        var categoryArray = resources.getStringArray(R.array.select_category)
        var arrayAdapter = ArrayAdapter(requireContext(), R.layout.region_dropdown_item, categoryArray)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        var category:String? = null

        binding.autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->

            category = parent.getItemAtPosition(position).toString()
            Log.d("CreateContentFragment", category!!)
        }

        binding.createButton.setOnClickListener {
            Log.d("CreateContentFragment", category!!)
            if(category!=null){
                Log.d("XXXXXXXXXXXXXXXX", image.toString())

                GlobalScope.launch {
                    val info = Info(
                        category = category!!,
                        binding.editTextText2.text.toString(),
                        "영통동",
                        "2023-01-01",
                        binding.editTextText.text.toString(),
                        x = 127.079732,
                        y = 37.240995,
                        )
                    val json = Gson().toJson(info)
                    image?.let { it1 ->
                        CreateService.create().create(
                            authorization = "Bearer ${token}",
                            MultipartBody.Part.createFormData("image","sdf.png",BitmapRequestBody(it1)),
                            RequestBody.create("application/json".toMediaTypeOrNull(),json)
                        ).execute()
                    }
                }
                findNavController().navigate(R.id.action_createContentFragment_to_mainLostFragment)
            }
            else {
                Toast.makeText(requireContext(), "category를 입력해주세요!", Toast.LENGTH_SHORT).show()
            }


        }

        binding.imageSelect.setOnClickListener {
            getContent.launch("image/*")
        }
        // TODO: Use the ViewModel
    }

}