package org.kaha.com.travelbot.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kaha.com.travelbot.dto.Country;
import org.kaha.com.travelbot.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Service
public class CountryServiceImpl implements CountryService {

    private static final Logger logger = LogManager.getLogger(CountryServiceImpl.class);
    private final String restCountriesApiUrl;


    public CountryServiceImpl(String restCountriesApiUrl) {
        this.restCountriesApiUrl = restCountriesApiUrl;
    }

    @Override
    public List<Country> getAllCountries() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(restCountriesApiUrl, String.class);
        return parseCountryData(responseEntity.getBody());
    }

    private List<Country> parseCountryData(String response) {
        List<Country> countries = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Country country = new Country();
                JSONObject nameObject = jsonObject.getJSONObject("name");
                country.setName(nameObject.getString("common"));

                JSONArray capitalsArray = jsonObject.optJSONArray("capital");
                if (capitalsArray != null && !capitalsArray.isEmpty()) {
                    country.setCapital(capitalsArray.getString(0));
                }

                JSONObject capitalInfo = jsonObject.optJSONObject("capitalInfo");
                if (capitalInfo != null && capitalInfo.has("latlng")) {
                    JSONArray latlngArray = capitalInfo.getJSONArray("latlng");
                    if (latlngArray.length() == 2) {
                        country.setLatitude((float) latlngArray.getDouble(0));
                        country.setLongitude((float) latlngArray.getDouble(1));
                    }
                }

                JSONObject languagesObject = jsonObject.optJSONObject("languages");
                if (languagesObject != null) {
                    List<String> languages = new ArrayList<>();
                    Iterator<String> keys = languagesObject.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        String languageName = languagesObject.getString(key);
                        languages.add(languageName);
                    }
                    country.setOfficialLanguages(languages);
                }

                JSONObject carObject = jsonObject.optJSONObject("car");
                if (carObject != null && carObject.has("side")) {
                    String drivingSide = carObject.getString("side");
                    country.setDrivingSide(drivingSide);
                }

                countries.add(country);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return countries;
    }

    @Override
    public Country getRandomCountryInSouthernHemisphere() {
        List<Country> countries = getAllCountries();
        List<Country> southernCountries = new ArrayList<>();
        for (Country country : countries) {
            if (country.getLatitude() < 0) {
                southernCountries.add(country);
            }
        }

        if (!southernCountries.isEmpty()) {
            int randomIndex = new Random().nextInt(southernCountries.size());
            return southernCountries.get(randomIndex);
        }
        return null;
    }
}
