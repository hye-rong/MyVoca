package com.example.myvoca.check

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


class RecyclerItemTouchHelper(dragDirs: Int, swipeDirs: Int, private val listener: RecyclerITHListener) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
    interface RecyclerITHListener {
        fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int)
    }
    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if(viewHolder!=null) {
            val foregroundView: View = (viewHolder as CheckAdapter.MyViewHolder).binding.viewForeground
            getDefaultUIUtil().onSelected(foregroundView)
        }
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val foregroundView: View = (viewHolder as CheckAdapter.MyViewHolder).binding.viewForeground
        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive)
    }


    override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val foregroundView: View = (viewHolder as CheckAdapter.MyViewHolder).binding.viewForeground
        getDefaultUIUtil().onDrawOver(c, recyclerView,
                foregroundView,
                dX, dY,
                actionState, isCurrentlyActive)
    }


    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        val foregroundView: View = (viewHolder as CheckAdapter.MyViewHolder).binding.viewForeground
        getDefaultUIUtil().clearView(foregroundView)
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onSwiped(viewHolder, direction, viewHolder.adapterPosition)
    }

}