package jp.go.saga.service.klb;

import java.util.List;
import java.util.Map;

import jp.go.saga.common.repository.CommonDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

	@Autowired
	CommonDao dao;

	public List<?> getCategoryListForCheckbox(Map<String, Object> paramMap) throws Throwable {
		return dao.queryForList("category.selectCategoryListForCheckbox", paramMap);
	}

	public List<?> getCategoryListForChecked(Map<String, Object> paramMap) throws Throwable {
		return dao.queryForList("category.selectCategoryListForChecked", paramMap);
	}

	public List<?> getCategoryListForListbox(Map<String, Object> paramMap) throws Throwable {
		return dao.queryForList("category.selectCategoryListForListbox", paramMap);
	}
}
