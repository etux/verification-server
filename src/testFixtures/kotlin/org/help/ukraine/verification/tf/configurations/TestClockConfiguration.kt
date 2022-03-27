package org.help.ukraine.verification.tf.configurations

import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import java.time.Clock


@TestConfiguration
class TestClockConfiguration {

    @Bean
    fun clock(): Clock = mock {
        on { instant() } doReturn Clock.systemUTC().instant()
        on { zone } doReturn Clock.systemUTC().zone
    }
}