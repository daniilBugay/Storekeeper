package technology.desoft.storekeeper.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product_right.view.*
import technology.desoft.storekeeper.R
import technology.desoft.storekeeper.model.item.Item
import technology.desoft.storekeeper.model.item.ItemType
import technology.desoft.storekeeper.ui.view.Stepper
import kotlin.math.roundToInt

class ItemRightAdapter(
    private val itemsAndTypes: List<Pair<Item, ItemType>>,
    private val onItemStep: (Item) -> Unit
) : RecyclerView.Adapter<ItemRightAdapter.ViewHolder>() {

    override fun getItemCount() = itemsAndTypes.size

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_product_right, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(position: Int){
            val (item, type) = itemsAndTypes[position]
            with(itemView){
                Picasso.get().load(type.image).fit().into(itemTypeImage)
                typeAmountStepper.value = item.amount.roundToInt()
                typeAmountStepper.setCallback(object: Stepper.Callback{
                    override fun onStep(oldValue: Int, newValue: Int) {
                        onItemStep(item)
                        item.amount = newValue.toDouble()
                    }
                })
            }
        }
    }
}