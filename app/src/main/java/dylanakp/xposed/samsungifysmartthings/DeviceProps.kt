package dylanakp.xposed.samsungifysmartthings

object DeviceProps {

    /**
     * Class to store different feature flags for different pixels.
     * @param displayName String to show to user to customize flag selection. Example "Pixel 2020"
     * Also note that these display names are what is actually stored in shared preferences.
     * The actual feature flags are then derived from the display names.
     * @param featureFlags List of actual features spoofed to Google Photos for that particular [displayName].
     * Example, for [displayName] = "Pixel 2020", [featureFlags] = listOf("com.google.android.feature.PIXEL_2020_EXPERIENCE")
     */
    class Features(
        val displayName: String,
        val featureFlags: List<String>,
    ){
        constructor(displayName: String, vararg featureFlags: String) : this(
            displayName,
            featureFlags.toList()
        )

        constructor(displayName: String, singleFeature: String) : this(
            displayName,
            listOf(singleFeature)
        )
    }

    /**
     * List of all possible feature flags.
     * CHRONOLOGY IS IMPORTANT. Elements are arranged in accordance of device release date.
     */
    val allFeatures = listOf(

        Features("Galaxy S23+",
            "com.samsung.android.feature.SemFloatingFeature",
        ),
    )

    private fun getFeaturesUpTo(featureLevel: String): List<Features> {
        val allFeatureDisplayNames = allFeatures.map { it.displayName }
        val levelIndex = allFeatureDisplayNames.indexOf(featureLevel)
        return if (levelIndex == -1) listOf()
        else {
            allFeatures.withIndex().filter { it.index <= levelIndex }.map { it.value }
        }
    }

    /**
     * Class storing android version information to be faked.
     *
     * @param label Just a string to show the user. Not spoofed.
     * @param release Corresponds to `ro.build.version.release`. Example values: "12", "11", "8.1.0" etc.
     * @param sdk Corresponds to `ro.build.version.sdk`.
     */
    data class AndroidVersion(
        val label: String,
        val release: String,
        val sdk: Int,
    ){
        fun getAsMap() = hashMapOf(
            Pair("RELEASE", release),
            Pair("SDK_INT", sdk),
            Pair("SDK", sdk.toString()),
        )
    }

    /**
     * List of all major android versions.
     * Pixel 1 series launched with nougat, so that is the lowest version.
     */
    val allAndroidVersions = listOf(
        AndroidVersion("Oreo 8.1.0", "8.1.0", 27),
        AndroidVersion("Pie 9.0", "9", 28),
        AndroidVersion("Q 10.0", "10", 29),
        AndroidVersion("R 11.0", "11", 30),
        AndroidVersion("S 12.0", "12", 31),
        AndroidVersion("T 13.0", "13", 32),
    )

    /**
     * Get instance of [AndroidVersion] from specified [label].
     * Send null if no such label.
     */
    fun getAndroidVersionFromLabel(label: String) = allAndroidVersions.find { it.label == label }

    /**
     * Class to contain device names and their respective build properties.
     * @param deviceName Actual device names, example "Pixel 4a".
     * @param props Contains the device properties to spoof.
     * @param featureLevelName Points to the features expected to be spoofed from [allFeatures],
     * from "Pixel 2016" up to this level.
     */
    data class DeviceEntries(
        val deviceName: String,
        val props: HashMap<String, String>,
        val featureLevelName: String,
        val androidVersion: AndroidVersion?,
    )

    /**
     * List of all devices and their build props and their required feature levels.
     */
    val allDevices = listOf(

        DeviceEntries("None", hashMapOf(), "None", null),

        DeviceEntries(
            "Galaxy S8", hashMapOf(
                Pair("BRAND", "samsung"),
                Pair("MANUFACTURER", "samsung"),
                Pair("DEVICE", "dreamlte"),
                Pair("PRODUCT", "dreamlte"),
                Pair("MODEL", "SM-G950F"),
                Pair("FINGERPRINT", "samsung/dreamltexx/dreamlte:7.0/NRD90M/G950FXXU1AQL5:user/release-keys"),
                Pair("HARDWARE", "samsungexynos8895"),
                Pair("BOARD", "universal8895"),
                Pair("BOOTLOADER", "G950FXXU1AQL5"),
                Pair("RADIO", "G950FXXU1AQL5"),
                Pair("TIMA", "1"),
            ),
            "Galaxy S8",
            getAndroidVersionFromLabel("T 13.0"),
        ),
    )

    /**
     * Get instance of [DeviceEntries] from a supplied [deviceName].
     */
    fun getDeviceProps(deviceName: String?) = allDevices.find { it.deviceName == deviceName }

    /**
     * Call [getFeaturesUpTo] using a device name rather than feature level.
     * Used in spinner in main activity.
     */
    fun getFeaturesUpToFromDeviceName(deviceName: String?): Set<String>{
        return getDeviceProps(deviceName)?.let {
            getFeaturesUpTo(it.featureLevelName).map { it.displayName }.toSet()
        }?: setOf()
    }

    /**
     * Default name of device to spoof.
     */
    val defaultDeviceName = "Galaxy S8"

    /**
     * Default feature level to spoof up to. Corresponds to what is expected for the device in [defaultDeviceName].
     */
    val defaultFeatures = getFeaturesUpTo("Galaxy S8")

}