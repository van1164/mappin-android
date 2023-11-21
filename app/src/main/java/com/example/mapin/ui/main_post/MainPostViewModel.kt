package com.example.mapin.ui.main_post

import androidx.lifecycle.ViewModel
import com.example.mapin.ui.main_content.ContentData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainLostViewModel : ViewModel() {
    private val _mainContentData = MutableStateFlow<List<ContentData>?>(null)
    val mainContentData: StateFlow<List<ContentData>?> = _mainContentData.asStateFlow()

}