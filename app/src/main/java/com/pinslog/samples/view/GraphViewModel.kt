package com.pinslog.samples.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinslog.samples.data.FakeDataRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor(
    private val fakeDataRepo: FakeDataRepo
) : ViewModel() {

    private val _fakeDataStateFlow = MutableStateFlow<Int?>(value = null)
    val fakeDataStateFlow = _fakeDataStateFlow.asStateFlow()

    private var currentJob: Job? = null

    fun startToGetFakeData(isContinue: Boolean) {
        currentJob?.cancel()
        _fakeDataStateFlow.value.apply {
            currentJob = viewModelScope.launch {
                fakeDataRepo.getFakeData(isContinue)
                    .collectLatest { fakeData ->
                        if (isContinue) {
                            fakeData?.let {
                                _fakeDataStateFlow.value = fakeData
                            }
                        } else {
                            _fakeDataStateFlow.value = null
                        }
                    }
            }
        }

    }


}