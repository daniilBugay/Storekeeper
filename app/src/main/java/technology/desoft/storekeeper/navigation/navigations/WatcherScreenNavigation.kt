package technology.desoft.storekeeper.navigation.navigations

import technology.desoft.storekeeper.navigation.Navigation
import technology.desoft.storekeeper.presentation.view.MainView

class WatcherScreenNavigation: Navigation<MainView> {
    override fun apply(view: MainView) {
        view.showWatcherScreen()
    }
}