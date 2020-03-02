package com.mastercard.connectedcities.service.impl;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.mastercard.connectedcities.service.ConnectedCitiesService;

/**
 * 
 * @author Rahul Landge
 *
 */
@Service
public class ConnetedCitiesServiceImpl implements ConnectedCitiesService {

	@Autowired
	CacheManager cacheManager;

	/**
	 * 
	 * @param city1
	 * @param ciy2
	 * @return
	 * @throws UnsupportedEncodingException 
	 */

	@Override
	public boolean areTheCitiesConnected(String city1, String city2) throws UnsupportedEncodingException {
		String city1fromCache = null;
		List<String>  city2FromCache = null;
		ValueWrapper cityWrapper = null;
		
	    if (Objects.nonNull(city1)) {
	    	 city1 = java.net.URLDecoder.decode(city1, StandardCharsets.UTF_8.name());
			cityWrapper = cacheManager.getCache("cities").get(city1);
			if (cityWrapper!=null)
			city1fromCache = (String) cityWrapper.get();
			
	    }
		if (Objects.nonNull(city2)) {
			city2= java.net.URLDecoder.decode(city2, StandardCharsets.UTF_8.name());
			
		}
		System.out.println("City1 from cache:" + city1fromCache);
			city2FromCache =getCacheKeyForValue(city1);
		System.out.println("City2 from cache:" + city2FromCache);
	
			return (city2.equalsIgnoreCase(city1fromCache)) ? true
					: Objects.nonNull(city2FromCache) ? city2FromCache.contains(city2) : false;
		
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public List<String> getCacheKeyForValue(String value) {
		String valueFromCache = null;
		List<String> keyList = new ArrayList<>();
		@SuppressWarnings("unchecked")
		ConcurrentMap<String,String> cache = (ConcurrentMap<String,String>) cacheManager.getCache("cities").getNativeCache();
		for (Object entry : cache.entrySet()) {
			@SuppressWarnings("unchecked")
			Entry<String,String> element = (Entry<String,String>) entry;
			if (element != null) {
				valueFromCache = (String) element.getValue();
				System.out.println("Key" + element.getKey() + ":value:" + element.getValue() + "value from url:" + value);
				if (valueFromCache.equalsIgnoreCase(value)) {
					System.out.println("Match:Key" + element.getKey() + ":value:" + element.getValue() + "value from url:" + value);
					keyList.add(element.getKey());
				}
			}

		}
		return keyList;

	}
}
