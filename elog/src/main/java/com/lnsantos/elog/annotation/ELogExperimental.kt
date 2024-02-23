package com.lnsantos.elog.annotation

import kotlin.RequiresOptIn

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@RequiresOptIn(
    message = "This version is experimental, please use annotation to warning",
    level = RequiresOptIn.Level.ERROR
)
annotation class ELogExperimental()