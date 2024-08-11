package com.example.equity.ViewModel.Deposit

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.equity.R
import com.example.equity.databinding.FragmentDepositQrcodeBinding
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class DepositQrcodeFragment : Fragment() {

    private var _binding : FragmentDepositQrcodeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DepositQrcodeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDepositQrcodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DepositQrcodeViewModel::class.java]

      val imageBit = DepositConfirmFragment.SharedData.imageBitmap
        val text = DepositConfirmFragment.SharedData.st

        val image = binding.imageViewQR
        val base64 = DepositConfirmFragment.SharedData.base64
      //  binding.txt.text = base64
       // var imageBit : Bitmap? = base64ToBitmap(base64)
        image.setImageBitmap(imageBit)

        binding.end.setOnClickListener(){
            findNavController().navigate(R.id.action_DepositQrcodeFragment_to_HomeFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
fun base64ToBitmap(base64Data: String?): Bitmap? {
    return try {
        val decodedString: ByteArray = Base64.decode(base64Data, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        null
    }
}