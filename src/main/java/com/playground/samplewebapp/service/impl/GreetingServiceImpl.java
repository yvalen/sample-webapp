package com.playground.samplewebapp.service.impl;

import com.playground.samplewebapp.controller.Greeting;
import com.playground.samplewebapp.repository.GreetingRepository;
import com.playground.samplewebapp.repository.entity.GreetingEntity;
import com.playground.samplewebapp.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GreetingServiceImpl implements GreetingService {
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
        return entities.stream().map(Greeting::fromEntity).collect(Collectors.toList());
    }
}
