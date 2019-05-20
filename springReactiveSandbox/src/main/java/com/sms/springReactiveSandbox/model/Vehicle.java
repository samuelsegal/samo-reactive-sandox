package com.sms.springReactiveSandbox.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document
public class Vehicle {

	private String id;
	private String name;
	private String description;
}
