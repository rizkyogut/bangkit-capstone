package com.bangkit2022.boemboe.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit2022.boemboe.api.ApiConfig
import com.bangkit2022.boemboe.api.ItemSpices
import com.bangkit2022.boemboe.api.ResponseSpices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewModel : ViewModel() {

    private val _isShowLoading = MutableLiveData<Boolean>()
    val isShowLoading: LiveData<Boolean> = _isShowLoading

    private val _listSpice = MutableLiveData<ArrayList<ItemSpices>>()
    val listSpice: LiveData<ArrayList<ItemSpices>> = _listSpice

    fun setListSpice() {
        _isShowLoading.value = true
        val client = ApiConfig.getApiService().getSpices()
        client.enqueue(object  : Callback<ResponseSpices> {
            override fun onResponse(
                call: Call<ResponseSpices>,
                response: Response<ResponseSpices>
            ) {
                _isShowLoading.value = false

                if (response.isSuccessful) {
                    _listSpice.postValue(response.body()?.data)
                    Log.d(TAG, "berhasil dapat data")
                } else {
                    Log.e(TAG, "onFailure34: ${response.message()}")
                    Log.d(TAG, "gagal dapat data")
                }
            }

            override fun onFailure(call: Call<ResponseSpices>, t: Throwable) {
                _isShowLoading.value = true
                Log.e(TAG, "onFailure: ${t.message}")
                Log.d(TAG, "gagal total")
            }
        })

    }



    companion object {
        private const val TAG = "HomeFragmentViewModel"
    }
}

