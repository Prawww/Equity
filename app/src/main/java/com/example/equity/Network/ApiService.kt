
import com.example.diceroller.dataClasses.Customer
import com.example.diceroller.dataClasses.CustomerAccount
import com.example.diceroller.dataClasses.Deposit
import com.example.diceroller.dataClasses.EntityResponse
import com.example.diceroller.dataClasses.Transaction
import com.example.diceroller.dataClasses.Withdraw
import com.example.equity.dataClasses.post
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
//    @GET("tran/getTran")
//    fun getUsers(@Query("id") id: Long): Call<EntityResponse<Tran>>

    @GET("Customer/getCustomer")
    fun getCustomer(@Query("id") id: Long): Call<EntityResponse<Customer>>

    @POST("account/withdrawReq")
    fun withdrawReq(@Query("id") id: Long,@Body data: Withdraw): Call<Void>

    @GET("Transaction/getTransactionById")
    fun getTran(@Query("id") id: String): Call<EntityResponse<Transaction>>

    @POST("account/depositReq")
    fun depositReq(@Query("id") id: Long,@Body data: Deposit): Call<Void>

    @GET("account/get")
    fun getAccount(@Query("id") id: Long): Call<EntityResponse<CustomerAccount>>

    @GET("account/findCustomerByAccno")
    fun getCustomerByAcc(@Query("id") id: Long): Call<EntityResponse<Customer>>

    @GET("/posts")
    fun getPosts(@Query("id") id:Long):Call<post>


}
