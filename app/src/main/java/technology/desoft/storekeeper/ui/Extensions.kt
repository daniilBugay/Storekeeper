package technology.desoft.storekeeper.ui

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.transition.TransitionInflater
import android.transition.TransitionSet
import technology.desoft.storekeeper.R

inline fun AppCompatActivity.changeFragment(fragment: Fragment, body: FragmentTransaction.() -> Unit){
    supportFragmentManager.beginTransaction()
        .replace(R.id.fragmentContainer, fragment)
        .apply(body)
        .commit()
}

inline fun AppCompatActivity.changeFragmentWithTransition(fragment: Fragment, body: FragmentTransaction.() -> Unit) {
    changeFragment(fragment) {
        val transitionSet = TransitionSet()
        transitionSet.duration = 350L
        transitionSet.addTransition(
            TransitionInflater
                .from(this@changeFragmentWithTransition)
                .inflateTransition(android.R.transition.move)
        )
        fragment.sharedElementEnterTransition = transitionSet
        body()
    }
}

inline fun <reified T: Activity>Context.startActivity(){
    startActivity(Intent(this, T::class.java))
}