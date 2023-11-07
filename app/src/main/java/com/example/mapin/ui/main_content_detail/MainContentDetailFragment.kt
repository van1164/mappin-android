package com.example.mapin.ui.main_content_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mapin.MainActivity
import com.example.mapin.R
import com.example.mapin.databinding.FragmentMainContentDetailBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class MainContentDetailFragment : Fragment() {

    companion object {
        fun newInstance() = MainContentDetailFragment()
    }

    private lateinit var viewModel: MainContentDetailViewModel
    private var _binding: FragmentMainContentDetailBinding? = null


    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainContentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainContentDetailViewModel::class.java]
        (activity as MainActivity).findViewById<FloatingActionButton>(R.id.fab).visibility =View.INVISIBLE
        val mapView = MapView(requireContext())
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.24793398172821, 127.0764451494671), false)

        val marker = MapPOIItem()
        marker.itemName = "카페 칸나"
        marker.tag = 0
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(37.24793398172821, 127.0764451494671)
        marker.markerType = MapPOIItem.MarkerType.BluePin

        mapView.addPOIItem(marker)
        binding.mapView.addView(mapView)

        binding.detailContentText.text ="이름이 박선우라고 써있는데, 버리려다 참았네요\n동해물과\n백두산이\n마르고\n닳도록"
        binding.DetailTitleText.text = "뭔지 모르는 갤럭시 핸드폰 발견했어요"
        binding.detailTimeText.text = "3시간전"
        binding.detailImage.setImageResource(R.drawable.sample)

    }

    //FAB이 MainActivity에 있어서 임시로 해놓은 기능.
    //기존의 경우 MainContentDetailFragment에서 뒤로가면 FAB이 안보이기 때문에
    override fun onDestroyView() {
        (activity as MainActivity).findViewById<FloatingActionButton>(R.id.fab).visibility =View.VISIBLE
        super.onDestroyView()
        _binding = null
    }

}