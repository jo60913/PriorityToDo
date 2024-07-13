package com.huangliner.prioritytodo.presentation.ui.bindingadapter

import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.huangliner.prioritytodo.R
import com.huangliner.prioritytodo.application.util.Priority
import java.time.Duration
import java.time.LocalDateTime

class RowItemBindingAdapter {
    companion object{
        @BindingAdapter("app:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(cardView: CardView, priority: Priority){
            when(priority){
                Priority.High -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.priority_high)) }
                Priority.Medium -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.priority_medium)) }
                Priority.Low -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.priority_low)) }
            }
        }

        @BindingAdapter("app:remainTime")
        @JvmStatic
        fun parsePriorityColor(textview: TextView, dueDate: LocalDateTime){
            val remainTime = Duration.between(LocalDateTime.now(),dueDate)
            textview.text = String.format(textview.context.getString(R.string.remain_time_format),textview.context.getString(R.string.row_item_remain_time),remainTime.toHours(),remainTime.toMinutes()%60)
        }
    }
}