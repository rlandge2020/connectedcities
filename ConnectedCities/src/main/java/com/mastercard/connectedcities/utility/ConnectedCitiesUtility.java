package com.mastercard.connectedcities.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
/**
 * 
 * @author Rahul Landge
 *
 */
@Component
public class ConnectedCitiesUtility {
	
	@Value("classpath:cities.txt")
	Resource resourceFile;

}
