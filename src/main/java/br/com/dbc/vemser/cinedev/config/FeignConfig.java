package br.com.dbc.vemser.cinedev.config;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {

    @Bean
    public Contract feignContract() {

        return new Contract.Default();
    }

    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .errorDecoder(new SimpleErrorDecode())
                .decoder(new JacksonDecoder());
    }
}