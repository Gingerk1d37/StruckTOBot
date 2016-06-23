package ca.gkwb.struckto.location.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import com.google.maps.model.GeocodingResult;

import ca.gkwb.struckto.location.StruckTOLocationBOImpl;
import ca.gkwb.twitter.connector.TwitterConnector;
import ca.gkwb.twitter.connector.TwitterConnectorImpl;
import twitter4j.Status;

public class StruckTOLocationBOTest {

	TwitterConnector tConn;
	StruckTOLocationBOImpl stlBO;
	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Before
	public void setUp() throws Exception {
		stlBO = new StruckTOLocationBOImpl("", "Toronto", "Canada");
		tConn = new TwitterConnectorImpl();
		tConn.connect();		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testParseLocations() {
		try {
			List<Status> status = tConn.getStatusByRegex("TPSOperations", tConn.PEDESTRIAN_REGEX, 100);
			logger.debug(status.size());
			for (Status s : status) {
				List<String> intersection = stlBO.parseStreetLocation(s.getText());
				logger.debug(intersection);
				Assert.isTrue(intersection.size()==2);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.isTrue(false);
		}
	}
	
	@Test
	public void testGetGeoLocation() {
		try {
			List<String> intersection = new ArrayList<String>();
			intersection.add("Yonge St");
			intersection.add("Sheppard Ave");
			List<GeocodingResult> results = stlBO.getGeoData(intersection, "Toronto", "Canada");
			for (int i=0;i<results.size();i++) {
				GeocodingResult g = results.get(i);
				logger.debug("For result " + i + " ########################## ");
				//geometry
				logger.debug(g.geometry);
				logger.debug(g.geometry.location);
				logger.debug(g.geometry.location.lat);
				logger.debug(g.geometry.location.lng);
				
				
				for (int j=0;j<g.addressComponents.length;j++) {
					logger.debug("AddressComponent " + j + " = " + g.addressComponents[j]);
				}
				logger.debug(g.formattedAddress);
				logger.debug(g.placeId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.isTrue(false);
		}
	}
	
	@Test
	public void testQueryLocationsByTweets() {
		try {
			List<Status> status = tConn.getStatusByRegex("TPSOperations", tConn.PEDESTRIAN_REGEX, 100);
			logger.debug(status.size());
			for (Status s : status) {
				List<String> intersection = stlBO.parseStreetLocation(s.getText());
				logger.debug(intersection);
				Assert.isTrue(intersection.size()==2);
				
				List<GeocodingResult> results = stlBO.getGeoData(intersection, "Toronto", "Canada");
				for (int i=0;i<results.size();i++) {
					GeocodingResult g = results.get(i);
					logger.debug("For result " + i + " ########################## ");
					//geometry
					logger.debug(g.geometry);
					logger.debug(g.geometry.location);
					logger.debug(g.geometry.location.lat);
					logger.debug(g.geometry.location.lng);
					
					
					for (int j=0;j<g.addressComponents.length;j++) {
						logger.debug("AddressComponent " + j + " = " + g.addressComponents[j]);
					}
					logger.debug(g.formattedAddress);
					logger.debug(g.placeId);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.isTrue(false);
		}
	}
	
	@Test
	public void testProcessTweets() {
		try {
			List<Status> status = tConn.getStatusByRegex("TPSOperations", tConn.PEDESTRIAN_REGEX, 100);
			logger.debug(status.size());
			for (Status s : status) {
				stlBO.processOneTweet(s.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.isTrue(false);
		}
	}	
}

