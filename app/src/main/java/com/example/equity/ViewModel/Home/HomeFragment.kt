package com.example.equity.ViewModel.Home

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.example.equity.R
import androidx.navigation.fragment.findNavController
import com.example.equity.databinding.FragmentHomeBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
            //ViewModelProvider(this).get(HomeViewModel::class.java)
        var cust = SearchFragment.SharedData.cust

        val depositBtn = binding.btnCashDep
        val withdrawBtn = binding.btnCashW
        val fullName = binding.fullName
        val nationalId = binding.nationalId
        val email = binding.email
        val phone = binding.phone
        val currentDate = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss")
        binding.time.text = currentDate.format(formatter)
//        nationalId.text = cust?.nationalId
//        fullName.text = cust?.firstName
        viewModel.customer.observe(viewLifecycleOwner, Observer {
               fullName.text = "${it?.firstName} ${it?.lastName}"
               nationalId.text = "${it?.nationalId}"
                email.text = "${it?.email}"
                phone.text = "${it?.phoneNumber}"



        })

        depositBtn.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_DepositFragment)
        }

        withdrawBtn.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_WithdrawFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
