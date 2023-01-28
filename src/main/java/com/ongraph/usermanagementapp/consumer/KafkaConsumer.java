package com.ongraph.usermanagementapp.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ongraph.usermanagementapp.dto.AddLoginHistoryDTO;
import com.ongraph.usermanagementapp.service.UserLoginHistoryService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaConsumer {

	@Autowired
	UserLoginHistoryService loginHistoryService;
	
	@KafkaListener(topics = {"${ongraph.kafka.topics.loginHistory}"}
					,groupId = "login.history",concurrency = "3")
	public void listen(@Payload AddLoginHistoryDTO loginHistoryDTO,@Header(name = KafkaHeaders.OFFSET) String offset) {
		log.info("consumed data:{} on offset:{}",loginHistoryDTO,offset);
		loginHistoryService.addLoginHistory(loginHistoryDTO);
	}
}
