package com.example.equity.ViewModel.Withdraw

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.equity.R
import com.example.equity.ViewModel.Deposit.DepositConfirmFragment
import com.example.equity.ViewModel.Deposit.DepositQrcodeViewModel
import com.example.equity.databinding.FragmentDepositQrcodeBinding
import com.example.equity.databinding.FragmentWithdrawQrcodeBinding

class WithdrawQrcodeFragment : Fragment() {

    private var _binding : FragmentWithdrawQrcodeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WithdrawQrcodeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWithdrawQrcodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[WithdrawQrcodeViewModel::class.java]

        val imageBitmap = WithdrawConfirmFragment.SharedData.imageBitmap
        val text = WithdrawConfirmFragment.SharedData.st
       // binding.txt.text = text
        val image = binding.imageViewQR
        image.setImageBitmap(imageBitmap)

        binding.end.setOnClickListener(){
            findNavController().navigate(R.id.action_WithdrawQrcodeFragment_to_HomeFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}