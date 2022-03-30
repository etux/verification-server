package org.help.ukraine.verification.business.configuration.settings

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("services.session")
@ConstructorBinding
data class SessionServiceSettings(val inputDetails: String)
