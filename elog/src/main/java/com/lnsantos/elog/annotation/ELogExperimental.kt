package com.lnsantos.elog.annotation

import kotlin.RequiresOptIn
import kotlin.coroutines.RestrictsSuspension

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.CONSTRUCTOR)
@RequiresOptIn(
    message = "This version is experimental, please use annotation to warning",
    level = RequiresOptIn.Level.ERROR
)
annotation class ELogExperimental()
