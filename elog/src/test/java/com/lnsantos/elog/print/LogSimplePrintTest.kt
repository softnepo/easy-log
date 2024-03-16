package com.lnsantos.elog.print

import android.util.Log
import com.lnsantos.elog.ELog
import com.lnsantos.elog.annotation.ELogExperimental
import com.lnsantos.elog.annotation.ELogInternalExperiment
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ELogInternalExperiment::class)
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, shadows = [], sdk = [24])
class LogSimplePrintTest {

    @OptIn(ELogExperimental::class)
    private val log = LogSimplePrint()
    @Before
    fun setup() {
        mockkObject(ELogPrint)
        mockkStatic(Log::class)
    }

    @OptIn(ELogExperimental::class)
    @Test
    fun `given message null when call onPrint, then must break render`() {
        log.onPrint(
            tag = "Test",
            level = ELog.Level.DEBUG,
            interception = null,
            progress = null,
            message = null
        )

        verify(exactly = 0) {
            ELogPrint.createLineWithCharacter(any(), any(), any(), any())
        }
    }

    @OptIn(ELogExperimental::class)
    @Test
    fun `given message when call onPrint, then must create line`() {
        log.onPrint(
            tag = "Test",
            level = ELog.Level.DEBUG,
            interception = null,
            progress = null,
            message = "this is testing"
        )

        verify {
            ELogPrint.createLineWithCharacter(
                eq("this is testing"),
                eq(ELogPrint.MAX_CHARACTER),
                eq(Char(124)),
                eq(Char(124))
            )
        }
    }
}