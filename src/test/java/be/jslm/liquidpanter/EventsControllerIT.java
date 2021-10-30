package be.jslm.liquidpanter;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventsControllerIT  {

    @LocalServerPort
    private int randomServerPort;

    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setup() {
        this.testRestTemplate = new TestRestTemplate();
    }

    @Test
    public void deletingKnownEntityShouldReturn404AfterDeletion() {

        long eventId = 1;
        String baseUrl = "http://localhost:" + randomServerPort;

        ResponseEntity<JsonNode> firstResult =
          this.testRestTemplate.getForEntity(baseUrl + "/api/events/" + eventId, JsonNode.class);

        log.error(firstResult.toString());

        assertThat(firstResult.getStatusCode(), is(HttpStatus.OK));

        this.testRestTemplate.delete(baseUrl + "/api/events/" + eventId);

        ResponseEntity<JsonNode> secondResult = this.testRestTemplate
          .getForEntity(baseUrl + "/api/events" + eventId, JsonNode.class);

        log.error(secondResult.toString());

        assertThat(secondResult.getStatusCode(), is(HttpStatus.NOT_FOUND));

    }

}
