package org.kaha.com.travelbot;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaha.com.travelbot.dto.Country;
import org.kaha.com.travelbot.service.CountryService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@AllArgsConstructor
public class CountryServiceTest {

    @InjectMocks
    private CountryService countryService;

    @Mock
    private RestTemplate restTemplate;

    @Value("${restcountries.api.url}")
    private String restCountriesApiUrl;

    @BeforeEach
    @Deprecated
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllCountries() {
        // Arrange
        String response = "{\"results\": [{\"name\": {\"common\": \"Country 1\"}, \"capital\": \"Capital 1\"}, {\"name\": {\"common\": \"Country 2\"}, \"capital\": \"Capital 2\"}]}";

        when(restTemplate.getForObject(restCountriesApiUrl, String.class)).thenReturn(response);

        // Act
        List<Country> countries = countryService.getAllCountries();

        // Assert
        assertNotNull(countries);
        assertEquals(2, countries.size());
        Country country1 = countries.get(0);
        assertEquals("Country 1", country1.getName());
        assertEquals("Capital 1", country1.getCapital());
        Country country2 = countries.get(1);
        assertEquals("Country 2", country2.getName());
        assertEquals("Capital 2", country2.getCapital());
    }

    @Test
    public void testGetRandomCountryInSouthernHemisphere() {
        // Arrange
        List<Country> countries = Arrays.asList(new Country("Country 1", "Capital 1", -23.5F, -45.6F, List.of("Language 1"), "Driving Side"), new Country("Country 2", "Capital 2", -30.5F, -50.6F, List.of("Language 2"), "Driving Side"));

        when(restTemplate.getForObject(restCountriesApiUrl, String.class)).thenReturn("");
        List<Country> allCountries = countryService.getAllCountries();
        allCountries.addAll(countries);

        // Act
        Country randomCountry = countryService.getRandomCountryInSouthernHemisphere();

        // Assert
        assertNotNull(randomCountry);
        assertEquals("Country 2", randomCountry.getName());
        assertEquals("Capital 2", randomCountry.getCapital());
    }

}
