package com.app.auth_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 1. Четко разрешаем адреса вашего фронтенда через паттерны
        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:3000",
                "http://localhost:5173"
        ));

        // 2. Разрешаем абсолютно любые HTTP-методы (GET, POST, PATCH, OPTIONS и др.)
        configuration.setAllowedMethods(List.of("*"));

        // 3. Разрешаем любые входящие заголовки, чтобы фронт не спотыкался
        configuration.setAllowedHeaders(List.of("*"));

        // 4. Разрешаем читать любые заголовки в ответе (полезно для отладки)
        configuration.setExposedHeaders(List.of("*"));

        // 5. Ставим true, чтобы фронтенд мог слать запросы с credentials:'include' без ошибок
        configuration.setAllowCredentials(true);

        // Кэшируем CORS-ответ браузера на 1 час, чтобы не спамить лишними предзапросами OPTIONS
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Применяем конфигурацию абсолютно ко всем путям на бэкенде
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}









//package com.app.auth_service.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.cors.CorsConfigurationSource;
//
//import java.util.List;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        configuration.setAllowedOrigins(List.of(
//                "http://localhost:3000",
//                "http://localhost:5173"
//        ));
//        configuration.setAllowedMethods(List.of(
//                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
//        ));
//        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
//        configuration.setExposedHeaders(List.of("Authorization"));
//        configuration.setAllowCredentials(false);
//        configuration.setMaxAge(3600L);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/api/**", configuration);
//        return source;
//    }
//}


