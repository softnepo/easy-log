package com.lnsantos.elog.print

import android.util.Log
import com.lnsantos.elog.ELog
import com.lnsantos.elog.annotation.ELogExperimental
import com.lnsantos.elog.annotation.ELogInternalExperiment

@OptIn(ELogInternalExperiment::class)
@ELogExperimental
internal class LogSimplePrint : ELogPrint {

    override fun onPrint(
        tag: String,
        level: ELog.Level,
        interception: ELog.Interception?,
        progress: ELog.Progress?,
        message: String?
    ) {
        if (message == null) return

        ELogPrint.createLineWithCharacter(
            content = message,
            maxLine = ELogPrint.MAX_CHARACTER,
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