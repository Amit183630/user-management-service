package org.jwick.usermanagementapp.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import org.jwick.commonserviceapp.cache.UserCacheRepository;
import org.jwick.commonserviceapp.model.ErrorCodes;
import org.jwick.usermanagementapp.dto.AddLoginHistoryDTO;
import org.jwick.usermanagementapp.entity.UserLoginHistory;
import org.jwick.usermanagementapp.exception.CustomException;
import org.jwick.usermanagementapp.repository.UserLoginHistoryRepository;
import org.jwick.usermanagementapp.repository.UserRepository;
import org.jwick.usermanagementapp.service.UserLoginHistoryService;
import org.jwick.usermanagementapp.transformer.ModelTransformer;
import org.jwick.usermanagementapp.util.LoggerHelper;
import org.jwick.usermanagementapp.util.Time;

import jakarta.transaction.Transactional;
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
	
	@Autowired
	UserCacheRepository userCacheRepository;
	
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
	@Transactional
	public UserLoginHistory addLoginHistory(AddLoginHistoryDTO addLoginHistoryDTO) {
		var user=userRepository.findByUserName(addLoginHistoryDTO.getUsername()).orElseThrow(()->
			new CustomException(ErrorCodes.E_NOTFOUND404, "user not found with username:"+addLoginHistoryDTO.getUsername()));
		
		var loginHistory= new UserLoginHistory();
		loginHistory.setIpAddress(addLoginHistoryDTO.getIp());
		loginHistory.setLoggedIn(addLoginHistoryDTO.isLoggedIn());
		loginHistory.setLogInAttemptTime(addLoginHistoryDTO.getLoggedInTime());
		loginHistory.setUser(user);
		loginHistory= loginHistoryRepository.save(loginHistory);
		user.getLoginHistories().add(loginHistory);
		userCacheRepository.saveOrUpdate(ModelTransformer.convertToUserDetails(user));
		return loginHistory;
	}

}
