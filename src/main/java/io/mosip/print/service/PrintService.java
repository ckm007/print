package io.mosip.print.service;

import java.util.Map;

import io.mosip.print.model.CredentialStatusEvent;

public interface PrintService {
    
    /*public String print(Map map){
        //TODO: implement the logic to print the card here

        return "";
    }*/
	public void publishEvent(String topic,CredentialStatusEvent credentialStatusEvent);
}