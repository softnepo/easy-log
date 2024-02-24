package com.lnsantos.elog.util

import java.lang.Exception

internal inline fun createTagByException(
    ignore: Boolean = false,
    exception: Throwable?,
    onResult: (tagFiend: String) -> Unit
) {

    if (ignore) return

    (exception ?: Exception()).stackTrace
        ?.first()
        ?.className
        ?.substringAfterLast(".")
        ?.also { onResult(it) }
}

internal fun createTagByException(
    ignore: Boolean = false,
    exception: Throwable?
): String? {

    if (ignore) return null

    return (exception ?: Exception()).stackTrace
        ?.first()
        ?.className
        ?.substringAfterLast(".")
}

internal fun Any?.captureTag(): String? {
    return this?.run { this::class.java.simpleName }
}