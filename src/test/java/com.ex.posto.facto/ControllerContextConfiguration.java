package com.ex.posto.facto;

import com.ex.post.facto.repository.BoardRepository;
import com.ex.post.facto.service.BoardService;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.ex.post.facto.controller" })
@Configuration
public class ControllerContextConfiguration {


    @Bean
    public BoardRepository getBoardRepository() {
        return Mockito.mock(BoardRepository.class);
    }

    @Bean
    public BoardService getRetrospectiveService() {
        return Mockito.mock(BoardService.class);
    }

}
