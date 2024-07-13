package com.huangliner.prioritytodo.presentation.ui.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.huangliner.prioritytodo.R
import com.huangliner.prioritytodo.application.util.Category
import com.huangliner.prioritytodo.application.util.CategoryUtils
import com.huangliner.prioritytodo.application.util.LocalDateTimeUtil.Companion.toHHmmss
import com.huangliner.prioritytodo.application.util.LocalDateTimeUtil.Companion.toyyyyMMdd
import com.huangliner.prioritytodo.application.util.LocalTimeUtil.Companion.toHHmmss
import com.huangliner.prioritytodo.application.util.Priority
import com.huangliner.prioritytodo.application.util.PriorityUtils
import com.huangliner.prioritytodo.application.util.StringUtil.Companion.convertDateStringToDateInt
import com.huangliner.prioritytodo.application.util.StringUtil.Companion.toLocalDateTime
import com.huangliner.prioritytodo.application.util.StringUtil.Companion.toLocalTime
import com.huangliner.prioritytodo.data.database.entiry.TodoItem
import com.huangliner.prioritytodo.databinding.FragmentUpdateBinding
import com.huangliner.prioritytodo.presentation.viewmodel.UpdateViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.time.LocalDateTime
import java.time.LocalTime

@AndroidEntryPoint
class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<UpdateFragmentArgs>()
    private val updateViewModel: UpdateViewModel by viewModels()
    private var taskNo: Long = 0
    private lateinit var originSubTask: List<TodoItem>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(layoutInflater)
        taskNo = args.taskNo
        updateViewModel.getTaskDetail(taskNo.toString())
        updateViewModel.taskList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty())
                this.originSubTask = it.filter { item -> item.parentNo != "0" }
            it.forEach { todoItem ->
                if (todoItem.parentNo == "0") {   //主要的
                    binding.task = todoItem
                    binding.tvUpdateTaskEndDate.text = todoItem.dueDate.toyyyyMMdd()
                    binding.tvUpdateTaskEndTime.text = todoItem.dueDate.toHHmmss()
                    binding.spUpdateTaskCategory.setSelection(
                        CategoryUtils.fromCategory(todoItem.category).toInt()
                    )
                    binding.spUpdateTaskPriority.setSelection(
                        PriorityUtils.fromPriority(todoItem.priority).toInt()
                    )
                } else {      //子任務
                    addSubTaskInputLayout(todoItem)
                }
            }
        }
        Timber.e("取得到的編號 ${taskNo}")

        binding.tvUpdateTaskEndDate.setOnClickListener {
            val today = binding.tvUpdateTaskEndDate.text.toString().convertDateStringToDateInt()
            val pickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val day = String.format("%02d", dayOfMonth)
                    binding.tvUpdateTaskEndDate.text = requireContext().getString(
                        R.string.common_formatted_date,
                        year,
                        month + 1,
                        day
                    )
                }, today.year, today.month, today.day
            )
            pickerDialog.show()
        }

        binding.tvUpdateTaskEndTime.setOnClickListener {
            val localTime = binding.tvUpdateTaskEndTime.text.toString().toLocalTime()

            TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    val newTime = LocalTime.of(hourOfDay, minute)
                    binding.tvUpdateTaskEndTime.text = newTime.toHHmmss()
                },
                localTime.hour,
                localTime.minute,
                true // 使用24小時制
            ).show()
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_ok -> {
                if (hasNecessaryIsEmpty())
                    return false

                updateViewModel.deleteTasks(this.originSubTask)
                val subTaskList = mutableListOf<TodoItem>()
                if (hasMoreThanOneCloneSubtask()) {
                    for (i in 0 until binding.llUpdateTaskSubTaskLayout.childCount) {
                        val child = binding.llUpdateTaskSubTaskLayout.getChildAt(i)
                        val textInputLayout =
                            child.findViewById(R.id.til_update_clone_task_clone_placeholder_title_layout) as TextInputLayout
                        val checkbox =
                            child.findViewById(R.id.cb_update_clone_task) as AppCompatCheckBox
                        val subTaskContent = textInputLayout.editText?.text.toString()
                        Timber.e("輸入匡內容 $subTaskContent 是否勾選${checkbox.isSelected}")
                        if (subTaskContent.isNotEmpty()) {
                            subTaskList.add(
                                TodoItem(
                                    parentNo = binding.task!!.no.toString(),
                                    isDown = checkbox.isChecked,
                                    title = "",
                                    content = subTaskContent,
                                    priority = PriorityUtils.toPriority(binding.spUpdateTaskPriority.selectedItemId.toString()),
                                    category = CategoryUtils.toCategory(binding.spUpdateTaskCategory.selectedItemId.toString()),
                                    dueDate = LocalDateTime.now(),
                                    createDate = LocalDateTime.now()
                                )
                            )
                        }
                    }
                }

                val mainIsDoneCount = subTaskList.filter { it.isDown }.size

                val mainTask = TodoItem(
                    no = binding.task!!.no,
                    parentNo = binding.task!!.parentNo,
                    isDown = (mainIsDoneCount == subTaskList.size),
                    title = binding.tilUpdateTaskTitleLayout.editText?.text.toString(),
                    content = binding.tilUpdateTaskContentTitleLayout.editText?.text.toString(),
                    priority = PriorityUtils.toPriority(binding.spUpdateTaskPriority.selectedItemId.toString()),
                    category = CategoryUtils.toCategory(binding.spUpdateTaskCategory.selectedItemId.toString()),
                    dueDate = "${binding.tvUpdateTaskEndDate.text.toString()} ${binding.tvUpdateTaskEndTime.text.toString()}".toLocalDateTime(),
                    createDate = LocalDateTime.now()
                )
                subTaskList.add(mainTask)
                updateViewModel.updateTask(subTasks = subTaskList,)
                Snackbar.make(
                    binding.root,
                    getString(R.string.update_task_modife_success),
                    Snackbar.LENGTH_LONG
                ).show()
                findNavController().popBackStack()
            }

            R.id.menu_delete->{
                val tasks = mutableListOf<TodoItem>()
                val mainTask = TodoItem(
                    no = binding.task!!.no,
                    parentNo = binding.task!!.parentNo,
                    isDown = false,
                    title = binding.tilUpdateTaskTitleLayout.editText?.text.toString(),
                    content = binding.tilUpdateTaskContentTitleLayout.editText?.text.toString(),
                    priority = PriorityUtils.toPriority(binding.spUpdateTaskPriority.selectedItemId.toString()),
                    category = CategoryUtils.toCategory(binding.spUpdateTaskCategory.selectedItemId.toString()),
                    dueDate = "${binding.tvUpdateTaskEndDate.text.toString()} ${binding.tvUpdateTaskEndTime.text.toString()}".toLocalDateTime(),
                    createDate = LocalDateTime.now()
                )
                tasks.addAll(this.originSubTask)
                tasks.add(mainTask)
                updateViewModel.deleteTasks(tasks)
                val deleteHint = Snackbar.make(
                    binding.root,
                    getString(R.string.update_task_delete_success),
                    Snackbar.LENGTH_LONG
                )
                deleteHint.setAction(getString(R.string.update_task_recover_hint)) {
                    Timber.e("復原刪除")

                    val newTask = tasks.map { item->
                        item.copy(no = 0)
                    }

                    updateViewModel.updateTask(newTask)
                }
                deleteHint.show()
                findNavController().popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hasMoreThanOneCloneSubtask() = binding.llUpdateTaskSubTaskLayout.childCount != 0

    private fun hasNecessaryIsEmpty(): Boolean {
        val title = binding.tilUpdateTaskTitleLayout.editText!!.text.toString().trim()
        val content = binding.tilUpdateTaskContentTitleLayout.editText!!.text.toString()

        if (title.isEmpty()) {
            binding.tilUpdateTaskTitleLayout.error = getString(R.string.add_task_input_title_hint)
            return true
        }

        if (content.isEmpty()) {
            binding.tilUpdateTaskContentTitleLayout.error =
                getString(R.string.add_task_content_titile_hint)
            return true
        }

        return false
    }

    private fun addSubTaskInputLayout(todoItem: TodoItem) {
        val inflater = LayoutInflater.from(requireContext())
        val inputLayout = inflater.inflate(
            R.layout.update_task_sub_task_item_layout,
            binding.llUpdateTaskSubTaskLayout,
            false
        ) as ConstraintLayout
        if (todoItem.isDown) {
            val checkBox = inputLayout.findViewById<AppCompatCheckBox>(R.id.cb_update_clone_task)
            checkBox.isChecked = true
        }
        val textInputLayout =
            inputLayout.findViewById<TextInputLayout>(R.id.til_update_clone_task_clone_placeholder_title_layout)
        textInputLayout.editText!!.setText(todoItem.content)
        binding.llUpdateTaskSubTaskLayout.addView(inputLayout)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}