package com.pte.liquid.relay.test;

import java.util.Date;
import java.util.UUID;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pte.liquid.relay.Transport;
import com.pte.liquid.relay.exception.RelayException;
import com.pte.liquid.relay.model.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="test-jms-application-context.xml")
public class TestJmsRelay {
	
	private final static Logger logger = Logger.getLogger(TestJmsRelay.class);

	@Autowired
	private Transport relayJmsTransport;

	@Test
	public void testInit(){
		logger.info("Start init test.");
		Assert.assertNotNull(relayJmsTransport);
	}
	
	/*@Test
	public void testSend() throws RelayException, InterruptedException{
		Message msg = new Message();
		msg.createPart("test", "Hallo hallo");
		msg.createPart("test1", "<test></test>");
		msg.setHeader("test", "tester");
		msg.setHeader("Hallo", "daar");
		msg.setSnapshotTime(new Date());
		
		msg.setTfl("TestLocation");		
		for (int i = 0; i < 10000; i++) {
			msg.setId(UUID.randomUUID().toString());
			relayJmsTransport.send(msg);
			Thread.sleep(20000);
			
		}
	}*/

	public Transport getRelayJmsTransport() {
		return relayJmsTransport;
	}

	public void setRelayJmsTransport(Transport relayJmsTransport) {
		this.relayJmsTransport = relayJmsTransport;
	}


	
	
	
}
