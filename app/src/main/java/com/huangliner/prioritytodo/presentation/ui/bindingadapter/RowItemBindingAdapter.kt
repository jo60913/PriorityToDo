package com.huangliner.prioritytodo.presentation.ui.bindingadapter

import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.huangliner.prioritytodo.R
import com.huangliner.prioritytodo.application.util.Priority

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
    }
}