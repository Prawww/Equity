package com.example.equity.ViewModel.Withdraw

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diceroller.dataClasses.Withdraw
import com.example.equity.Network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class WithdrawConfirmViewModel : ViewModel() {

    private var _withdraw = MutableLiveData<Withdraw>()
    private val withdraw : LiveData<Withdraw> get () = _withdraw

    private var _successMsg = MutableLiveData<String>()
    private val successMsg : LiveData<String> get() = _successMsg

    private var _errorMsg = MutableLiveData<String>()
    private val errorMsg : LiveData<String> get() = _errorMsg
    fun postWithdraw(id: Long, withdraw: Withdraw){
        viewModelScope.launch{
            try{
             val result = RetrofitClient.instance.withdrawReq(id, withdraw).awaitResponse()
                 if(result.isSuccessful){
                 _successMsg.postValue("Data sent directly")
                 }
                else{
                    _errorMsg.postValue("Error encountered ${result.errorBody()}")
             }
         }
            catch(e:Exception){
                _errorMsg.postValue("Error encountered ${e.message}")

            }
        }
    }
}