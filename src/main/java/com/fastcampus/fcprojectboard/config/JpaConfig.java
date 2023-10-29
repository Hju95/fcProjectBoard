package com.fastcampus.fcprojectboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    // Article 에서 CreatedBy 에 대한 정보를 여기서 설정해줌
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of( "ju"); //TODO : 스프링 시큐리티로 인증 기능을 붙이게 될 때, 수정하자
    }
}
