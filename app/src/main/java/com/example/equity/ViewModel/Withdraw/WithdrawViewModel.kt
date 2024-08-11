package com.example.equity.ViewModel.Withdraw

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diceroller.dataClasses.Customer
import com.example.diceroller.dataClasses.CustomerAccount
import com.example.equity.Network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class WithdrawViewModel : ViewModel() {
    private var _account = MutableLiveData<CustomerAccount>()
    val account : LiveData<CustomerAccount> get() = _account

    private var _customer = MutableLiveData<Customer>()
    val customer : LiveData<Customer> get() = _customer

    init{
        getCustomer(1)
    }
    fun getCustomer(id:Long){
        viewModelScope.launch {
            try {
                val result = RetrofitClient.instance.getCustomer(id).awaitResponse()
                // val result = RetrofitClient.instance.getPosts(id).awaitResponse()
                if (result.isSuccessful) {
                    val res = result.body()
                    println("****customer")
                    val customer = res?.entity
//                    val accounts = customer?.customerAccount
//                    for(account in accounts!!)
//                    _account.postValue(account)}
                    _customer.postValue(customer)

                }
                else {
                    Log.i("DepositViewModel", "Error encountered: ${result.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("DepositViewModel", "Exception encountered", e)
            }
        }
    }
}