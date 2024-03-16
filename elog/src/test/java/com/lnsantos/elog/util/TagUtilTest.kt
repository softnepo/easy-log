package com.lnsantos.elog.util

import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test

class TagUtilTest {

    @Test
    fun `when call captureTag, then must return class name`() {
        val actual = String().captureTag()
        val expected = "String"

        MatcherAssert.assertThat(actual, Matchers.equalTo(expected))
    }

    @Test
    fun `when call createTagByException with exception, then must is valid result`() {
        val expected = "TagUtilTest"
        createTagByException(
            exception = Throwable(),
            onResult = {actual ->
                MatcherAssert.assertThat(actual, Matchers.equalTo(expected))
            }
        )
    }

    @Test
    fun `when call createTagByException none exception, then must is valid result`() {
        val expected = "TagUtilTest"
        createTagByException(
            exception = null,
            onResult = { actual ->
                MatcherAssert.assertThat(actual, Matchers.equalTo(expected))
            }
        )
    }

    @Test
    fun `when call createTagByException with ignore, then must break validation`() {
        val lambda = mockk<(tagFiend: String) -> Unit>(relaxed = true)
        createTagByException(
            ignore = true,
            exception = null,
            onResult = lambda
        )

        verify(exactly = 0) { lambda.invoke(any()) }
    }
}