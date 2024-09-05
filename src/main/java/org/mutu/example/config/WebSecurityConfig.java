package org.mutu.example.config;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author Zaw Than Oo
 * @since 23-08-2024
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http.headers(headers -> headers.frameOptions(fo -> fo.disable()));
    	http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
    	// http.csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")));
        // http.csrf(csrf -> csrf.disable());
    	http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    	
    	return http.build();
    }
    
    @Value("${corsConfig.allow-origin}")
    private String allowOrigins;
    @Value("${corsConfig.allow-credentials}")
    private boolean allowCredentials;
    @Value("${corsConfig.allow-methods}")
    private String allowMethods;
    @Value("${corsConfig.allow-headers}")
    private String allowHeaders;
    @Value("${corsConfig.expose-headers}")
    private String exposeHeaders;
    
    private UrlBasedCorsConfigurationSource corsConfigurationSource() {
    	final var configuration = new CorsConfiguration();
        List<String> originsList = Stream.of(allowOrigins.split(",", -1)).collect(Collectors.toList());
        configuration.setAllowedOrigins(originsList);
        configuration.setAllowCredentials(allowCredentials);
        List<String> methodList = Stream.of(allowMethods.split(",", -1)).collect(Collectors.toList());
        configuration.setAllowedMethods(methodList);
        List<String> headerList = Stream.of(allowHeaders.split(",", -1)).collect(Collectors.toList());
        configuration.setAllowedHeaders(headerList);
        List<String> exposeHeaderList = Stream.of(exposeHeaders.split(",", -1)).collect(Collectors.toList());
        configuration.setExposedHeaders(exposeHeaderList);

        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}