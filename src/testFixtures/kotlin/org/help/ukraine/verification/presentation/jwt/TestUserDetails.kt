package org.help.ukraine.verification.presentation.jwt

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class TestUserDetails(
    private val username: String,
    private val password: String,
    private val authorities: Collection<GrantedAuthority>,
    private val isAccountExpired: Boolean = false,
    private val isAccountLocked: Boolean = false,
    private val isCredentialsExpired: Boolean = false,
    private val isDisabled: Boolean = false
): UserDetails {
    override fun getAuthorities() = authorities

    override fun getPassword() = password

    override fun getUsername() = username

    override fun isAccountNonExpired() = !isAccountExpired

    override fun isAccountNonLocked() = !isAccountLocked

    override fun isCredentialsNonExpired() = !isCredentialsExpired

    override fun isEnabled() = !isDisabled
}