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
class ProgressAnalyticsPrintTest {

    @OptIn(ELogExperimental::class)
    private val log = ProgressAnalyticsPrint()
    @Before
    fun setup() {
        mockkObject(ELogPrint)
        mockkStatic(Log::class)
    }

    @OptIn(ELogExperimental::class)
    @Test
    fun `given toggle getShowInterceptionProgress false when call onPrint, then must skip render`() {

        ELog.initialization(showInterceptionProgress = false)

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
    fun `given interception when call onPrint, then must create line`() {

        ELog.initialization(showInterceptionProgress = true)

        val expectedContent = "Class FakeInterception::Progress CONTINUE"

        log.onPrint(
            tag = "Test",
            level = ELog.Level.DEBUG,
            interception = FakeInterception(),
            progress = ELog.Progress.CONTINUE,
            message = null
        )

        verify {
            ELogPrint.createLineWithCharacter(
                eq(expectedContent),
                eq(ELogPrint.MAX_CHARACTER),
                eq(Char(124)),
                eq(Char(124))
            )
        }
    }

    @OptIn(ELogExperimental::class)
    inner class FakeInterception() : ELog.Interception {
        @OptIn(ELogExperimental::class)
        override fun onInterception(
            level: ELog.Level,
            message: String?,
            throwable: Throwable?
        ): ELog.Progress {
            return ELog.Progress.CONTINUE
        }
    }
}