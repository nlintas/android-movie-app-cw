# Couch Fever Movies
This is an Android project for browsing trending movies and finding out details and reviews about them. The content is fresh from the TMDB REST API.
# Languages Available
- English
# Technologies Used
- Java
- Gradle
- Android Studio IDE
- Volley HTTP Library
- GSON java library for serialization/de-serialization
# Features
- Filter movies within the page and once a search request is made (a query is entered and the search button is pressed) get the top 20 most relevant results (only first page supported). 
- Adult movies are clearly marked once clicked for their details (test search query: "the widow", WARNING its pornographic).
- Main screen uses the Trending movies API endpoint and displays the poster and title but also holds in the background the id to allow for getting the movie details.
- The movie details displays all data when available except: belongs to collection, popularity, original title, production countries, spoken languages, video. Most of these were deemed unnecessary.
- Paging, only for the main page when requesting trending movies.
- If a connection is slow or unstable loading screens are shown both at the results in the main screen and movie details.
- When the connection is completely offline a new screen appears automatically informing the user. Once the network returns the user is automatically redirected back to the main application.
# Compatibility
- API Level 31 with a minimum of 26
- Gradle JDK 11.0.10
- Gradle version 7.0.2
- Gradle Android Plugin version 7.0.4
- Developed with Android Studio Arctic Fox 2020.3.1 Patch 4
