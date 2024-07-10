package com.huangliner.prioritytodo.presentation.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu,menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}