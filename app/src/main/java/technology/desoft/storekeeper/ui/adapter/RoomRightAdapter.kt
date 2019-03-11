package technology.desoft.storekeeper.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_room_right.view.*
import technology.desoft.storekeeper.R
import technology.desoft.storekeeper.model.item.Item
import technology.desoft.storekeeper.model.room.Room
import technology.desoft.storekeeper.ui.view.Stepper
import kotlin.math.roundToInt

class RoomRightAdapter(
    private val roomsAndItems: List<Pair<Room, Item>>,
    private val onItemStep: (Item) -> Unit
) : RecyclerView.Adapter<RoomRightAdapter.ViewHolder>() {

    override fun getItemCount() = roomsAndItems.size

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_room_right, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(position: Int){
            val (room, item) = roomsAndItems[position]
            with(itemView){
                roomNumberText.text = room.number
                roomAmountStepper.value = item.amount.roundToInt()
                roomAmountStepper.setCallback(object: Stepper.Callback{
                    override fun onStep(oldValue: Int, newValue: Int) {
                        onItemStep(item)
                        item.amount = newValue.toDouble()
                    }
                })
            }
        }
    }
}