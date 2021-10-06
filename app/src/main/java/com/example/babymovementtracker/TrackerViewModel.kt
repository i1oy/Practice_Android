package com.example.babymovementtracker

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.babymovementtracker.data.Record
import com.example.babymovementtracker.data.room.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TrackerViewModel(
    private val context: Context,
) : ViewModel() {
    var counter = 0
    private val _kickTimes = MutableLiveData<Int>(0)
    val kickTimes: LiveData<Int> = _kickTimes

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    private val _state = MutableStateFlow(RecordViewState())

    val state: StateFlow<RecordViewState>
        get() = _state

    private var db: AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "data.db")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    init {
        viewModelScope.launch {
            updateViewState()
        }
    }


    fun softKick() {
        Log.d(counter.toString(), "KICKED")
        db.recordDao().insertAll(Record(tapped = LocalDateTime.now()))
        updateViewState()
    }

    private fun updateViewState() {
        val list = db.recordDao().getAll()
        val viewState = RecordViewState(records = list)
        _state.value = viewState
    }
}

data class RecordViewState(
    val records: List<Record> = emptyList(),
)