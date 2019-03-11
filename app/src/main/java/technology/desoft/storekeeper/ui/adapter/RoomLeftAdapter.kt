package technology.desoft.storekeeper.ui.adapter

import android.support.design.card.MaterialCardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_room_right.view.*
import technology.desoft.storekeeper.R
import technology.desoft.storekeeper.model.item.ItemType
import technology.desoft.storekeeper.model.room.Room

class RoomLeftAdapter(
    private val rooms: List<Room>,
    private val onItemClick: (Room) -> Unit
) : RecyclerView.Adapter<RoomLeftAdapter.ViewHolder>() {

    private var selectedItem: Int = 0
    override fun getItemCount() = rooms.size

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_room_left, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == selectedItem)
            holder.bindWithSelected(position)
        else
            holder.bindWithoutSelection(position)
    }

    fun setSelectedType(room: Room) {
        val lastSelectedPosition = selectedItem
        selectedItem = rooms.indexOf(room)
        notifyItemChanged(lastSelectedPosition)
        notifyItemChanged(selectedItem)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private fun bind(position: Int){
            val room = rooms[position]
            itemView.roomNumberText.text = room.number
            itemView.setOnClickListener { onItemClick(room) }
        }

        fun bindWithoutSelection(position: Int){
            bind(position)
            (itemView as MaterialCardView).cardElevation = itemView.resources.getDimension(
                R.dimen.card_view_default_elevation
            )
        }

        fun bindWithSelected(position: Int){
            bind(position)
            (itemView as MaterialCardView).cardElevation = itemView.resources.getDimension(
                R.dimen.card_view_active_elevation
            )
        }
    }
}