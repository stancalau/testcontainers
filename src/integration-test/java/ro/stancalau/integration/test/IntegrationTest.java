package ro.stancalau.integration.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.Network;
import ro.stancalau.integration.RouterContainer;
import ro.stancalau.router.model.Hop;
import ro.stancalau.router.model.HopPath;

import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class IntegrationTest {

    private static final int APP_PORT = 1234;

    private final ObjectMapper mapper = new ObjectMapper();
    private static final Network network = Network.newNetwork();

    @ClassRule
    public static RouterContainer container = RouterContainer.createContainer(network, "london", APP_PORT);

    @Test
    public void test() throws Exception {
        // Given
        String uri = "http://" + container.getContainerIpAddress() + ":" + container.getMappedPort(APP_PORT)
                + "/api/route/london/myMessage";
        HttpUriRequest request = new HttpGet(uri);

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

        InputStream content = response.getEntity().getContent();
        HopPath actualPath = mapper.readValue(content, HopPath.class);
        Hop expectedHop = new Hop("london", "hidden");
        assertThat(actualPath.getHops().contains(expectedHop), equalTo(true));
    }
}
