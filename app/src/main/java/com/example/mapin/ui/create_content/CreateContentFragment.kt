package com.example.mapin.ui.create_content

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mapin.R
import com.example.mapin.databinding.FragmentCreateContentBinding
import com.example.mapin.databinding.FragmentFirstBinding
import com.example.mapin.databinding.FragmentMainContentDetailBinding
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class CreateContentFragment : Fragment() {

    companion object {
        fun newInstance() = CreateContentFragment()
    }

    private lateinit var viewModel: CreateContentViewModel
    private var _binding: FragmentCreateContentBinding? = null

    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let{
            var bitmap : Bitmap
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source =ImageDecoder.createSource(getContentResolver(), uri)
                bitmap = ImageDecoder.decodeBitmap(source)
            } else {
                bitmap= MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            }
            rectangleView.drawImage(bitmap)
        }

    }
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateContentViewModel::class.java)
        val mapView = MapView(requireContext())
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.24793398172821, 127.0764451494671), false)
        binding.createMapView.addView(mapView)


        // TODO: Use the ViewModel
    }

}