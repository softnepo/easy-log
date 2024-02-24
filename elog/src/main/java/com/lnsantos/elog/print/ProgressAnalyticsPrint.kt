package com.lnsantos.elog.print

import android.util.Log
import com.lnsantos.elog.ELog
import com.lnsantos.elog.annotation.ELogExperimental
import com.lnsantos.elog.annotation.ELogInternalExperiment
import com.lnsantos.elog.print.ELogPrint.Companion.MAX_CHARACTER
import com.lnsantos.elog.print.ELogPrint.Companion.createLineWithCharacter

@OptIn(ELogInternalExperiment::class)
@ELogExperimental
internal class ProgressAnalyticsPrint : ELogPrint {

    override fun onPrint(
        tag: String,
        level: ELog.Level,
        interception: ELog.Interception?,
        progress: ELog.Progress?,
        message: String?
    ) {

        if (ELog.getShowInterceptionProgress()) {
            val content = "Class ${interception?.javaClass?.simpleName}::Progress ${progress?.name}"

            createLineWithCharacter(
                content = content,
                maxLine = MAX_CHARACTER,
                startPrefix = Char(124),
                endPrefix = Char(124)
            ).forEach { line ->
                Log.println(
                    level.getPriority(),
                    tag,
                    line
                )
            }
        }
    }
}