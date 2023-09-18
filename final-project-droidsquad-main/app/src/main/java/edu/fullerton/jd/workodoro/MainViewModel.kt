package edu.fullerton.jd.workodoro

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.fullerton.jd.workodoro.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {
    val myResponse2:MutableLiveData<Response<QuoteModel>>  = MutableLiveData()

    fun getQuote(number: Int){
        viewModelScope.launch {
            val response: Response<QuoteModel> = repository.getQuote(number)
            myResponse2.value= response
        }
    }
}