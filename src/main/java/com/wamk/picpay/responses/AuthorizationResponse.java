package com.wamk.picpay.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorizationResponse {

	@JsonProperty("status")
	private String string;

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}
}
