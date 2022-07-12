class Version constructor(major: Int? = 0, minor: Int? = 0, patch: Int? = 0) {
    private var patch: Int
    private var minor: Int
    private var major: Int

    init {
        if(major == null && minor == null && patch == null) {
            this.major = 0
            this.minor = 0
            this.patch = 1
        }
        else {
            this.major = major ?: 0
            this.minor = minor ?: 0
            this.patch = patch ?: 0
        }
    }

    companion object {
        fun from(major: Int?, minor: Int?, patch: Int?) =
            Version(major, minor, patch)

        fun from(version: Version?) =
            if (version == null)
                Version()
            else
                Version(version.major, version.minor, version.patch)

        fun from(version: String?): Version {
            if (version.isNullOrBlank())
                return Version()

            val strParts = version
                .split("\\.".toRegex())
                .take(3)
            if (strParts.isEmpty() ||
                strParts.map { it.toIntOrNull() }.contains(null)
            )
                throw RuntimeException("Error occured while parsing version!")

            val versionParts = strParts.map { it.toInt() }
            return Version(
                versionParts.getOrNull(0),
                versionParts.getOrNull(1),
                versionParts.getOrNull(2)
            )
        }
    }

    fun incrementMajor() {
        major++
    }

    fun incrementMinor() {
        minor++
    }

    fun incrementPatch() {
        patch++
    }

    fun resetMinor() {
        minor = 0
    }

    fun resetPatch() {
        patch = 0
    }

    fun formatted(): String = "$major.$minor.$patch"
}