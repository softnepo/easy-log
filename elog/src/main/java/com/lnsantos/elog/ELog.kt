package com.lnsantos.elog

import com.lnsantos.elog.annotation.ELogExperimental
import com.lnsantos.elog.core.ELogContract

@ELogExperimental
class ELog private constructor() : ELogContract() {

    init {
        throw RuntimeException("this is singleton, please not try make this")
    }

    enum class Level {
        VERBOSE,
        DEBUG,
        INFO,
        WARN,
        ERROR,
        ASSERT;

        fun getPriority() = ordinal + 0b10;
    }

    enum class Progress {
        CONTINUE,
        SKIP
    }

    interface Interception {
        fun onInterception(
            level: Level,
            message: String?,
            throwable: Throwable?
        ) : Progress
    }

    companion object Simple : ELogPack() {

        private val interceptions = mutableListOf<Interception>()
        private var _showInterceptionProgress: Boolean = false

        fun initialization(
            showInterceptionProgress: Boolean = false
        ) {
            _showInterceptionProgress = showInterceptionProgress
            setup(interceptions)
        }

        internal fun getShowInterceptionProgress() = _showInterceptionProgress

        fun registerInterception(
            vararg interception: Interception
        ) : Simple = apply {
            interceptions.addAll(interception)
        }

        fun removeInterception(
            interception: Interception
        ) : Simple = apply {
            interceptions.remove(interception)
        }
    }
}