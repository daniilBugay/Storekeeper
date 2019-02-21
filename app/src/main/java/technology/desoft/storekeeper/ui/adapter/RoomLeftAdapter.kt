package technology.desoft.storekeeper.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_room_right.view.*
import technology.desoft.storekeeper.R
import technology.desoft.storekeeper.model.room.Room

class RoomLeftAdapter(
    private val rooms: List<Room>,
    private val onItemClick: (Room) -> Unit
) : RecyclerView.Adapter<RoomLeftAdapter.ViewHolder>() {

    override fun getItemCount() = rooms.size

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_room_left, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(position: Int){
            val room = rooms[position]
            itemView.roomNumberText.text = room.number
            itemView.setOnClickListener { onItemClick(room) }
        }
    }
}