package com.lnsantos.elog

import android.util.Log
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

internal const val ELogPackTAG = "ELogPack"

@OptIn(InternalCoroutinesApi::class)
open class ELogPack : ELogContract.Logger {

    private val interceptions = mutableListOf<ELog.Interception>()

    internal fun setup(
        middlewares: List<ELog.Interception>
    ) {
        synchronized(this) { interceptions.addAll(middlewares) }
    }

    @Synchronized
    private fun applyLog(
        level: ELog.Level,
        tag: String?,
        message: String?,
        exception: Throwable?,
    ) {
        var finalTag: String = ELogPackTAG
        var messageFinal = message ?: exception?.message.toString()

        createTagByException(
            ignore = tag != null,
            exception = exception,
            onResult = { finalTag = it }
        )

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
                    Log.println(
                        level.getPriority(),
                        finalTag,
                        "--------------------------------------"
                    )
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

    override fun d(message: String?): ELogContract.Logger = apply {
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

    private fun createTagByException(
        ignore: Boolean = false,
        exception: Throwable?,
        onResult: (String) -> Unit
    ) {

        if (ignore) return

        exception?.stackTrace
            ?.first()
            ?.className
            ?.substringAfterLast(".")
            ?.also { onResult(it) }
    }
}