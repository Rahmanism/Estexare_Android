package ir.rahmanism.estexare

import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textMessage: TextView
    private lateinit var resultTxt: TextView
    private lateinit var sureLabelTxt: TextView
    private lateinit var sureTxt: TextView
    private lateinit var ayeLabelTxt: TextView
    private lateinit var ayeNoTxt: TextView
    private lateinit var ayeTxt: TextView
    private lateinit var commentTxt: TextView
    private lateinit var newBtn: Button
    private var notLoadedYet: Boolean = true

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_fa -> {
                textMessage.setText(R.string.title_home)
                if (notLoadedYet) {
                    sureLabelTxt.setText(R.string.sure_fa)
                    sureTxt.setText(R.string.placeholder_sure_fa)
                    ayeLabelTxt.setText(R.string.aye_fa)
                    ayeNoTxt.setText(R.string.placeholder_aye_no_fa)
                    ayeTxt.setText(R.string.placeholder_aye_fa)
                    commentTxt.setText(R.string.placeholder_comment_fa)
                    newBtn.setText(R.string.new_estexare_fa)
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_en -> {
                textMessage.setText(R.string.title_home)
                if (notLoadedYet) {
                    sureLabelTxt.setText(R.string.sure_en)
                    sureTxt.setText(R.string.placeholder_sure_en)
                    ayeLabelTxt.setText(R.string.aye_en)
                    ayeNoTxt.setText(R.string.placeholder_aye_no_en)
                    ayeTxt.setText(R.string.placeholder_aye_en)
                    commentTxt.setText(R.string.placeholder_comment_en)
                    newBtn.setText(R.string.new_estexare_en)
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        textMessage = findViewById(R.id.title_text)
        resultTxt = findViewById(R.id.result)
        sureLabelTxt = findViewById(R.id.sure_label)
        sureTxt = findViewById(R.id.sure)
        ayeLabelTxt = findViewById(R.id.aye_label)
        ayeNoTxt = findViewById(R.id.aye_no)
        ayeTxt = findViewById(R.id.aye)
        commentTxt = findViewById(R.id.comment)
        newBtn = findViewById(R.id.new_estexare_btn)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
