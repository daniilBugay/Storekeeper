package technology.desoft.storekeeper.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.toolbar_default.*
import technology.desoft.storekeeper.App
import technology.desoft.storekeeper.R
import technology.desoft.storekeeper.model.item.Item
import technology.desoft.storekeeper.model.item.ItemType
import technology.desoft.storekeeper.model.room.Room
import technology.desoft.storekeeper.presentation.presenter.UserPresenter
import technology.desoft.storekeeper.presentation.view.UserView
import technology.desoft.storekeeper.ui.activity.MainActivity
import technology.desoft.storekeeper.ui.adapter.ItemRightAdapter
import technology.desoft.storekeeper.ui.adapter.RoomLeftAdapter
import technology.desoft.storekeeper.ui.startActivity

class UserFragment : MvpAppCompatFragment(), UserView {

    @InjectPresenter
    lateinit var userPresenter: UserPresenter

    @ProvidePresenter
    fun providePresenter(): UserPresenter {
        return with(activity?.application as App){
            UserPresenter(itemRepository, roomRepository, userProvider)
        }
    }

    private val progressIndicator
        get() = view?.findViewById<ProgressBar>(R.id.refreshProgressIndicator)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading()
        userLeftRecycler.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        userRightRecycler.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        logoutButton.setOnClickListener { userPresenter.onLogout() }
        refreshButton.setOnClickListener { userPresenter.refresh() }
    }

    override fun showRooms(rooms: List<Room>) {
        userLeftRecycler.adapter = RoomLeftAdapter(
            rooms = rooms,
            onItemClick = {userPresenter.onRoomSelected(it)}
        )
    }

    override fun showItemsWithType(itemsAndTypes: List<Pair<Item, ItemType>>) {
        userRightRecycler.adapter = ItemRightAdapter(
            itemsAndTypes = itemsAndTypes,
            onItemStep = {userPresenter.onItemValueChange(it)}
        )
        progressIndicator?.visibility = View.GONE
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        progressIndicator?.visibility = View.GONE
    }

    override fun logout() {
        activity?.startActivity<MainActivity>()
        activity?.finish()
    }

    override fun showLoading() {
        progressIndicator?.visibility = View.VISIBLE
    }
}
