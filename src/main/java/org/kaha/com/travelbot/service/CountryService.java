package org.kaha.com.travelbot.service;

import org.kaha.com.travelbot.dto.Country;

import java.util.List;

public interface CountryService {
    List<Country> getAllCountries();

    Country getRandomCountryInSouthernHemisphere();
}
