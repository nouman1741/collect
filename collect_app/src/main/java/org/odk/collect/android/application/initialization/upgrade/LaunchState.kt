package org.odk.collect.android.application.initialization.upgrade

import org.odk.collect.shared.Settings

interface LaunchState {
    fun isUpgradedFirstLaunch(): Boolean
    fun appLaunched()
}

class VersionCodeLaunchState(
    private val key: String,
    private val settings: Settings,
    private val currentVersion: Int,
    private val installDetector: InstallDetector? = null
) : LaunchState {

    override fun isUpgradedFirstLaunch(): Boolean {
        return if (settings.contains(key)) {
            settings.getInt(key) < currentVersion
        } else {
            return installDetector?.installDetected() ?: false
        }
    }

    override fun appLaunched() {
        settings.save(key, currentVersion)
    }
}

interface InstallDetector {
    fun installDetected(): Boolean
}
