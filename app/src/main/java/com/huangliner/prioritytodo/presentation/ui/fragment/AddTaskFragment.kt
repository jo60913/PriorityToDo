package com.huangliner.prioritytodo.presentation.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.huangliner.prioritytodo.R
import com.huangliner.prioritytodo.application.util.CategoryUtils
import com.huangliner.prioritytodo.application.util.LocalDateUtil.Companion.toyyyyMMddString
import com.huangliner.prioritytodo.application.util.PriorityUtils
import com.huangliner.prioritytodo.application.util.StringUtil.Companion.convertDateStringToDateInt
import com.huangliner.prioritytodo.application.util.StringUtil.Companion.toLocalDate
import com.huangliner.prioritytodo.databinding.FragmentAddTaskBinding
import com.huangliner.prioritytodo.presentation.viewmodel.AddTaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.time.LocalDate

@AndroidEntryPoint
class AddTaskFragment : Fragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val addTaskViewModel: AddTaskViewModel by viewModels()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(layoutInflater)
        binding.tvAddTaskEndDate.text = LocalDate.now().toyyyyMMddString()
        binding.tvAddTaskEndDate.setOnClickListener {
            val today = binding.tvAddTaskEndDate.text.toString().convertDateStringToDateInt()
            val pickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val day = String.format("%02d", dayOfMonth)
                    binding.tvAddTaskEndDate.text = requireContext().getString(
                        R.string.common_formatted_date,
                        year,
                        month + 1,
                        day
                    )
                }, today.year, today.month, today.day
            )
            pickerDialog.show()
        }
        binding.btnAddTaskAddSubtask.setOnClickListener {
            addSubTaskInputLayout()
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_ok -> {
                if(hasNecessaryIsEmpty())
                    return false
                val subTaskList = mutableListOf<String>()
                if(hasMoreThanOneCloneSubtask()) {
                    for (i in 0 until binding.llAddTaskSubTaskLayout.childCount) {
                        val child = binding.llAddTaskSubTaskLayout.getChildAt(i)
                        val textInputLayout =
                            child.findViewById(R.id.til_add_task_clone_placeholder_title_layout) as TextInputLayout
                        val subTaskContent = textInputLayout.editText?.text.toString()
                        Timber.e("輸入匡內容 $subTaskContent")
                        if (subTaskContent.isNotEmpty())
                            subTaskList.add(subTaskContent)
                    }
                }

                val subtasks = mutableListOf(binding.tilAddTaskPlaceholderTitleLayout.editText?.text.toString())
                subtasks.addAll(subTaskList.toList())
                addTaskViewModel.addTask(
                    title = binding.tilAddTaskTitleLayout.editText?.text.toString(),
                    content = binding.tilAddTaskContentTitleLayout.editText?.text.toString(),
                    endDate = binding.tvAddTaskEndDate.text.toString().toLocalDate(),
                    priority = PriorityUtils.toPriority(binding.spAddTaskPriority.selectedItemId.toString()),
                    subTasks = subtasks,
                    category = CategoryUtils.toCategory(binding.spAddTaskCategory.selectedItemId.toString()),
                )
                Snackbar.make(binding.root,getString(R.string.add_task_add_success),Snackbar.LENGTH_LONG).show()
                findNavController().popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hasNecessaryIsEmpty() : Boolean{
        val title = binding.tilAddTaskTitleLayout.editText!!.text.toString().trim()
        val content = binding.tilAddTaskContentTitleLayout.editText!!.text.toString()
        val firstSubTask = binding.tilAddTaskPlaceholderTitleLayout.editText!!.text.toString()
        if(title.isEmpty()){
            binding.tilAddTaskTitleLayout.error = getString(R.string.add_task_input_title_hint)
            return true
        }

        if(content.isEmpty()){
            binding.tilAddTaskContentTitleLayout.error = getString(R.string.add_task_content_titile_hint)
            return true
        }

        if(firstSubTask.isEmpty()){
            binding.tilAddTaskPlaceholderTitleLayout.error = getString(R.string.add_task_subtask_input_hint)
            return true
        }

        return false
    }

    private fun addSubTaskInputLayout() {
        if(hasMoreThanOneCloneSubtask()) {
            val lastChild =
                binding.llAddTaskSubTaskLayout.getChildAt(binding.llAddTaskSubTaskLayout.childCount - 1)
            val subTaskContent = lastChild.findViewById<TextInputLayout>(R.id.til_add_task_clone_placeholder_title_layout)
            if(subTaskContent.editText?.text.toString().isEmpty())
                return
        }else{
            if(binding.tilAddTaskPlaceholderTitleLayout.editText?.text.toString().isEmpty())
                return
        }
        val inflater = LayoutInflater.from(requireContext())
        val inputLayout = inflater.inflate(R.layout.add_task_sub_task_item_layout, binding.llAddTaskSubTaskLayout, false) as ConstraintLayout

        binding.llAddTaskSubTaskLayout.addView(inputLayout)
    }

    private fun hasMoreThanOneCloneSubtask() = binding.llAddTaskSubTaskLayout.childCount != 0

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}