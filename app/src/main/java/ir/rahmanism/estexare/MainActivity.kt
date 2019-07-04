package ir.rahmanism.estexare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var titleText: TextView
    private lateinit var resultTxt: TextView
    private lateinit var sureLabelTxt: TextView
    private lateinit var sureTxt: TextView
    private lateinit var ayeLabelTxt: TextView
    private lateinit var ayeNoTxt: TextView
    private lateinit var ayeTxt: TextView
    private lateinit var commentTxt: TextView
    private lateinit var newBtn: Button
    // At the start of app, we just put placeholder texts
    private var notLoadedYet: Boolean = true
    private lateinit var navView: BottomNavigationView
    // Name and no of sure and aye
    private lateinit var smallInfoContainer: LinearLayout
    // The estexare object
    private lateinit var estexare: OneEstexare
    // To know we are in Farsi tab or English
    private var navViewCurrent: String = "fa"

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_fa -> {
                navViewCurrent = "fa"
                titleText.setText(R.string.app_name_fa)
                if (notLoadedYet) {
                    sureTxt.setText(R.string.placeholder_sure_fa)
                    ayeNoTxt.setText(R.string.placeholder_aye_no_fa)
                    ayeTxt.setText(R.string.placeholder_aye_fa)
                    commentTxt.setText(R.string.placeholder_comment_fa)
                } else {
                    filEstexareView()
                }

                sureLabelTxt.setText(R.string.sure_fa)
                ayeLabelTxt.setText(R.string.aye_fa)
                newBtn.setText(R.string.new_estexare_fa)
                smallInfoContainer.layoutDirection = View.LAYOUT_DIRECTION_RTL
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_en -> {
                navViewCurrent = "en"
                titleText.setText(R.string.app_name_en)
                if (notLoadedYet) {
                    sureTxt.setText(R.string.placeholder_sure_en)
                    ayeNoTxt.setText(R.string.placeholder_aye_no_en)
                    ayeTxt.setText(R.string.placeholder_aye_en)
                    commentTxt.setText(R.string.placeholder_comment_en)
                } else {
                    filEstexareView()
                }
                sureLabelTxt.setText(R.string.sure_en)
                ayeLabelTxt.setText(R.string.aye_en)
                newBtn.setText(R.string.new_estexare_en)
                smallInfoContainer.layoutDirection = View.LAYOUT_DIRECTION_LTR
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the ui controls from layout.
        navView = findViewById(R.id.nav_view)
        titleText = findViewById(R.id.title_text)
        resultTxt = findViewById(R.id.result)
        sureLabelTxt = findViewById(R.id.sure_label)
        sureTxt = findViewById(R.id.sure)
        ayeLabelTxt = findViewById(R.id.aye_label)
        ayeNoTxt = findViewById(R.id.aye_no)
        ayeTxt = findViewById(R.id.aye)
        commentTxt = findViewById(R.id.comment)
        newBtn = findViewById(R.id.new_estexare_btn)
        smallInfoContainer = findViewById(R.id.small_info_container)

        // Set event listeners
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        newBtn.setOnClickListener(onNewEstexareClick)
    }

    private val onNewEstexareClick = View.OnClickListener {
        var tmpEstexare = getNewEstexare()
        if (tmpEstexare != null) {
            estexare = tmpEstexare
            notLoadedYet = false
            filEstexareView()
        } else {
            Toast.makeText(
                this@MainActivity,
                "Error in getting estexare!", Toast.LENGTH_LONG
            ).show()
        }
    }

        // These info are the same for all languages!
    private fun filEstexareView() {
        sureTxt.setText(estexare.sureNo.toString() + " - " + estexare.sure)
        ayeNoTxt.setText(estexare.ayeNo.toString())
        ayeTxt.setText(estexare.aye)

        // The data for every language separately
        if (navViewCurrent == "fa") {
            resultTxt.setText(estexare.resultFa)
            commentTxt.setText(estexare.commentFa)
        } else if (navViewCurrent == "en") {
            resultTxt.setText(estexare.resultEn)
            commentTxt.setText(estexare.commentEn)
        }
    }

    private fun getNewEstexare(): OneEstexare? {
        //// THE MAGIC happens here

        val randomAyeNo = Random.nextInt(1, 182)

        val query =
            "SELECT sure, aye, sure_no, aye_no, fi_result, fi_comment," +
                    "fa_result, fa_comment, en_result, en_comment " +
                    "FROM estexare WHERE rowid = $randomAyeNo"

        val adb = AssetDatabaseOpenHelper(this)
        val db = adb.openDatabase()
        val estexareRecord = db.rawQuery(query, null)

        var estexare = OneEstexare()
        var hasRecord = estexareRecord.count > 0
        if (hasRecord) {
            estexareRecord.moveToFirst()

            val sure = estexareRecord.getString(0)
            val aye = estexareRecord.getString(1)
            val sureNo = estexareRecord.getInt(2)
            val ayeNo = estexareRecord.getInt(3)
            val fiResult = estexareRecord.getString(4)
            val fiComment = estexareRecord.getString(5)
            val faResult = estexareRecord.getString(6)
            val faComment = estexareRecord.getString(7)
            val enResult = estexareRecord.getString(8)
            val enComment = estexareRecord.getString(9)

            estexare.sure = sure
            estexare.aye = aye
            estexare.sureNo = sureNo
            estexare.ayeNo = ayeNo
            estexare.resultFi = fiResult
            estexare.commentFi = fiComment
            estexare.resultFa = faResult
            estexare.commentFa = faComment
            estexare.resultEn = enResult
            estexare.commentEn = enComment

            estexareRecord.close()
        } else {
            Log.d("Error getting new one", "No record is retrieved!")

        }

        db.close()
        return if (hasRecord) estexare else null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        var inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_app_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.about_menu_item) {
            val intent = Intent(this@MainActivity, AboutActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
}
