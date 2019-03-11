package technology.desoft.storekeeper.ui.adapter

import android.graphics.Color
import android.net.Uri
import android.support.design.card.MaterialCardView
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product_left.view.*
import technology.desoft.storekeeper.R
import technology.desoft.storekeeper.model.item.ItemType
import kotlin.random.Random

class ItemLeftAdapter(
    private val itemTypes: List<ItemType>,
    private val onItemClick: (ItemType) -> Unit
) : RecyclerView.Adapter<ItemLeftAdapter.ViewHolder>() {

    private var selectedItem: Int = 0

    override fun getItemCount() = itemTypes.size

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_product_left, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == selectedItem)
            holder.bindWithSelected(position)
        else
            holder.bindWithoutSelection(position)
    }

    fun setSelectedType(itemType: ItemType) {
        val lastSelectedPosition = selectedItem
        selectedItem = itemTypes.indexOf(itemType)
        notifyItemChanged(lastSelectedPosition)
        notifyItemChanged(selectedItem)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private fun bind(position: Int){
            val itemType = itemTypes[position]
            Picasso.get().load(Uri.parse(itemType.image)).into(itemView.itemProductImage)
            itemView.itemProductImage.setOnClickListener { onItemClick(itemType) }
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