package uz.tashxis.client.business.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginTopBottomItemDecoration(private val spacing: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        rect: Rect,
        view: View,
        parent: RecyclerView,
        s: RecyclerView.State
    ) {
        parent.adapter?.let { adapter ->
            when (parent.getChildAdapterPosition(view)) {
                RecyclerView.NO_POSITION -> {
                }
                adapter.itemCount - 1 -> {
                    rect.top = spacing
                    rect.bottom = spacing
                }
                else -> {
                    rect.top = spacing
                }
            }
        }
    }
}