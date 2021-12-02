package com.handysparksoft.groupsgenerator.platform

import android.app.backup.BackupAgentHelper
import android.app.backup.SharedPreferencesBackupHelper
import com.handysparksoft.groupsgenerator.data.Prefs

// A key to uniquely identify the set of backup data
const val PREFS_BACKUP_KEY = "prefs"

class PrefsBackupAgent : BackupAgentHelper() {

    override fun onCreate() {
        SharedPreferencesBackupHelper(this, Prefs.PREFS_NAME).also {
            addHelper(PREFS_BACKUP_KEY, it)
        }
    }
}
