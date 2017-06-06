//Copyright 2015 Paul Tegelaar
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
package com.pte.liquid.relay.client.stomp;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.security.auth.login.LoginException;

import org.springframework.scheduling.annotation.Async;

import com.pte.liquid.relay.Marshaller;
import com.pte.liquid.relay.Transport;
import com.pte.liquid.relay.exception.RelayException;
import com.pte.liquid.relay.model.Message;

import net.ser1.stomp.Client;
import net.ser1.stomp.Listener;

public class StompTransport implements Transport, Listener {

	private static final String RELAY_STOMP_PORT = "relay_stomp_port";
	private static final String RELAY_STOMP_HOSTNAME = "relay_stomp_hostname";
	private static final String RELAY_DESTINATION = "relay_destination";
	
	
	private Marshaller marshaller;
	private String hostname = "localhost";
	private int port = 33555;
	private String destination = "com.pte.liquid.relay.in";
	private Client client;	
	

	public StompTransport(){
		
	}

	@Override
	@Async
	public synchronized void send(Message msg) throws RelayException {
		final String stringContent = marshaller.marshal(msg);
		
		try {
			if (client == null || client.isClosed()) {
				client = new Client(hostname, port, "", "");	
				client.addErrorListener(this);
			}	
			client.send("/queue/"+ destination, stringContent);		
		} catch (IOException e) {
			this.destroy();
			throw new RelayException(e);
		} catch (LoginException e) {
			this.destroy();
			throw new RelayException(e);
		}

	}

	public Marshaller getMarshaller() {
		return marshaller;
	}

	@Override
	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Override
	public void setProperties(Properties properties) {
		if (properties != null && properties.getProperty(RELAY_DESTINATION) !=null && !"".equals(properties.getProperty(RELAY_DESTINATION))) {
			destination = properties.getProperty(RELAY_DESTINATION);
		}
		if (properties != null && properties.getProperty(RELAY_STOMP_HOSTNAME) !=null && !"".equals(properties.getProperty(RELAY_STOMP_HOSTNAME))) {
			hostname = properties.getProperty(RELAY_STOMP_HOSTNAME);
		}
		if (properties != null && properties.getProperty(RELAY_STOMP_PORT) !=null && !"".equals(properties.getProperty(RELAY_STOMP_PORT))){
			port = Integer.parseInt(properties.getProperty(RELAY_STOMP_PORT));
		}		
		
		
		
	}

	@Override
	public void destroy() {	
		if(client!=null){
			try {
				client.delErrorListener(this);
				client.abort();
				client.disconnect();
				client=null;
			} catch (Exception e) {
				//Ignore all errors
			}
		}
		
	}

	@Override
	public void message(Map headers, String body) {
		this.destroy();
	}

}
