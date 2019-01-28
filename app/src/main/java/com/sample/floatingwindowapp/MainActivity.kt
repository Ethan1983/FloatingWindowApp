package com.sample.floatingwindowapp

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.button).setOnClickListener {
            launchFloatingWindow()
        }
    }

    private fun launchFloatingWindow() {

        var canDrawOverlays = true

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            canDrawOverlays = Settings.canDrawOverlays( this )

            if( !canDrawOverlays ) {
                /*
                 * ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SYSTEM_ALERT_WINDOW)
                 * never returns true even if the user has denied access in previous attempts.
                 */
                if( ActivityCompat.shouldShowRequestPermissionRationale( this,
                        Manifest.permission.SYSTEM_ALERT_WINDOW ) ) {
                    toast( "Permission had been denied, show rationale to educate user" )
                } else {
                    toast( "Requesting SYSTEM_ALERT_WINDOW permission" )
                    val intent = Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:${this.packageName}"))
                    startActivityForResult(intent,
                        OVERLAY_PERMISSION_REQ_CODE
                    )
                }
            }
        }

        if( canDrawOverlays ) {
            toast( "Permission available" )
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            OVERLAY_PERMISSION_REQ_CODE -> {
                if (Settings.canDrawOverlays(this)) {
                    toast( "Permission granted" )
                } else {
                    toast( "Permission denied" )
                }
            }
        }
    }

    private fun toast( message : CharSequence ) =
        Toast.makeText( this, message, Toast.LENGTH_SHORT ).show()

    companion object {
        private const val OVERLAY_PERMISSION_REQ_CODE = 1
    }
}
