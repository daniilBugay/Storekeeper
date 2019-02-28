package technology.desoft.storekeeper.ui.adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product_left.view.*
import technology.desoft.storekeeper.R
import technology.desoft.storekeeper.model.item.ItemType

class ItemLeftAdapter(
    private val itemTypes: List<ItemType>,
    private val onItemClick: (ItemType) -> Unit
) : RecyclerView.Adapter<ItemLeftAdapter.ViewHolder>() {

    override fun getItemCount() = itemTypes.size

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_product_left, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(position: Int){
            val itemType = itemTypes[position]
            Picasso.get().load(Uri.parse(itemType.image)).into(itemView.itemProductImage)
            itemView.itemProductImage.setOnClickListener { onItemClick(itemType) }
        }
    }
}