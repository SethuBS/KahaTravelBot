package org.kaha.com.travelbot.config;

import lombok.AllArgsConstructor;
import org.kaha.com.travelbot.service.CountryService;
import org.kaha.com.travelbot.service.SunriseSunsetService;
import org.kaha.com.travelbot.service.impl.CountryServiceImpl;
import org.kaha.com.travelbot.service.impl.SunriseSunsetServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@AllArgsConstructor
@Configuration
public class ApplicationConfig {
    @Value("${restcountries.api.url}")
    private String restCountriesApiUrl;
    @Value("${sunriseSunsetApiUrl}")
    private String sunriseSunsetApiUrl;

    @Bean
    public CountryService countryService() {
        return new CountryServiceImpl(restCountriesApiUrl);
    }

    @Bean
    public SunriseSunsetService sunriseSunsetService() {
        return new SunriseSunsetServiceImpl(sunriseSunsetApiUrl);
    }
}
