package io.mosip.print;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.mosip.kernel.core.websub.spi.SubscriptionClient;
import io.mosip.kernel.websub.api.model.SubscriptionChangeRequest;
import io.mosip.kernel.websub.api.model.SubscriptionChangeResponse;

@SpringBootApplication
public class PrintPDFApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrintPDFApplication.class, args);
	}

}
