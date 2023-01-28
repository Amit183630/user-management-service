package com.ongraph.usermanagementapp.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ongraph.usermanagementapp.dto.AddLoginHistoryDTO;
import com.ongraph.usermanagementapp.entity.UserLoginHistory;
import com.ongraph.usermanagementapp.exception.CustomException;
import com.ongraph.usermanagementapp.model.ErrorCodes;
import com.ongraph.usermanagementapp.repository.UserLoginHistoryRepository;
import com.ongraph.usermanagementapp.repository.UserRepository;
import com.ongraph.usermanagementapp.service.UserLoginHistoryService;
import com.ongraph.usermanagementapp.util.LoggerHelper;
import com.ongraph.usermanagementapp.util.Time;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserLoginHistoryImpl implements UserLoginHistoryService {

	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;
	
	@Value("${ongraph.kafka.topics.loginHistory}")
	private String loginHistoryTopic;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserLoginHistoryRepository loginHistoryRepository;
	
	@Override
	public void emitAddLoginHistoryEvent(AddLoginHistoryDTO addLoginHistoryDTO) {
		
		var future=kafkaTemplate.send(this.loginHistoryTopic, addLoginHistoryDTO);
		future.whenComplete((result,ex)->{
			if(ex!=null) {
				log.error("emitAddLoginHistoryEvent()->error:{}",LoggerHelper.printStackTrace(ex));
			}else {
				var recordMetaData= result.getRecordMetadata();
				log.info("emitAddLoginHistoryEvent()->event emitted on offset:{},time:{}",recordMetaData.offset(),Time.convert(recordMetaData.timestamp()));
			}
		});
		
	}

	@Override
	public UserLoginHistory addLoginHistory(AddLoginHistoryDTO addLoginHistoryDTO) {
		var user=userRepository.findByUserName(addLoginHistoryDTO.getUsername()).orElseThrow(()->
			new CustomException(ErrorCodes.E_NOTFOUND404, "user not found with username:"+addLoginHistoryDTO.getUsername()));
		
		var loginHistory= new UserLoginHistory();
		loginHistory.setIpAddress(addLoginHistoryDTO.getIp());
		loginHistory.setLoggedIn(addLoginHistoryDTO.isLoggedIn());
		loginHistory.setLogInAttemptTime(addLoginHistoryDTO.getLoggedInTime());
		loginHistory.setUser(user);
		return loginHistoryRepository.save(loginHistory);
	}

}
