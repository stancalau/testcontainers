package ro.stancalau.router.service;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;
import ro.stancalau.router.config.HopConfig;
import ro.stancalau.router.model.Hop;
import ro.stancalau.router.model.HopPath;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class RoutingServiceTest {

    private static final String NAME = "London";
    private static HopConfig emptyHopsConfig = new HopConfig(NAME, Collections.emptyList());

    @Mock
    private RestTemplate template;

    private RoutingService service;

    @Test
    public void givenNoHopsWhenCallSelfThenGetPath() {
        //Given
        service = new RoutingService(emptyHopsConfig, template);
        HopPath expected = new HopPath(new Hop(NAME, "hidden"));

        //When
        HopPath route = service.route("London", "myMessage");

        //Then
        assertEquals(expected, route);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenNoHopsWhenCallNonExistingThenIllegalArgumentException() {
        //Given
        service = new RoutingService(emptyHopsConfig, template);
        HopPath expected = new HopPath(new Hop("London", "hidden"));

        //When
        HopPath route = service.route("NonExistingHop", "myMessage");
    }
}
