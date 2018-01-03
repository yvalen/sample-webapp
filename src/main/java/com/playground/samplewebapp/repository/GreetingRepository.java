package com.playground.samplewebapp.repository;

import com.playground.samplewebapp.repository.entity.GreetingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GreetingRepository extends MongoRepository<GreetingEntity, String> {
    List<GreetingEntity> findByUser(String user);
}
