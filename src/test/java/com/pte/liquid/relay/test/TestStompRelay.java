package com.pte.liquid.relay.test;

import java.util.logging.Logger;

import org.apache.activemq.broker.Broker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pte.liquid.relay.Marshaller;
import com.pte.liquid.relay.Transport;
import com.pte.liquid.relay.client.stomp.StompTransport;
import com.pte.liquid.relay.exception.RelayException;
import com.pte.liquid.relay.model.Message;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="test-stomp-application-context.xml")
public class TestStompRelay {
	
	private final static Logger logger = Logger.getLogger(TestStompRelay.class.getName());

	@Autowired
	private Transport relayStompTransport;
	
	@Autowired
	private Marshaller relayApiMarshaller;

	
	@Test
	public void testInit(){
		logger.info("Start init test.");
		Assert.assertNotNull(relayStompTransport);
	}
	
	@Test
	public void testSend(){
		logger.info("Start send test.");
		Message msg = new Message();
		msg.setId("123");		
		msg.createPart("Hallo", "dit is een test");
		try{
			relayStompTransport.send(msg);	
			
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}
		
		
	}
	
	@Test
	public void testSendFail(){
		StompTransport failRelayTransport = new StompTransport();
		failRelayTransport.setMarshaller(relayApiMarshaller);
		failRelayTransport.setPort(33556);
		
		logger.info("Start fail send test.");
		Message msg = new Message();
		msg.setId("1234");		
		msg.createPart("Hallo", "dit is een test");
		

			try {
				failRelayTransport.send(msg);
				Assert.fail("Send should cause exception");
			} catch (RelayException e) {
				Assert.assertTrue(true);
			}
		
		
	}

	public Transport getRelayStompTransport() {
		return relayStompTransport;
	}

	public void setRelayStompTransport(Transport relayStompTransport) {
		this.relayStompTransport = relayStompTransport;
	}
	



	
	
	
}
