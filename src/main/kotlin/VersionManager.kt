class VersionManager {
    init {
        previousVersions = ArrayDeque()
    }

    constructor() {
        version = Version()
        historyLimit = 1
    }

    constructor(version: Version) {
        this.version = Version.from(version)
        historyLimit = 1
    }

    constructor(historyLimit: Int) {
        this.version = Version()
        this.historyLimit = historyLimit
    }

    constructor(version: Version, historyLimit: Int) {
        this.version = Version.from(version)
        this.historyLimit = historyLimit
    }

    private var version: Version
    private val previousVersions: ArrayDeque<Version>
    private val historyLimit: Int

    fun major(): VersionManager {
        previousVersions.pushWithinLimit(Version.from(version))
        version.incrementMajor()
        version.resetMinor()
        version.resetPatch()
        return this
    }

    fun minor(): VersionManager {
        previousVersions.pushWithinLimit(Version.from(version))
        version.incrementMinor()
        version.resetPatch()
        return this
    }

    fun patch(): VersionManager {
        previousVersions.pushWithinLimit(Version.from(version))
        version.incrementPatch()
        return this
    }

    fun rollback(): VersionManager {
        if (previousVersions.isEmpty())
            throw RuntimeException("Cannot rollback!")

        version = Version.from(previousVersions.removeFirst())
        return this
    }

    fun release(): String {
        return version.formatted()
    }

    private fun <Version> ArrayDeque<Version>.pushWithinLimit(version: Version) {
        if(this.size == historyLimit)
            this.removeFirst()
        this.addLast(version)
    }
}

