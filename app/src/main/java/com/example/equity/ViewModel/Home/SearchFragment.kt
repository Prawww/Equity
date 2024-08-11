package com.example.equity.ViewModel.Home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.diceroller.dataClasses.Customer
import com.example.equity.R
import com.example.equity.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!
    var customer : Customer? = null
    object SharedData {
        var cust : Customer? = null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        binding.btn.setOnClickListener(){
            val account = binding.account.text.toString()
            val acc = account.toLong()

            setObservers()


            viewModel.getCustomer(34560)
            binding.main.text = customer?.nationalId
            SharedData.cust = this.customer

           // findNavController().navigate(R.id.action_SearchFragment_to_HomeFragment)
            }
    }
    fun setObservers(){
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.loadingTextView.visibility = View.VISIBLE

            } else {
                binding.progressBar.visibility = View.GONE
                binding.loadingTextView.visibility = View.GONE

            }
        })
        viewModel.successMsg.observe(viewLifecycleOwner,Observer{
            Toast.makeText(requireContext(), it,Toast.LENGTH_SHORT).show()
        })
        viewModel.errorMsg.observe(viewLifecycleOwner,Observer{
            Toast.makeText(requireContext(), it,Toast.LENGTH_SHORT).show()
        })
        viewModel.customer.observe(viewLifecycleOwner, Observer {
            customer = it
        })
    }

}