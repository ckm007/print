package io.mosip.print.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import io.mosip.kernel.core.http.ResponseWrapper;
import io.mosip.kernel.websub.api.annotation.PreAuthenticateContentAndVerifyIntent;
import io.mosip.print.model.CredentialStatusEvent;
import io.mosip.print.model.EventModel;
import io.mosip.print.model.MOSIPMessage;
import io.mosip.print.service.PrintService;
import io.swagger.annotations.ApiResponses;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/print")
public class Print {

	@Value("${mosip.event.secret}")
	private String secret;
	
	@Autowired
	PrintService printService;

    @PostMapping(value = "/enqueue",consumes = "application/json")
    @PreAuthenticateContentAndVerifyIntent(secret = "Kslk30SNF2AChs2",callback = "/print/enqueue",topic = "http://mosip.io/print/pdf")
	public void printPost(@RequestBody MOSIPMessage message) {
		System.out.println(message.getTopic());
		//TODO: Validate the MOSIPmessage
		//TODO:Call the print service with the map that we received from MOSIPMessage
		//printService.print()
	}
    
    @PostMapping(path = "/callback/notifyPrint", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//@ApiResponses(value = { @ApiResponse(code = 200, message = "Request authenticated successfully") })
	@PreAuthenticateContentAndVerifyIntent(secret = "test", callback = "/print/callback/notifyPrint", topic  = "792112/CREDENTIAL_ISSUED")
	public ResponseWrapper<?> handleSubscribeEvent( @RequestBody EventModel eventModel) {
    	
    	JSONArray jsonAray=new JSONArray(eventModel.getEvent().getData());
    	JSONObject jsonObj=new JSONObject(jsonAray.get(0).toString());
    	String credential=jsonObj.getString("credential");
    	byte[] str1=Base64.getDecoder().decode(credential);
    	System.out.println("Credential :  "+str1);
		return new ResponseWrapper<>();
	}
    @PostMapping(path = "/publish/statusPrint", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> handlePublishEvent(@RequestParam String topic, @RequestBody CredentialStatusEvent credentialStatusEvent) {
    	
    	printService.publishEvent(topic, credentialStatusEvent);
    	return new ResponseEntity<>("successfully published", HttpStatus.ACCEPTED);
	}
}
