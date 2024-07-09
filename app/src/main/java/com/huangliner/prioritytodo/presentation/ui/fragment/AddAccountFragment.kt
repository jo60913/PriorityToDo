package com.huangliner.prioritytodo.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.huangliner.prioritytodo.application.util.NetworkResult
import com.huangliner.prioritytodo.databinding.FragmentAddAccountBinding
import com.huangliner.prioritytodo.presentation.viewmodel.AddAccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AddAccountFragment : Fragment() {

    private var _binding : FragmentAddAccountBinding? = null
    private val binding get() = _binding!!
    private val addAccountViewModel : AddAccountViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAccountBinding.inflate(layoutInflater)
        binding.btnAddAccountConfirm.setOnClickListener {
            addAccountViewModel.addAccount()
        }
        binding.lifecycleOwner = this
        binding.addAccountViewModel = addAccountViewModel

        addAccountViewModel.addAccountState.observe(viewLifecycleOwner) {
            when(it){
                is NetworkResult.Error -> {
                    Timber.e("新增帳號 失敗")
                    Snackbar.make(binding.root,"${it.message}",Snackbar.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {
                    Timber.e("新增帳號 成功")
                    Snackbar.make(binding.root,"註冊成功，請回來登入",Snackbar.LENGTH_LONG).show()
                    findNavController().popBackStack()
                }
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}