package org.kaha.com.travelbot.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.kaha.com.travelbot.dto.Country;
import org.kaha.com.travelbot.dto.SunriseSunsetTimes;
import org.kaha.com.travelbot.service.SunriseSunsetService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class SunriseSunsetServiceImpl implements SunriseSunsetService {

    private static final Logger logger = LogManager.getLogger(CountryServiceImpl.class);

    private final String sunriseSunsetApiUrl;

    public SunriseSunsetServiceImpl(String sunriseSunsetApiUrl) {
        this.sunriseSunsetApiUrl = sunriseSunsetApiUrl;
    }

    @Override
    public SunriseSunsetTimes getSunriseSunsetTimes(Country country) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?lat=%f&lng=%f", sunriseSunsetApiUrl, country.getLatitude(), country.getLongitude());
        String response = restTemplate.getForObject(url, String.class);

        SunriseSunsetTimes times = new SunriseSunsetTimes();
        try {
            assert response != null;
            JSONObject json = new JSONObject(response);
            JSONObject results = json.getJSONObject("results");
            times.setSunrise(results.getString("sunrise"));
            times.setSunset(results.getString("sunset"));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return times;
    }
}
