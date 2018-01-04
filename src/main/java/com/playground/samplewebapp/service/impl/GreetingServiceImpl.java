package com.playground.samplewebapp.service.impl;

import com.playground.samplewebapp.controller.Greeting;
import com.playground.samplewebapp.repository.GreetingRepository;
import com.playground.samplewebapp.repository.entity.GreetingEntity;
import com.playground.samplewebapp.service.GreetingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GreetingServiceImpl implements GreetingService {
    private static final Logger logger = LogManager.getLogger();

    private final GreetingRepository greetingRepository;

    @Autowired
    public GreetingServiceImpl(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    @Override
    public void sendGreeting(Greeting greeting) {
        greetingRepository.save(Greeting.toEntity(greeting));
    }

    @Override
    public Map<String, List<Greeting>> getAllGreetings() {
        List<GreetingEntity> entities = greetingRepository.findAll();
        return entities.stream().map(Greeting::fromEntity).collect(Collectors.groupingBy(Greeting::getGreeter));
    }

    @Override
    public List<Greeting> getGreetingsFromUser(String user) {
        List<GreetingEntity> entities = greetingRepository.findByUser(user);
        logger.debug("Greetings from {}: {}", user, entities);
        return entities.stream().map(Greeting::fromEntity).collect(Collectors.toList());
    }
}
