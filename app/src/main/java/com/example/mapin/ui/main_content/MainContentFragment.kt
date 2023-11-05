package com.example.mapin.ui.main_content

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mapin.databinding.FragmentFirstBinding

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
        val mainRecyclerView = binding.mainContentRecyclerView
        Log.d("BBBBBBBBBBBBBBBBb", "BBBBBBBBBBBBBBBBBBB")
        val item = listOf(
            ContentData("http://placehold.it/200/200", "테스트", "테스트전", "테스트위치"),
            ContentData("http://placehold.it/200/200", "테스트", "테스트전", "테스트위치"),
            ContentData("http://placehold.it/200/200", "테스트", "테스트전", "테스트위치"),
            ContentData("http://placehold.it/200/200", "테스트", "테스트전", "테스트위치"),
            ContentData("http://placehold.it/200/200", "테스트", "테스트전", "테스트위치"),
            ContentData("http://placehold.it/200/200", "테스트", "테스트전", "테스트위치")
        )
        val mainRecyclerAdapter = MainContentAdapter()
        mainRecyclerView.adapter = mainRecyclerAdapter
        mainRecyclerView.layoutManager = LinearLayoutManager(context)
        mainRecyclerAdapter.submitList(listOf<ContentData>())
        mainRecyclerAdapter.submitList(item)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}