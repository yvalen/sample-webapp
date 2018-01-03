package com.playground.samplewebapp.controller;

import com.playground.samplewebapp.repository.entity.GreetingEntity;
import org.joda.time.DateTime;

public class Greeting {
    private String greeter;
    private String message;
    private DateTime timestamp;

    public Greeting() {}

    public Greeting(String greeter, String message) {
        this.greeter = greeter;
        this.message = message;
    }

    public Greeting(String greeter, String message, DateTime timestamp) {
        this.greeter = greeter;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getGreeter() {
        return greeter;
    }

    public void setGreeter(String greeter) {
        this.greeter = greeter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "greeter='" + greeter + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public static Greeting fromEntity(GreetingEntity entity) {
        return new Greeting(entity.getUser(), entity.getMessage(), entity.getTimestamp());
    }

    public static GreetingEntity toEntity(Greeting greeting) {
        return new GreetingEntity(greeting.getGreeter(), greeting.message);
    }
}
