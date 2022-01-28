package io.mosip.print.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import io.mosip.kernel.core.websub.spi.PublisherClient;
import io.mosip.kernel.core.websub.spi.SubscriptionClient;
import io.mosip.kernel.websub.api.model.SubscriptionChangeRequest;
import io.mosip.kernel.websub.api.model.SubscriptionChangeResponse;
import io.mosip.kernel.websub.api.model.UnsubscriptionRequest;
import io.mosip.print.model.CredentialStatusEvent;
import io.mosip.print.service.PrintService;

@Service
public class PrintServiceImpl implements PrintService{

	private String topic="CREDENTIAL_STATUS_UPDATE";

	@Value("${mosip.event.secret}")
	private String secret;
	  
	@Value("${mosip.event.hubURL}")   
	private String hubURL;
	
	@Autowired
	private PublisherClient<String, Object, HttpHeaders> pb;
	
	@Override
	public void publishEvent(String topic, CredentialStatusEvent credentialStatusEvent) {
		// TODO Auto-generated method stub
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cookie", "Authorizatoin=test");
		pb.publishUpdate(topic, credentialStatusEvent, MediaType.APPLICATION_JSON_UTF8_VALUE, headers,  hubURL+"/publish");
		
	}
	
	
	



	
}
