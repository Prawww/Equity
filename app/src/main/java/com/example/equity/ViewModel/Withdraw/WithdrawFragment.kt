package com.example.equity.ViewModel.Withdraw

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.equity.R
import com.example.equity.ViewModel.Deposit.DepositViewModel
import com.example.equity.databinding.FragmentWithdrawBinding

class WithdrawFragment : Fragment() {

    private var _binding : FragmentWithdrawBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WithdrawViewModel
    private var name: String? = null
    private var nationalId: String? = null
    private var accno: String? = null
    private var baln: String? = null
    private var selectedItem :String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWithdrawBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[WithdrawViewModel::class.java]

        val accountView = binding.account
        val balance = binding.txtBalance
        var bundle : Bundle? = null
        val button = binding.button
        val amount = binding.amount
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


        button.setOnClickListener() {

            val amt = amount.text.toString()

            val currency = selectedItem

            bundle = Bundle().apply {
                putString("name", name)
                putString("nationalId", nationalId)
                putString("account", accno)
                putString("balance", baln)
                putString("amount", amt)
                putString("currency", currency)

            }

            findNavController().navigate(R.id.action_WithdrawFragment_to_WithdrawConfirmFragment, bundle)
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