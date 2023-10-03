# Testcontainers and WireMock demo application

1. Install [Testcontainers Desktop app](https://testcontainers.com/desktop?utm_medium=event&utm_source=2023-devoxx&utm_content=session-demo-app). It is free to try and takes less than 5 minutes!
2. Clone this repository or download and unzip it. 
3. Run application locally letting Spring Boot and Testcontainers set up a database for it: `./mvnw spring-boot:test-run`
4. Open the application in the browser: [link](http://localhost:8080/?http://localhost:8080/todos)
5. curl -X POST http://localhost:8080/todos/hn

P.S Check out the `ContainersConfig` class to see how elegant the Spring Boot and Testcontainers integration is now.

## References

- [WireMock Module for Testcontainers](https://testcontainers.com/modules/wiremock/)
- [HackerNews Mock API](https://library.wiremock.org/catalog/api/y/ycombinator.com/hackernews_v0/) on the
  [WireMock API Templates Library](https://library.wiremock.org/)
