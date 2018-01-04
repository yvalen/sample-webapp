package com.playground.samplewebapp.repository;

import com.playground.samplewebapp.repository.entity.GreetingEntity;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.playground.samplewebapp.GreetingFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class GreetingRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private GreetingRepository greetingRepository;

    @Before
    public void setup() {
        greetingRepository.deleteAll();
    }

    @Test
    public void saveThenGet() {
        greetingRepository.save(new GreetingEntity(TEST_USER_1, TEST_MESSAGE_1));
        greetingRepository.save(new GreetingEntity(TEST_USER_1, TEST_MESSAGE_2));
        greetingRepository.save(new GreetingEntity(TEST_USER_2, TEST_MESSAGE_1));

        List<GreetingEntity> allGreetings = greetingRepository.findAll();
        assertThat(allGreetings)
                .extracting("user", "message")
                .containsExactlyInAnyOrder(
                        tuple(TEST_USER_1, TEST_MESSAGE_1),
                        tuple(TEST_USER_1, TEST_MESSAGE_2),
                        tuple(TEST_USER_2, TEST_MESSAGE_1)
                );

        List<GreetingEntity> greetingsFromUser = greetingRepository.findByUser(TEST_USER_1);
        assertThat(greetingsFromUser)
                .extracting("user", "message")
                .containsExactlyInAnyOrder(
                        tuple(TEST_USER_1, TEST_MESSAGE_1),
                        tuple(TEST_USER_1, TEST_MESSAGE_2)
                );
    }
}
