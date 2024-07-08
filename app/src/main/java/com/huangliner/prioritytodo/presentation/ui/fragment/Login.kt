package com.huangliner.prioritytodo.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.huangliner.prioritytodo.application.util.NetworkResult
import com.huangliner.prioritytodo.databinding.FragmentLoginBinding
import com.huangliner.prioritytodo.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class Login : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel : LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.loginViewModel = loginViewModel
        binding.btnLoginLogin.setOnClickListener {
            loginViewModel.login()
        }

        loginViewModel.loginState.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Error -> {
                    Timber.e("登入 失敗")
                    Snackbar.make(binding.root,"登入錯誤${it.message}",Snackbar.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    Timber.e("登入 載入中")
                    Snackbar.make(binding.root,"登入中",Snackbar.LENGTH_SHORT).show()
                }
                is NetworkResult.Success -> {
                    Snackbar.make(binding.root,"登入成功",Snackbar.LENGTH_SHORT).show()
                    Timber.e("登入 ${it}")
                    val action = LoginDirections.actionLoginToHomeFragment()
                    findNavController().navigate(action)
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