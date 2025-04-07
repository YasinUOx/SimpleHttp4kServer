package com.oocode

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import com.natpryce.hamkrest.equalTo
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.hamkrest.hasBody
import org.junit.jupiter.api.Test

class SimpleHttp4KServerTest {
    @Test
    fun canAnswerMyName() {
        assertThat(app(Request(GET, "?q=What+is+your+name%3F")), equalTo(Response(OK).body("Yasin")))
    }

    @Test
    fun showsHomePageIfMissingQueryString() {
        assertThat(app(Request(GET, "/")), hasBody(containsSubstring("<html>")))
    }
}
