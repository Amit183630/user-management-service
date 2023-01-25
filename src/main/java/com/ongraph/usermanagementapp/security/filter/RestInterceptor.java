package com.ongraph.usermanagementapp.security.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ongraph.usermanagementapp.exception.CustomException;
import com.ongraph.usermanagementapp.model.ErrorCodes;
import com.ongraph.usermanagementapp.security.impl.UserDetailsServiceImpl;
import com.ongraph.usermanagementapp.security.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RestInterceptor implements HandlerInterceptor {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	String[] unSecPaths= {"/auth/signin","/auth/signup"};
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
//		if(!checkIfUnsecRequest(request)) {
//			var token=parseJwt(request);
//			if(token!=null && jwtUtil.validateJwtToken(token)) {
//				var username=jwtUtil.getUserNameFromJwtToken(token);
//				
//				var userDetails=userDetailsService.loadUserByUsername(username);
//				
//				var authentication=new UsernamePasswordAuthenticationToken(
//						userDetails, null,userDetails.getAuthorities());
//				
//				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				
//				SecurityContextHolder.getContext().setAuthentication(authentication);
//				return true;
//				
//			}else {
//				throw new CustomException(ErrorCodes.E_AUTH401);
//			}
//		}
		return true;
	}
	
	private boolean checkIfUnsecRequest(HttpServletRequest request) {
		var url=request.getRequestURI();
		for(var path:unSecPaths) {
			if(url.contains(path)) {
				return true;
			}
		}
		return false;
	}
	
	private String parseJwt(HttpServletRequest request) {
		var headerAuth=request.getHeader("Authorization");
		
		if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}

}
