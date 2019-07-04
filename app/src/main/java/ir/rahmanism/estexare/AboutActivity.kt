package ir.rahmanism.estexare

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        var versionIfoTxt: TextView = findViewById(R.id.version_info)
        var version = BuildConfig.VERSION_NAME

        versionIfoTxt.text = version

        var closeBtn: ImageButton = findViewById(R.id.close_about)
        closeBtn.setOnClickListener {
            finish()
        }
    }

}
