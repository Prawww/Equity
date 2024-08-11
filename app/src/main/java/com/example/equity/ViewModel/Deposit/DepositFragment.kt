package com.example.equity.ViewModel.Deposit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.equity.R
import com.example.equity.ViewModel.Home.HomeViewModel
import com.example.equity.databinding.FragmentDepositBinding

class DepositFragment : Fragment() {

    private lateinit var viewModel: DepositViewModel
    private var _binding : FragmentDepositBinding? = null
    private val binding get() = _binding!!
    var selectedItem :String? = null

    private var name: String? = null
    private var nationalId: String? = null
    private var accno: String? = null
    private var baln: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDepositBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DepositViewModel::class.java]

        val accountView = binding.account
        val balance = binding.txtBalance
        var bundle : Bundle? = null
        val button = binding.btn
        val amount = binding.amount
        val depositor = binding.depositor
        val depositorId = binding.depositorId
        val depositorNum = binding.depositorNum
        val spinner = binding.spinner
        val items = listOf("KES","USD")

        useSpinner(items, spinner)

        viewModel.customer.observe(viewLifecycleOwner, Observer{
//            account.text = it.accno.toString()
//            balance.text = it.balance.toString()
            name = "${it.firstName} ${it.lastName}"
            nationalId = "${it.nationalId}"
            for(account in it.customerAccount){
                accno = account.accno.toString()
                baln = account.balance.toString()
                accountView.text = account.accno.toString()
                balance.text = account.balance.toString()
            }

        })


        button.setOnClickListener {

            val amt = amount.text.toString()
            val dep = depositor.text.toString()
            val depId = depositorId.text.toString()
            val depNo = depositorNum.text.toString()
            val currency = selectedItem

            bundle = Bundle().apply {
                putString("name", name)
                putString("nationalId", nationalId)
                putString("account", accno)
                putString("balance", baln)
                putString("amount", amt)
                putString("currency", currency)
                putString("depositor", dep)
                putString("depositorId", depId)
                putString("depositorNo", depNo)
            }

            findNavController().navigate(R.id.action_DepositFragment_to_DepositConfirmFragment, bundle)
        }
    }
    private fun useSpinner(items: List<String>, spinner: Spinner){
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedItem = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle the case when no item is selected
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
