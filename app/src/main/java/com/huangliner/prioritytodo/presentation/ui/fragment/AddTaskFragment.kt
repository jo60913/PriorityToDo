package com.huangliner.prioritytodo.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.huangliner.prioritytodo.R
import com.huangliner.prioritytodo.databinding.FragmentAddAccountBinding
import com.huangliner.prioritytodo.databinding.FragmentAddTaskBinding


class AddTaskFragment : Fragment() {
    private var _binding : FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(layoutInflater)
        return binding.root
    }

}