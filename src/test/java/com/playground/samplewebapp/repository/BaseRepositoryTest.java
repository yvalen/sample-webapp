package com.playground.samplewebapp.repository;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
@TestPropertySource(locations="classpath:application-test.properties")
public abstract class BaseRepositoryTest {
}
