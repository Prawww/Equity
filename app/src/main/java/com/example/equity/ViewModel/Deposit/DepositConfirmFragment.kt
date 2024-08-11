package com.example.equity.ViewModel.Deposit

import android.graphics.Bitmap
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.diceroller.dataClasses.Deposit
import com.example.equity.R
import com.example.equity.databinding.FragmentDepositConfirmBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date

class DepositConfirmFragment : Fragment() {

  private var _binding : FragmentDepositConfirmBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DepositConfirmViewModel
    object SharedData {
        var imageBitmap: Bitmap? = null
        var st: String? = null
        var base64 : String? = null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDepositConfirmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DepositConfirmViewModel::class.java]

        val amt = arguments?.getString("amount")
        val dep = arguments?.getString("depositor")
        val depId = arguments?.getString("depositorId")
        val depNo = arguments?.getString("depositorNo")
        val name = arguments?.getString("name")
        val nationalId = arguments?.getString("nationalId")
        val accno = arguments?.getString("account")
        val baln = arguments?.getString("balance")
        val currency = arguments?.getString("currency")
        var bundle : Bundle? = null


        binding.dep.text = dep
        binding.depId.text = depId
        binding.depNo.text = depNo
        binding.currency.text = currency
        binding.txtAccount.text = accno
        binding.txtAmt.text = amt

        val specificTime = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
        val dateString = dateFormat.format(specificTime)
        val tranId: String = generateRandomString(6)
        val tran: String = "Deposit"
        binding.downloadQr.setOnClickListener {


            var combi =
                "Name:$name,NationalID: $nationalId ,Account: $accno, Balance: $baln, TransactionID: $tranId, Date: $dateString, Amount: $amt, TransactionType:$tran,Depositor:$dep, DepositorID:$depId, DepositorNumber:$depNo, Currency: $currency"
            var qrCodeBitmap = generateQRCode(combi, 512)
            var bitImage = convertImageToBase64(qrCodeBitmap)
            var doubleAmt = amt!!.toDouble()

            SharedData.imageBitmap = qrCodeBitmap
            SharedData.st = "nyophi"
            SharedData.base64 = bitImage

            var deposit = Deposit(
                0, tranId, doubleAmt, dateString, false, bitImage, 0, 0, "Deposit",
                dep!!, depId!!, depNo!!, currency!!
            )

            viewModel.postDeposit(1, deposit)

            viewModel.successMsg.observe(viewLifecycleOwner, Observer { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            })

            viewModel.errorMsg.observe(viewLifecycleOwner, Observer { error ->
                // Handle the error message, e.g., show a toast
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
            })



            findNavController().navigate(R.id.action_DepositConfirmFragment_to_DepositQrcodeFragment)



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
