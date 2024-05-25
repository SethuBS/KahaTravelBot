package org.kaha.com.travelbot;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaha.com.travelbot.dto.Country;
import org.kaha.com.travelbot.dto.SunriseSunsetTimes;
import org.kaha.com.travelbot.service.SunriseSunsetService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@AllArgsConstructor
public class SunriseSunsetServiceTest {

    @InjectMocks
    private SunriseSunsetService sunriseSunsetService;

    @Mock
    private RestTemplate restTemplate;

    @Value("${sunrise_sunset.api.url}")
    private String sunriseSunsetApiUrl;

    @BeforeEach
    @Deprecated
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetSunriseSunsetTimes() {
        // Arrange
        String response = "{\"results\": {\"sunrise\": \"12:00\", \"sunset\": \"18:00\"}}";

        when(restTemplate.getForObject(String.format("%s?lat=%f&lng=%f", sunriseSunsetApiUrl, -23.5, -45.6), String.class)).thenReturn(response);

        // Act
        SunriseSunsetTimes times = sunriseSunsetService.getSunriseSunsetTimes(new Country("Country 1", "Capital 1", -23.5F, -45.6F, List.of("Language 1"), "Driving Side"));

        // Assert
        assertNotNull(times);
        assertEquals("12:00", times.getSunrise());
        assertEquals("18:00", times.getSunset());
    }
}
