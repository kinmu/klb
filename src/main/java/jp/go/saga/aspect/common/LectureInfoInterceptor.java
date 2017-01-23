package jp.go.saga.aspect.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.saga.common.Constants;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LectureInfoInterceptor extends HandlerInterceptorAdapter {
	private List<String> targetUrl = null;
	private String redirectUrl = null;
	
	public List<String> getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(List<String> targetUrl) {
		this.targetUrl = targetUrl;
	}
	
	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		List<?> lectureList = (List<?>) request.getSession().getAttribute("lectureList");
//		// GLOBAL_TEACH_ID 가 필요한 화면(Url)을 검사
//		if (lectureList != null && lectureList.size() > 0 && request.getSession().getAttribute(Constants.GLOBAL_TEACH_ID) == null) {			
//			for (String url: targetUrl) {
//				if (request.getRequestURI().indexOf(url) > 0) {
//					request.getRequestDispatcher(redirectUrl+"?alert=true").forward(request, response);
//				}
//			}
//		}
		
		return true;
	}
 
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)throws Exception {
	}
}