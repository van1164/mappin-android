package com.example.mapin.ui.main_content

import android.animation.Animator
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mapin.DataStoreApplication
import com.example.mapin.MainActivity
import com.example.mapin.R
import com.example.mapin.data.model.AdItem
import com.example.mapin.databinding.FragmentFirstBinding
import com.example.mapin.network.model.LoginResponse
import com.example.mapin.network.model.MainListResponse
import com.example.mapin.network.service.LoginRequest
import com.example.mapin.network.service.LoginService
import com.example.mapin.network.service.SearchMainListInterface
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainContentFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null


    private val binding get() = _binding!!
    private val viewModel: MainContentViewModel by viewModels()
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

        binding.fab4.setOnClickListener {
            //TODO:게시물 작성
            findNavController().navigate(R.id.action_FirstFragment_to_createPostFragment)

        }
        binding.fab5.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_createContentFragment)
            //TODO:분실물 등록
        }

        binding.btnCategory.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_searchCategory)
        }

        binding.btnLocation.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_searchLocation)
        }

        binding.btnShop.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_searchShop)
        }

        binding.btnNearbyLost.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_mainLostFragment)
        }

        binding.btnPost.setOnClickListener{
            findNavController().navigate(R.id.action_FirstFragment_to_mainPostFragment)
        }

        val AdRecyclerView = binding.rvAd
        val adRecyclerAdapter = AdAdapter()
        AdRecyclerView.adapter = adRecyclerAdapter
        AdRecyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)


        val adImage1: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ad1)
        val adImage2: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ad2)
        val adImage3: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ad3)
        val adImage4: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ad4)


        adRecyclerAdapter.submitList(listOf(AdItem(adImage1),AdItem(adImage2), AdItem(adImage3), AdItem(adImage4)))
        startAutoScroll(adRecyclerAdapter, AdRecyclerView)


    }


    private fun showFABMenu() {
        binding.fabLayout4.visibility = View.VISIBLE
        binding.fabLayout5.visibility = View.VISIBLE
        binding.fabBGLayout.visibility = View.VISIBLE
        binding.fab.animate().rotationBy(45F)
        binding.fabLayout4.animate().translationY(-resources.getDimension(R.dimen.standard_75))
        binding.fabLayout5.animate().translationY(-resources.getDimension(R.dimen.standard_120))
    }

    private fun closeFABMenu() {
        binding.fabBGLayout.visibility = View.GONE
        binding.fab.animate().rotation(0F)
        binding.fabLayout4.animate().translationY(0f)
        binding.fabLayout5.animate().translationY(0f)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {}
                override fun onAnimationEnd(animator: Animator) {
                    if (View.GONE == binding.fabBGLayout.visibility) {
                        binding.fabLayout4.visibility = View.GONE
                        binding.fabLayout5.visibility = View.GONE
                    }
                }

                override fun onAnimationCancel(animator: Animator) {}
                override fun onAnimationRepeat(animator: Animator) {}
            })

    }

    private fun startAutoScroll(adapter:AdAdapter, recyclerView: RecyclerView) {
        var scrollPosition = 0
        val scrollDelay: Long = 3000 // 3초
        lifecycleScope.launch {
            while (true) {
                if (scrollPosition < adapter.itemCount) {
                    recyclerView.smoothScrollToPosition(scrollPosition)
                    scrollPosition++
                } else {
                    scrollPosition = 0
                }

                // 지정된 시간(3초) 대기
                delay(scrollDelay)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}