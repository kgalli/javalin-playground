package app.books;

import common.BaseIntegrationTest;
import de.kgalli.bookstore.utils.Path;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("Books Integration Test")
public class BooksIntegrationTest extends BaseIntegrationTest {

    @DisplayName("POST book")
    @Nested
    class PostBook {

        @Test
        @Disabled
        @DisplayName("should create book if input is valid")
        public void createBookWithValidInput() {
            assertThat(true, is(true));
        }

        @Test
        @Disabled
        @DisplayName("should fail validation if input is invalid")
        public void createBookWithInvalidInput() {
            assertThat(true, is(true));

        }
    }

    @DisplayName("GET book")
    @Nested
    class GetBooks {

        @Test
        @DisplayName("should fetch all book")
        public void fetchAllBooks() {
            var response = getRequest(Path.Api.BOOKS);
            var statusCode = response.statusCode();
            var body = responseToHashMapList(response.body());

            // TODO create some book and set list below accordingly
            var expectedBookList = Collections.EMPTY_LIST;

            assertThat(statusCode, is(200));
            assertThat(body, is(equalTo(expectedBookList)));

        }
    }

    private HttpResponse<String> getRequest(String path) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(
                    HttpRequest
                            .newBuilder(new URI("http://localhost:" + APP_PORT + path))
                                    .header("Content-Type", "application/json")
                                    .GET()
                                    .build(), HttpResponse.BodyHandlers.ofString()
                            );

            return response;
        } catch (URISyntaxException|IOException|InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Map<String, String> responseToHashMap(String response) {
        var objectMapper = new UncheckedObjectMapper();

        return objectMapper.readValue(response);
    }

    private List<Map<String, String>> responseToHashMapList(String response) {
        var objectMapper = new UncheckedObjectMapper();

        return objectMapper.readValues(response);
    }

    class UncheckedObjectMapper extends ObjectMapper {
        public Map<String, String> readValue(String content) {
            try {
                return this.readValue(content, new TypeReference<>() {
                });
            } catch (IOException ioe) {
                throw new CompletionException(ioe);
            }
        }

        public List<Map<String, String>> readValues(String content) {
            try {
                return this.readValue(content, new TypeReference<List>() {
                });
            } catch (IOException ioe) {
                throw new CompletionException(ioe);
            }
        }
    }
}
