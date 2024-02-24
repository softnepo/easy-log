package com.lnsantos.elog.print

import com.lnsantos.elog.ELog
import com.lnsantos.elog.annotation.ELogExperimental
import com.lnsantos.elog.annotation.ELogInternalExperiment

@ELogInternalExperiment
@OptIn(ELogExperimental::class)
internal interface ELogPrint {

    fun onPrint(
        tag: String,
        level: ELog.Level,
        interception: ELog.Interception? = null,
        progress: ELog.Progress? = null,
        message: String? = null
    )

    companion object {
        internal const val MAX_CHARACTER = 120

        internal fun createLine(
            maxChar: Int,
            prefixInside: String = "|",
            prefixMargin: String = "_"
        ): String {
            val line = StringBuilder()

            for (i in 0..maxChar) {
                if (i == 0 || i == maxChar) {
                    line.append(prefixInside)
                } else {
                    line.append(prefixMargin)
                }
            }
            return line.toString()
        }

        internal fun createLineWithCharacter(
            content: String,
            maxLine: Int,
            startPrefix: Char,
            endPrefix: Char
        ): ArrayList<String> {
            val firstLine = createLine(maxChar = maxLine, prefixInside = "-", prefixMargin = "-")
            val listLine: ArrayList<String> = arrayListOf(firstLine)
            val tempLine = StringBuilder()
            val lengthContent = content.length

            var maxLineCurrent = maxLine

            for (index in 1..lengthContent) {
                val charInString = content[index - 1].toString()

                when {
                    index == 1 || tempLine.isEmpty() -> {
                        tempLine
                            .append(startPrefix)
                            .append(charInString)
                    }

                    index == (maxLineCurrent - 1) || tempLine.length == (maxLineCurrent - 1) -> {
                        tempLine
                            .append(charInString)
                            .append(endPrefix)
                    }

                    index == lengthContent && index < maxLineCurrent -> {
                        val diff = maxLineCurrent - lengthContent
                        tempLine.append(charInString)
                        for (i in 1..diff) {
                            if (i == diff) {
                                tempLine.append(endPrefix)
                            } else {
                                tempLine.append(" ")
                            }
                        }

                        listLine.add(tempLine.toString())
                        tempLine.clear()
                    }

                    index == maxLineCurrent || tempLine.length == maxLine -> {
                        listLine.add(tempLine.toString())
                        tempLine.clear()
                        maxLineCurrent += maxLine
                    }

                    else -> tempLine.append(charInString)
                }
            }
            listLine.add(createLine(maxLine))
            return listLine
        }
    }
}