package jp.go.saga.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FLogoutController {
	
	@RequestMapping("/fLogout.do")
	public @ResponseBody void logout(HttpServletRequest request) throws Throwable {
		SecurityContextHolder.getContext().setAuthentication(null);
		request.getSession().invalidate();
		// response.sendRedirect(ApplicationProperty.get("sso.url.logout"));
	}
}
