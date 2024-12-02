package com.capstone.pantauharga.ui.savedItem

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.pantauharga.database.FavoriteEvents
import com.capstone.pantauharga.database.FavoriteRoomDatabase
import com.capstone.pantauharga.repository.EventsRepository
import kotlinx.coroutines.launch

class SavedItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: EventsRepository
    val favoriteEvents: LiveData<List<FavoriteEvents>>
    val error: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        val favoriteDao = FavoriteRoomDatabase.getDatabase(application).favoriteEventsDao()
        repository = EventsRepository(favoriteDao)
        favoriteEvents = repository.getAllFavoriteEvents()
    }

    fun getFavoriteEvents() {
        repository.getAllFavoriteEvents()
    }

    fun removeFavoriteEvent(event: FavoriteEvents) {
        viewModelScope.launch {
            repository.delete(event)
            error.value = false
        }
    }

    fun setError(isError: Boolean) {
        error.value = isError
    }
}
