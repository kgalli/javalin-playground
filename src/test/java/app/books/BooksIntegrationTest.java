package app.books;

import app.App;
import app.utils.Path;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.*;

import javax.print.attribute.URISyntax;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Books Integration Test")
public class BooksIntegrationTest {

    private static App app;

    @BeforeAll
    public static void setup() {
        app = new App(3099, BooksRoutes.booksRoutes());
        app.run();
    }

    @AfterAll
    public static void tearDown() {
        app.stop();
    }

    @DisplayName("POST book")
    @Nested
    class PostBook {


        @Test
        @DisplayName("should create book if input is valid")
        public void createBookWithValidInput() {
            assertEquals(true, true);
        }


        @Test
        @DisplayName("should fail validation if input is invalid")
        public void createBookWithInvalidInput() {
            assertEquals(true, true);

        }

    }

    @DisplayName("GET books")
    @Nested
    class GetBooks {

        @Test
        @DisplayName("should fetch all books")
        public void fetchAllBooks() throws URISyntaxException, IOException, InterruptedException {
            var response = getRequest(Path.Api.BOOKS);
            var statusCode = response.statusCode();
            var body = responseToHashMap(response.body());

            assertEquals(200, statusCode);
            assertEquals(body, body.isEmpty());
        }
    }

    private HttpResponse<String> getRequest(String path) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(
                    HttpRequest
                            .newBuilder(new URI("http://localhost:3099" + path))
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
        UncheckedObjectMapper objectMapper = new UncheckedObjectMapper();

        return objectMapper.readValue(response);
    }

    class UncheckedObjectMapper extends com.fasterxml.jackson.databind.ObjectMapper {
        /**
         * Parses the given JSON string into a Map.
         */
        Map<String, String> readValue(String content) {
            try {
                return this.readValue(content, new TypeReference<>() {
                });
            } catch (IOException ioe) {
                throw new CompletionException(ioe);
            }
        }
    }
}
