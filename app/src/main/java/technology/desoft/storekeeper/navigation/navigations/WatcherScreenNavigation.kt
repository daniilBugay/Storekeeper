package technology.desoft.storekeeper.navigation.navigations

import technology.desoft.storekeeper.navigation.Navigation
import technology.desoft.storekeeper.presentation.view.StartupView

class WatcherScreenNavigation: Navigation<StartupView> {
    override fun apply(view: StartupView) {
        view.showWatcherScreen()
    }
}