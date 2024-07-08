package com.huangliner.prioritytodo.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
        binding.btnLoginLogin.setOnClickListener {
            loginViewModel.login()
        }
        lifecycleScope.launchWhenCreated {
            loginViewModel.authLogin.collect {
                Timber.e("登入 ${it}")
                val action = LoginDirections.actionLoginToHomeFragment()
                findNavController().navigate(action)
            }
            loginViewModel.loginErrorMsg.collect {

            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}