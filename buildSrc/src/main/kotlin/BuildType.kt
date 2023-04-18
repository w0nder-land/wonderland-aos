interface BuildType {

    companion object {
        const val DEBUG = "debug"
        const val RELEASE = "release"
    }

    val appName: String
    val dimensionsName: String
    val suffixName: String
    val versionCode: Int
    val versionName: String
    val minifyEnabled: Boolean
}

object BuildTypeDebug : BuildType {
    override val appName = "원더랜드(DEV)"
    override val dimensionsName = "dev"
    override val suffixName = ".dev"
    override val versionCode = 1
    override val versionName = "1.0.0"
    override val minifyEnabled = false
}

object BuildTypeRelease : BuildType {
    override val appName = "원더랜드"
    override val dimensionsName = "prod"
    override val suffixName = ""
    override val versionCode = 1
    override val versionName = "1.0.0"
    override val minifyEnabled = true
}
