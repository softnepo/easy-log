package com.lnsantos.elog.annotation

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@RequiresOptIn(
    message = "This version is testing for developer",
    level = RequiresOptIn.Level.ERROR
)
internal annotation class ELogInternalExperiment()
