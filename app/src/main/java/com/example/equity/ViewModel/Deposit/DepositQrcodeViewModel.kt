package com.example.equity.ViewModel.Deposit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.equity.Network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class DepositQrcodeViewModel : ViewModel() {
    private var _bitmap = MutableLiveData<String>()
    val bitmap : LiveData<String> get() = _bitmap


    fun getTransaction(id: String){
        viewModelScope.launch{
            try{
                val result = RetrofitClient.instance.getTran(id).awaitResponse()
                if(result.isSuccessful){
                    val res = result.body()
                    val tran = res?.entity
                    println("*****Transaction")
                    _bitmap.postValue(tran?.imageData)

                }
                else{

                }
            }
            catch(e : Exception){
              println("Error encountered ${e.message}")
            }

    }}
}