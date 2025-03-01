package com.example.mobile_development_2_1.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_development_2_1.DataResponce
import com.example.mobile_development_2_1.DayListAdapter
import com.example.mobile_development_2_1.DayPrognosis
import com.example.mobile_development_2_1.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    private val _selectedCity = MutableLiveData<String>().apply {
        value = "Shklov"
    }
    val selectedCity = _selectedCity

    private val _isCelsius = MutableLiveData<Boolean>().apply {
        value = true
    }

    val isCelsius = _isCelsius

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage = _errorMessage

    private val _items = MutableLiveData<DataResponce>()
    val items: LiveData<DataResponce> get() = _items

    fun fetchWeather(){
        var units = "imperial"
        if(isCelsius.value == true){
            units = "metric"
        }
        selectedCity.value?.let {
            RetrofitClient.instance.GetWeather(it, units).enqueue(object : Callback<DataResponce> {
                override fun onResponse(call: Call<DataResponce>, response: Response<DataResponce>) {
                    if (response.isSuccessful) {
                        _items.value = response.body()
                    } else{
                        _errorMessage.value = "Город не найден"
                    }
                }

                override fun onFailure(call: Call<DataResponce>, t: Throwable) {
                    _errorMessage.value = "Ошибка соединения: ${t.message}"
                }
            })
        }
    }
}