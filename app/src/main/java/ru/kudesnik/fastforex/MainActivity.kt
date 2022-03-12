package ru.kudesnik.fastforex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
}