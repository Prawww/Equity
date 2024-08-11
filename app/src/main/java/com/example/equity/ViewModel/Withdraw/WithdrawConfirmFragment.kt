package com.example.equity.ViewModel.Withdraw

import android.graphics.Bitmap
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.diceroller.dataClasses.Withdraw
import com.example.equity.R
import com.example.equity.ViewModel.Deposit.DepositConfirmViewModel
import com.example.equity.databinding.FragmentDepositConfirmBinding
import com.example.equity.databinding.FragmentWithdrawConfirmBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date

class WithdrawConfirmFragment : Fragment() {

    private var _binding : FragmentWithdrawConfirmBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WithdrawConfirmViewModel
    object SharedData {
        var imageBitmap: Bitmap? = null
        var st: String? = null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWithdrawConfirmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[WithdrawConfirmViewModel::class.java]

        val amt = arguments?.getString("amount")
        val name = arguments?.getString("name")
        val nationalId = arguments?.getString("nationalId")
        val accno = arguments?.getString("account")
        val baln = arguments?.getString("balance")
        val currency = arguments?.getString("currency")
        var bundle : Bundle? = null
        val image = binding.imageViewQR

        binding.currency.text = currency
        binding.txtAccount.text = accno
        binding.txtAmt.text = amt

        val specificTime = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
        val dateString = dateFormat.format(specificTime)
        val tranId: String = generateRandomString(6)
        val tran: String = "Withdraw"
        binding.downloadQr.setOnClickListener() {
            val doubleAmt = amt!!.toDouble()

            var combi =
                "Name:$name,NationalID: $nationalId ,Account: $accno, Balance: $baln, TransactionID: $tranId, Date: $dateString, Amount: $amt, TransactionType:$tran, Currency: $currency"
            var qrCodeBitmap = generateQRCode(combi, 512)
            var bitImage = convertImageToBase64(qrCodeBitmap)

            //image.setImageBitmap(qrCodeBitmap)
            var withdraw = Withdraw(1,tranId, doubleAmt, dateString,false,bitImage, 0,0,tran, currency!! )
            viewModel.postWithdraw(1, withdraw)

            SharedData.imageBitmap = qrCodeBitmap
            SharedData.st = "nyophi"

            findNavController().navigate(R.id.action_WithdrawConfirmFragment_to_WithdrawQrcodeFragment)



        }

    }}
private fun generateQRCode(text: String, size: Int): Bitmap? {
    val multiFormatWriter = MultiFormatWriter()
    try {
        val bitMatrix: BitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, size, size)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
            }
        }
        return bitmap
    } catch (e: WriterException) {
        e.printStackTrace()
    }
    return null
}

private fun generateRandomString(length: Int): String {
    val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { charset.random() }
        .joinToString("")
}

private fun convertImageToBase64(bitmap: Bitmap?): String {
    val outputStream = ByteArrayOutputStream()
    bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream) // Use the appropriate format and quality here
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

