package com.example.equity.ViewModel.Home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diceroller.dataClasses.Customer
import com.example.equity.Network.RetrofitClient
import com.example.equity.dataClasses.post
import com.example.equity.dataClasses.postItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class HomeViewModel : ViewModel() {


    private var _customer  = MutableLiveData<Customer>()
    val customer : LiveData<Customer> get() = _customer

    private var _post = MutableLiveData<postItem>()
    val post: LiveData<postItem> get() = _post

    init{
        getCustomerByAcc(34550)
    }
    fun getCustomerByAcc(id: Long) {
        viewModelScope.launch {
            try {
                val result = RetrofitClient.instance.getCustomerByAcc(id).awaitResponse()
               // val result = RetrofitClient.instance.getPosts(id).awaitResponse()
                if (result.isSuccessful) {
                    val res = result.body()
                    println("****customer")
                    _customer.postValue(res?.entity)}


                 else {
                    Log.i("CustomerViewModel", "Error encountered: ${result.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("CustomerViewModel", "Exception encountered", e)
            }
        }
    }}