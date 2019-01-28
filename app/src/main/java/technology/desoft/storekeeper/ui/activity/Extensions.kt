package technology.desoft.storekeeper.ui.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import technology.desoft.storekeeper.R

inline fun AppCompatActivity.changeFragment(fragment: Fragment, body: FragmentTransaction.() -> Unit){
    supportFragmentManager.beginTransaction()
        .replace(R.id.fragmentContainer, fragment)
        .apply(body)
        .commit()
}