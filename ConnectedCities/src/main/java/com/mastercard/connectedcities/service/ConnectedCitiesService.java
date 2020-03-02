package com.mastercard.connectedcities.service;

import java.io.UnsupportedEncodingException;

public interface ConnectedCitiesService {
	
	public boolean areTheCitiesConnected(String city1, String city2) throws UnsupportedEncodingException;

}
