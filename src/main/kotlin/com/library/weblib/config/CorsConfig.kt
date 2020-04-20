package com.library.weblib.config

import java.util.Collections

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

/**
 *
 * Enable application CORS
 *
 */
@Configuration
class CorsConfig {
    @Bean
    fun corsFilterRegistrationBean(): FilterRegistrationBean<CorsFilter> {
        val config = CorsConfiguration()
        config.allowCredentials = false
        config.allowedOrigins = Collections.singletonList("*")
        config.allowedMethods = Collections.singletonList("*")
        config.allowedHeaders = Collections.singletonList("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        val bean: FilterRegistrationBean<CorsFilter> = FilterRegistrationBean<CorsFilter>()
        bean.filter = CorsFilter(source)
        bean.order = Ordered.HIGHEST_PRECEDENCE
        return bean
    }
}