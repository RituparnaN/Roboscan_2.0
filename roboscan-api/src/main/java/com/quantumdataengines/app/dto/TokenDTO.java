package com.quantumdataengines.app.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*@Getter
@Setter
@AllArgsConstructor*/
@SuppressWarnings("serial")
public class TokenDTO implements Serializable {

    private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public TokenDTO(String token) {
		super();
		this.token = token;
	}

}