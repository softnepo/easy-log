package com.lnsantos.elog

import android.os.Build
import android.util.Log
import java.util.regex.Pattern

internal const val ELogPackTAG = "ELogPack"

open class ELogPack() : ELogContract.Logger {

    private val interceptions = mutableListOf<ELog.Interception>()


    /**
     * Find this pattern
     *
     * $123
     * $12345678
     * $$12
     * $123$456
     * */
    private val anonymousClassPattern = Pattern.compile("(\\$\\d+)+$")
    internal fun setup(
        middlewares: List<ELog.Interception>
    ) {
        interceptions.addAll(middlewares)
    }

    private fun applyLog(
        level: ELog.Level,
        tag: String?,
        message: String?,
        exception: Throwable?,
    ) {

        var finalTag: String = this::class.simpleName ?: ELogPackTAG
        var messageFinal = message ?: exception?.message.toString()

        val isAndroid8OrUpper = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

        if (exception != null) {
            val tag = exception.stackTrace
                .first()
                .className
                .substringAfterLast(".")

            if ((finalTag.length + tag.length) > 23 && isAndroid8OrUpper) {
                finalTag.plus(".$tag")
            }
        }

        if (tag != null && (tag.length >= 23 && isAndroid8OrUpper || tag.length < 23)) {
            finalTag = tag
        }

        runCatching {
            Log.println(level.getPriority(), finalTag, "--------------------------------------")
            interceptions.forEachIndexed { index, interception ->
                val progress = interception.onInterception(level, messageFinal, exception)

                if (ELog.getShowInterceptionProgress()) {
                    Log.println(
                        ELog.Level.DEBUG.getPriority(),
                        finalTag,
                        "[$index] Class ${interception.javaClass.simpleName}::Progress ${progress.name}"
                    )
                    Log.println(level.getPriority(), finalTag, "--------------------------------------")
                }

                if (progress == ELog.Progress.CONTINUE) {
                    Log.println(level.getPriority(), finalTag, messageFinal)
                }
            }
        }.onSuccess {
            Log.println(level.getPriority(), finalTag, "--------------------------------------")
        }.onFailure {
            Log.println(
                ELog.Level.ERROR.getPriority(),
                this::class.simpleName,
                it.stackTraceToString()
            )
            Log.println(level.getPriority(), finalTag, "--------------------------------------")
        }
    }

    override fun d(message: String?): ELogContract.Logger = apply{
        applyLog(ELog.Level.DEBUG, null, message, null)
    }

    override fun v(message: String?): ELogContract.Logger = apply {
        applyLog(ELog.Level.VERBOSE, null, message, null)
    }

    override fun e(message: String?): ELogContract.Logger = apply {
        applyLog(ELog.Level.ERROR, null, message, null)
    }

    override fun i(message: String?): ELogContract.Logger = apply {
        applyLog(ELog.Level.INFO, null, message, null)
    }

    override fun w(message: String?): ELogContract.Logger = apply {
        applyLog(ELog.Level.WARN, null, message, null)
    }

    override fun a(message: String?): ELogContract.Logger = apply {
        applyLog(ELog.Level.ASSERT, null, message, null)
    }
}