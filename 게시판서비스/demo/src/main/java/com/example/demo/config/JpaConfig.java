package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

    @Bean
    // String인 이유는 작성자의 이름을 넣을거기에 pk값을 넣는다면 Long으로 바꿔도 무방
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("admin"); // Todo: 스프링 시큐리티로 인즈기능 이용시 수정
    }
}
