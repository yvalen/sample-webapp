package com.playground.samplewebapp.repository.entity;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "greeting")
public class GreetingEntity {
    @Id
    private ObjectId id;

    @Indexed
    private String user;

    private String message;

    @LastModifiedDate
    private DateTime timestamp;

    public GreetingEntity() {}

    public GreetingEntity(String user, String content) {
        this.user = user;
        this.message = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
        return "GreetingEntity{" +
                "user='" + user + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
