package ru.kudesnik.fastforex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import ru.kudesnik.fastforex.ui.favourites.FavouriteFragment
import ru.kudesnik.fastforex.ui.main.MainFragment

const val API_KEY = "f84a43f79f-b4a00222ad-r8kma6"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        val nav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        nav.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    R.id.btn_menu_popular -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, MainFragment.newInstance())
                            .commitNow()
                        true
                    }
                    R.id.btn_menu_favourites -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, FavouriteFragment.newInstance())
                            .commitNow()
                        true
                    }
                    else -> {false}
                }
            }
        })
    }
}