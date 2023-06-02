package org.jwick.usermanagementapp.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.jwick.commonserviceapp.model.EmailDetails;
import org.jwick.usermanagementapp.entity.User;
import org.jwick.usermanagementapp.service.EmailService;
import org.jwick.usermanagementapp.util.LoggerHelper;
import org.jwick.usermanagementapp.util.Time;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private KafkaTemplate<String,Object> kafkaTemplate ;
	
	@Value("${ongraph.kafka.topics.registrationEmail}")
	private String registrationEmail;
	
	
	@Override
	public void emitEmailRegistrationEvent(User user) {
		
		EmailDetails emailDetails = EmailDetails.builder()
				.recipient(user.getEmail())
				.variables(new HashMap<>(){ private static final long serialVersionUID = 1L;

				{
					put("name", user.getFirstName());
					put("registrationLink", customeUrl(user.getConfirmationToken()));
				}
					
				}).build();
		var future = kafkaTemplate.send(registrationEmail, emailDetails);
		future.whenComplete((result,ex)->{
			if(ex!=null) {
				log.error("emitEmailRegistrationEvent()->error:{}",LoggerHelper.printStackTrace(ex));
			}else {
				var recordMetaData= result.getRecordMetadata();
				log.info("emitEmailRegistrationEvent()->event emitted on offset:{},time:{}",recordMetaData.offset(),Time.convert(recordMetaData.timestamp()));
			}
		});
		
	}
	
	
	@Override
	public String customeUrl(String confirmationToken) {
		String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/confirmEmail").queryParam("confirmationToken", confirmationToken).toUriString();
		return baseUrl ;
	}

}
