package jp.go.saga.service.portal;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.go.saga.common.Constants;
import jp.go.saga.common.repository.CommonDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bsmedia.system.ibatis.PaginatedList;

@Service
public class LoginLogService {
	@Autowired
	CommonDao dao;

	public PaginatedList selectLoginLogList(Map<String,Object> paramMap) {
		return dao.queryForPaginatedList("loginLog.selectLoginLogList", paramMap);
	}

	public List<?> selectLoginLogUserDetail(Map<String,Object> paramMap){
		return dao.queryForList("loginLog.selectLoginLogUserDetail", paramMap);
	}


	public List<?> selectLoginLogByUserAndTime(Map<String,Object> paramMap){
		return dao.queryForList("loginLog.selectLoginLogByUserAndTime", paramMap);
	}

	public void insertLoginLogDetail(Map<String,Object> credentialMap){
		credentialMap.put("HTTP_IW_ROUTE",credentialMap.get(Constants.HTTP_IW_ROUTE));
		credentialMap.put("IW_SSL_CLIENT_I_DN",credentialMap.get(Constants.IW_SSL_CLIENT_I_DN));
		credentialMap.put("IW_SSL_CLIENT_I_DN_CN",credentialMap.get(Constants.IW_SSL_CLIENT_I_DN_CN));
		credentialMap.put("IW_SSL_CLIENT_I_DN_O",credentialMap.get(Constants.IW_SSL_CLIENT_I_DN_O));
		credentialMap.put("IW_SSL_CLIENT_S_DN",credentialMap.get(Constants.IW_SSL_CLIENT_S_DN));
		credentialMap.put("SSL_CLIENT_S_DN_CN",credentialMap.get(Constants.SSL_CLIENT_S_DN_CN));
		credentialMap.put("SSL_CLIENT_VERIFY",credentialMap.get(Constants.SSL_CLIENT_VERIFY));
		credentialMap.put("HTTP_IW_MAILPASS",credentialMap.get(Constants.HTTP_IW_MAILPASS));

		dao.insert("loginLog.insertLoginLogDetail",credentialMap);
	}


	public void insertLoginLogDetailForKLB(Map<String,Object> credentialMap, HttpServletRequest request) {

		credentialMap.put("HTTP_IW_ROUTE",credentialMap.get(Constants.HTTP_IW_ROUTE));
		credentialMap.put("IW_SSL_CLIENT_I_DN",credentialMap.get(Constants.IW_SSL_CLIENT_I_DN));
		credentialMap.put("IW_SSL_CLIENT_I_DN_CN",credentialMap.get(Constants.IW_SSL_CLIENT_I_DN_CN));
		credentialMap.put("IW_SSL_CLIENT_I_DN_O",credentialMap.get(Constants.IW_SSL_CLIENT_I_DN_O));
		credentialMap.put("IW_SSL_CLIENT_S_DN",credentialMap.get(Constants.IW_SSL_CLIENT_S_DN));
		credentialMap.put("SSL_CLIENT_S_DN_CN",credentialMap.get(Constants.SSL_CLIENT_S_DN_CN));
		credentialMap.put("SSL_CLIENT_VERIFY",credentialMap.get(Constants.SSL_CLIENT_VERIFY));
		credentialMap.put("HTTP_IW_MAILPASS",credentialMap.get(Constants.HTTP_IW_MAILPASS));
		credentialMap.put("USER_IP",request.getRemoteAddr());

		dao.insert("loginLog.insertLoginLogDetailForKLB", credentialMap);
	}

}