package com.example.equity.ViewModel.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diceroller.dataClasses.Customer
import com.example.equity.Network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class SearchViewModel : ViewModel() {
   private var _customer = MutableLiveData<Customer>()
   val customer : LiveData<Customer> get() = _customer

   private var _errorMsg = MutableLiveData<String>()
    val errorMsg : LiveData<String> get() = _errorMsg

   private var _successMsg = MutableLiveData<String>()
   val successMsg : LiveData<String> get() = _successMsg

   private val _isLoading = MutableLiveData<Boolean>()
   val isLoading: LiveData<Boolean> get() = _isLoading

   fun getCustomer(id: Long){
      viewModelScope.launch{
         _isLoading.value = true
         try{
            val result = RetrofitClient.instance.getCustomerByAcc(id).awaitResponse()
            println("****customer***")
            if(result.isSuccessful){
               val res = result.body()
               _customer.postValue(res?.entity)
               _successMsg.postValue(res?.message)
            }
            else{ _errorMsg.postValue("${result.errorBody()}")
            }
         }
         catch(e: Exception){
            _errorMsg.postValue("Error encountered ${e.message}")
         }
         _isLoading.value = false
      }}

}