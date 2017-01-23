package jp.go.saga.service.klb;

import java.util.List;
import java.util.Map;

import jp.go.saga.common.repository.CommonDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeService {

	@Autowired
	CommonDao dao;

	public List<?> getCode(Map<String, Object> paramMap) throws Throwable {
		return dao.queryForList("code.getCode", paramMap);
	}

	public List<?> getGradeCode(Map<String, Object> paramMap) throws Throwable {
		return dao.queryForList("code.getGradeCode", paramMap);
	}

	public List<?> selectCodeMasterList(Map<String, Object> paramMap) throws Throwable {
		return dao.queryForList("code.selectCodeMasterList", paramMap);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectCodeByCodeIdAndCode(Map<String, Object> paramMap) throws Throwable {
		return dao.queryForMap("code.selectCodeByCodeIdAndCode", paramMap);
	}


}