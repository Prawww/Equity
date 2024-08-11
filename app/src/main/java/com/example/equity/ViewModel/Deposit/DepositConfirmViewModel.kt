package com.example.equity.ViewModel.Deposit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diceroller.dataClasses.Deposit
import com.example.equity.Network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class DepositConfirmViewModel : ViewModel() {
    private var _deposit = MutableLiveData<Deposit>()

    private val deposit : LiveData<Deposit> get() = _deposit

    private var _successMsg = MutableLiveData<String>()
    val successMsg : LiveData<String> get() = _successMsg

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> get() = _errorMsg

    fun postDeposit(id: Long, deposit: Deposit){
        viewModelScope.launch {
            try {
                val result = RetrofitClient.instance.depositReq(id, deposit).awaitResponse()
                // val result = RetrofitClient.instance.getPosts(id).awaitResponse()
                if (result.isSuccessful) {
                    println("****Data sent directly")
                    _successMsg.postValue("Deposit request successful")

                }
                else {
                    Log.i("DepositViewModel", "Error encountered: ${result.errorBody()}")
                    _errorMsg.postValue("Error encountered: ${result.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("DepositViewModel", "Exception encountered", e)
                _errorMsg.postValue("Exception encountered: ${e.message}")
            }
        }
    }

}