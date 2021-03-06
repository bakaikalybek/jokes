package kg.bakai.sunrisejokes.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.bakai.sunrisejokes.domain.model.Joke
import kg.bakai.sunrisejokes.usecase.GetJokesUseCase
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val getJokesUseCase: GetJokesUseCase) : ViewModel() {
    private val _jokes = MutableLiveData<List<Joke>>()
    val jokes: LiveData<List<Joke>> = _jokes

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getJokes() = viewModelScope.launch {
        _loading.value = true

        try {
            _jokes.value = getJokesUseCase.getJokes()
            _loading.value = false
        } catch (exception: Exception) {
            _error.value = exception.message
            _loading.value = false
        }
    }
}