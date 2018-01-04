package com.playground.samplewebapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.playground.samplewebapp.service.GreetingService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static com.playground.samplewebapp.GreetingFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GreetingControllerTest extends BaseControllerTest {
    private static final String SEND_GREETING_URI = "/greeting";
    private static final String GET_ALL_GREETINGS_URI = "/greeting";
    private static final String GET_GREETINGS_FROM_USER_URI = "/greeting/" + TEST_USER_1;

    private GreetingController greetingController;

    @Mock
    private GreetingService greetingService;

    @Captor
    private ArgumentCaptor<Greeting> greetingCaptor;

    private Greeting greeting1;
    private Greeting greeting2;
    private Greeting greeting3;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        greetingController = new GreetingController(greetingService);
        mockMvc = MockMvcBuilders.standaloneSetup(greetingController)
                .setMessageConverters(jacksonConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
        greeting1 = buildGreeting(TEST_USER_1, TEST_MESSAGE_1);
        greeting2 = buildGreeting(TEST_USER_1, TEST_MESSAGE_2);
        greeting3 = buildGreeting(TEST_USER_2, TEST_MESSAGE_1);
    }

    @Test
    public void sendGreeting() throws Exception {
        successfulRequest(buildSendGreetingRequest(greeting1));
        verify(greetingService).sendGreeting(greetingCaptor.capture());
        Greeting greeting = greetingCaptor.getValue();
        assertThat(greeting.getGreeter()).isEqualTo(TEST_USER_1);
        assertThat(greeting.getMessage()).isEqualTo(TEST_MESSAGE_1);
    }

    @Test
    public void returnGreetingsFromUser() throws Exception {
        ArgumentCaptor<String> userCaptor = ArgumentCaptor.forClass(String.class);
        when(greetingService.getGreetingsFromUser(userCaptor.capture()))
                .thenReturn(Arrays.asList( greeting1, greeting2));
        List<Greeting> greetings = objectMapper.readValue(
                successfulRequest(buildGetGreetingsFromUserRequest()).getContentAsString(),
                new TypeReference<List<Greeting>>(){}
        );
        assertThat(userCaptor.getValue()).isEqualTo(TEST_USER_1);
        assertThat(greetings).extracting("greeter", "message")
                .containsExactlyInAnyOrder(
                        tuple(TEST_USER_1, TEST_MESSAGE_1),
                        tuple(TEST_USER_1, TEST_MESSAGE_2)
                );
    }

    @Test
    public void returnAllGreetings() throws Exception {
        Map<String, List<Greeting>> greetingsByUser = new HashMap<>();
        greetingsByUser.put(TEST_USER_1, Arrays.asList(greeting1, greeting2));
        greetingsByUser.put(TEST_USER_2, Collections.singletonList(greeting3));
        when(greetingService.getAllGreetings()).thenReturn(greetingsByUser);
        Map<String, List<Greeting>> greetings = objectMapper.readValue(
                successfulRequest(buildGetAllGreetingsRequest()).getContentAsString(),
                new TypeReference<Map<String, List<Greeting>>>(){}
        );
        assertThat(greetings).containsOnly(
                entry(TEST_USER_1, Arrays.asList(greeting1, greeting2)),
                entry(TEST_USER_2, Collections.singletonList(greeting3))
        );
    }


    private Greeting buildGreeting(String user, String message) {
        return new Greeting(user, message);
    }

    private RequestBuilder buildSendGreetingRequest(Greeting greeting) throws JsonProcessingException {
        return MockMvcRequestBuilders
                .post(SEND_GREETING_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(greeting));
    }

    private RequestBuilder buildGetGreetingsFromUserRequest() {
        return MockMvcRequestBuilders
                .get(GET_GREETINGS_FROM_USER_URI)
                .accept(MediaType.APPLICATION_JSON);
    }

    private RequestBuilder buildGetAllGreetingsRequest() {
        return MockMvcRequestBuilders
                .get(GET_ALL_GREETINGS_URI)
                .accept(MediaType.APPLICATION_JSON);
    }
}
