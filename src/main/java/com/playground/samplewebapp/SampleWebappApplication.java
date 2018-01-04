package com.playground.samplewebapp;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.playground.samplewebapp.repository")
@EnableMongoAuditing
public class SampleWebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleWebappApplication.class, args);
	}

	@Bean
	public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory,
													   MongoMappingContext context,
													   BeanFactory beanFactory) {
		DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
		MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
		try {
			mappingConverter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
		}
		catch (NoSuchBeanDefinitionException ignore) {}

		// Don't save _class to mongo
		mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));

		return mappingConverter;
	}


	@Bean
	public Module jodaModule() {
		return new JodaModule();
	}
}
