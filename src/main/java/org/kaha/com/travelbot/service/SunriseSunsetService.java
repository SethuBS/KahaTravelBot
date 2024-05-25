package org.kaha.com.travelbot.service;

import org.kaha.com.travelbot.dto.Country;
import org.kaha.com.travelbot.dto.SunriseSunsetTimes;

public interface SunriseSunsetService {
    SunriseSunsetTimes getSunriseSunsetTimes(Country country);
}
