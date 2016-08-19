package com.pte.liquid.relay.test;

import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pte.liquid.relay.Transport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="test-stomp-application-context.xml")
public class TestStompRelay {
	
	private final static Logger logger = Logger.getLogger(TestStompRelay.class.getName());

	@Autowired
	private Transport relayStompTransport;

	@Test
	public void testInit(){
		logger.info("Start init test.");
		Assert.assertNotNull(relayStompTransport);
	}

	public Transport getRelayStompTransport() {
		return relayStompTransport;
	}

	public void setRelayStompTransport(Transport relayStompTransport) {
		this.relayStompTransport = relayStompTransport;
	}
	



	
	
	
}
