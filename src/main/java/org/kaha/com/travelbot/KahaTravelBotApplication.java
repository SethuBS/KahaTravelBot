/*
 * KAHA Programming Assignment - Console Application
 *
 * - At Kaha, we love to travel. We've created a basic program to help us dream up the next place we want to visit on holiday, providing you with all the essential information for your trip.
 *
 *Instructions:
 * - This assignment is designed to assess your coding skills, debugging abilities, and understanding of working with APIs.
 * - Your solution should be clean, well-organized, and thoroughly tested. If you are unsure of anything, state you assumptions in comments as you proceed with the assignment.
 * - You may make use of external libraries such as Newtonsoft.Json or System.Text.Json for proper JSON parsing and deserialization.
 * - Feel free to refactor and improve the code where necessary.
 * - Once you're done, submit your solution as a zip file via email to chat@kaha.co.za [FIRSTNAME SURNAME - TECH ASSIGNMENT YYYYMMDD.ZIP]
 *
 * Your assignment is to complete the following 3 tasks in this C# console application:
 *
 * 1. Fetching Country Data: (Working)
 *    - GetAllCountries is a working function in the application that returns a list of all countries from the public API at https://restcountries.com/v3.1/all
 *    - Your task in this section of the code is to refactor the code to the best of you ability (It has been deliberately written badly)
 *    - If you do choose to refactor this code, please ensure that it still successfully works as the subsequent functions rely on this call.
 *
 * 2. Random Country Selection: ()
 *    - The point of this function is to select a random country from the southern hemisphere.
 *    - There is a subtle bug in the code
 *    - Debug and fix the bug to ensure that a country from the southern hemisphere is correctly selected.
 *
 * 3. Sunrise and Sunset Times:
 *    - Implement the GetSunriseSunsetTimes function to retrieve sunrise and sunset times for the capital city of the selected random country.
 *    - Use the https://sunrise-sunset.org/api to fetch the data. This function is intentionally left empty for you to implement.
 *    - Once you have the information regarding the sunnset and sunrise display, display the times using Console.WriteLine in a friendly format
 *
 * 4. Travel Bot Summary
 *    - Wrap up the exercise by outputting an interesting country summary to the command line
 *    - This can consist of some key pieces of information that you think would be interesting for a potential traveler
 *    - Some key stats that we would like to see would be: Total number of official languages, the side of the inhabitants drive on
 *    - Bonus points will be awarded if you are able to convert all Console.Writeline actions to write all output in a "typewritter style"
 *    - Bonus points will also be awarded if you can specify the distance in KM between your random capital city and the KAHA offices
 *      (https://www.google.com/maps/place/Kaha/@-33.9759679,18.4566283,17z/data=!3m1!4b1!4m6!3m5!1s0x1dcc43998511fac3:0x4d7e223b5879a20a!8m2!3d-33.9759724!4d18.4592032!16s%2Fg%2F11h5l47g67?entry=ttu)
 *
 * Happy coding!
 */

package org.kaha.com.travelbot;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kaha.com.travelbot.dto.Country;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class KahaTravelBotApplication {

    // TODO: move Url to config file which is application.properties file
    // Urls in question: https://restcountries.com/v3.1/all, and https://api.sunrise-sunset.org/json?lat=" + country.getLatitude() + "&lng=" + country.getLongitude()

    public static void main(String[] args) {
        System.out.println("Welcome to the KAHA Travel Bot");
        System.out.println("Fetching all countries from https://restcountries.com");

        List<Country> countries = getAllCountries();

        if (countries.isEmpty()) {
            System.out.println("Error fetching countries!");
            return;
        }

        System.out.println("Choosing random country from the southern hemisphere...");
        Country randomCountry = randomCountryInSouthernHemisphere(countries);

        if (randomCountry != null) {
            System.out.println("Selected Random Country: " + randomCountry.getName());
            // Asynchronous call to get sunrise and sunset times for the random country
            getSunriseSunsetTimes(randomCountry);
        }
    }

     static List<Country> getAllCountries() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://restcountries.com/v3.1/all", String.class);
        String response = responseEntity.getBody();
        return parseCountryData(response);
    }

    static List<Country> parseCountryData(String response) {
        List<Country> countries = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Country country = new Country();
                JSONObject nameObject = jsonObject.getJSONObject("name");
                country.setName(nameObject.getString("common"));

                JSONArray capitalsArray = jsonObject.optJSONArray("capital");
                if (capitalsArray != null && capitalsArray.length() > 0) {
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

                // Extract language information from the nested "languages" object
                JSONObject languagesObject = jsonObject.optJSONObject("languages");
                if (languagesObject != null) {
                    List<String> languages = new ArrayList<>();
                    Iterator<String> keys = languagesObject.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        String languageName = languagesObject.getString(key);
                        languages.add(languageName);
                    }
                    country.setOfficialLanguages(languages); // Set officialLanguages property
                }

                // Extract driving side information nested under the "car" object
                JSONObject carObject = jsonObject.optJSONObject("car");
                if (carObject != null && carObject.has("side")) {
                    String drivingSide = carObject.getString("side");
                    country.setDrivingSide(drivingSide); // Set drivingSide property
                }


                countries.add(country);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return countries;
    }
    static Country randomCountryInSouthernHemisphere(List<Country> countries) {
        List<Country> countriesInSouthernHemisphere = new ArrayList<>();
        for (Country country : countries) {
            if (country.getLatitude() < 0) {
                countriesInSouthernHemisphere.add(country);
            }
        }

        if (!countriesInSouthernHemisphere.isEmpty()) {
            int randomIndex = new Random().nextInt(countriesInSouthernHemisphere.size());
            return countriesInSouthernHemisphere.get(randomIndex);
        }

        return null;
    }

    static void getSunriseSunsetTimes(Country country) {

        try {
            if (country == null || country.getCapital() == null || country.getLatitude() == 0 || country.getLongitude() == 0) {
                System.out.println("Country information is incomplete. Unable to retrieve sunrise and sunset times.");
                return;
            }

            String capital = country.getCapital();
            URL url = new URL("https://api.sunrise-sunset.org/json?lat=" + country.getLatitude() + "&lng=" + country.getLongitude());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            JSONObject json = new JSONObject(response.toString());
            JSONObject results = json.getJSONObject("results");
            String sunrise = results.getString("sunrise");
            String sunset = results.getString("sunset");

            System.out.println("Sunrise time in " + capital + ": " + sunrise);
            System.out.println("Sunset time in " + capital + ": " + sunset);

            // Output country summary in typewriter style
            System.out.println("=== Country Summary ===");
            System.out.println("Total number of official languages: " + (country.getOfficialLanguages() != null ? country.getOfficialLanguages().size() : 0));
            System.out.println("Side of the inhabitants drive on: " + (country.getDrivingSide() != null ? country.getDrivingSide() : "Information not available"));
            System.out.println();

            // Calculate distance between the random capital city and KAHA offices (example calculation)
            double distance = calculateDistance(country.getLatitude(), country.getLongitude(), -33.9759679, 18.4566283);
            System.out.println("Distance between " + (country.getCapital() != null ? country.getCapital() : "Capital information not available") + " and KAHA offices: " + distance + " km");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Example calculation of distance using latitude and longitude coordinates
        // You can use a more accurate formula for distance calculation based on the Earth's curvature

        double theta = lon1 - lon2;
        double distance = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        distance = Math.acos(distance);
        distance = Math.toDegrees(distance);
        distance = distance * 60 * 1.1515 * 1.609344; // Convert miles to kilometers

        return distance;
    }
}