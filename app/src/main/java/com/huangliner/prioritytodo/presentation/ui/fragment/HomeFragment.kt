package com.huangliner.prioritytodo.presentation.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.huangliner.prioritytodo.R
import com.huangliner.prioritytodo.application.util.NetworkResult
import com.huangliner.prioritytodo.databinding.FragmentHomeBinding
import com.huangliner.prioritytodo.presentation.ui.adapter.HomeDueDateRecyclerViewAdapter
import com.huangliner.prioritytodo.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
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
        homeViewModel.todoItem.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Error -> {
                }
                is NetworkResult.Loading -> {
                    binding.pbHomeLoading.visibility = View.VISIBLE
                    binding.rvHomeDueDateTodoItem.visibility = View.INVISIBLE
                }
                is NetworkResult.Success -> {
                    if (it.data.isNullOrEmpty()){
                        binding.clHomeEmptyHintLayout.visibility = View.VISIBLE
                        binding.rvHomeDueDateTodoItem.visibility = View.INVISIBLE
                        return@observe
                    }
                    binding.clHomeEmptyHintLayout.visibility = View.INVISIBLE
                    binding.rvHomeDueDateTodoItem.visibility = View.VISIBLE
                    dueDateAdapter.submitList(it.data)
                    dueDateAdapter.setClickListener { dueDateItem ->
                        Timber.e("點下 ${dueDateItem.title}")
                    }
                }
            }

        }

        homeViewModel.todoItem.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Error -> {
                    binding.rvHomeDueDateTodoItem.visibility = View.VISIBLE
                    binding.pbHomeLoading.visibility = View.INVISIBLE
                }
                is NetworkResult.Loading -> {
                    binding.rvHomeDueDateTodoItem.visibility = View.INVISIBLE
                    binding.pbHomeLoading.visibility = View.VISIBLE
                }
                is NetworkResult.Success -> {
                    binding.rvHomeDueDateTodoItem.visibility = View.VISIBLE
                    dueDateAdapter.submitList(it.data!!)
                    binding.pbHomeLoading.visibility = View.INVISIBLE
                }
            }
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_fragment_menu,menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    homeViewModel.searchTodo(it)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        val searchCloseButtonId =
            searchView!!.findViewById<View>(androidx.appcompat.R.id.search_close_btn).id
        val closeButton = searchView.findViewById<ImageView>(searchCloseButtonId)
        closeButton.setOnClickListener {
            Timber.e("搜尋匡 關閉")
            homeViewModel.getDueDateTodo()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}