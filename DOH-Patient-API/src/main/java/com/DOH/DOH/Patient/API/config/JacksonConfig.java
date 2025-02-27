package com.DOH.DOH.Patient.API.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfig {

	@Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS"));
        mapper.setDateFormat(new java.text.SimpleDateFormat("yyyy-MM-dd"));
        return mapper;
    }
	
}
