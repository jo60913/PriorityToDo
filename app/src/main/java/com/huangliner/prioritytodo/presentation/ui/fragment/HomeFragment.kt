package com.huangliner.prioritytodo.presentation.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.huangliner.prioritytodo.R
import com.huangliner.prioritytodo.databinding.FragmentHomeBinding
import com.huangliner.prioritytodo.presentation.ui.adapter.HomeDueDateRecyclerViewAdapter
import com.huangliner.prioritytodo.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel : HomeViewModel by viewModels()
    private val dueDateAdapter by lazy { HomeDueDateRecyclerViewAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.fabHome.setOnClickListener {
            findNavController().navigate(R.id.addTaskFragment)
        }
        binding.rvHomeDueDateTodoItem.adapter = dueDateAdapter
        lifecycleScope.launchWhenCreated {
            homeViewModel.dueDateList.collect {
                if (it.isEmpty()){
                    binding.clHomeEmptyHintLayout.visibility = View.VISIBLE
                    binding.rvHomeDueDateTodoItem.visibility = View.INVISIBLE
                    return@collect
                }
                binding.clHomeEmptyHintLayout.visibility = View.INVISIBLE
                binding.rvHomeDueDateTodoItem.visibility = View.VISIBLE
                dueDateAdapter.submitList(it)
                dueDateAdapter.setClickListener { dueDateItem ->
                    Timber.e("點下 ${dueDateItem.title}")
                }
            }
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_fragment_menu,menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}