# KahaTravelBot
This is a Java console application for the KAHA Travel Bot programming assignment. Let's break down the key points:

Purpose: The application is designed to assist travelers in planning their next holiday destination by providing essential information about various countries.

Instructions: The assignment outlines four tasks that need to be completed:

Fetching Country Data: Refactor the code to retrieve a list of all countries from the REST API provided.
Random Country Selection: Fix a subtle bug in the code that selects a random country from the southern hemisphere.
Sunrise and Sunset Times: Implement a function to retrieve sunrise and sunset times for the capital city of a randomly selected country using a specific API.
Travel Bot Summary: Output an interesting country summary to the command line, including the total number of official languages, the driving side, and the distance between the random capital city and KAHA offices.
Code Structure:

The KahaTravelBotApplication class contains the main method and other static methods for fetching country data, selecting a random country, retrieving sunrise and sunset times, and calculating distances.
It uses external libraries like RestTemplate for making HTTP requests and JSONObject for parsing JSON responses.
The code is organized into methods for better readability and maintainability.
Testing: The assignment emphasizes the importance of clean, well-organized, and thoroughly tested code. It suggests using external libraries for JSON parsing and deserialization and encourages refactoring and improving the code as necessary.

Overall, the assignment aims to assess the candidate's coding skills, debugging abilities, and understanding of working with APIs while implementing various features in the Java console application.

# Objectives:
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
