package jp.go.saga.common.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.go.saga.common.Constants;
import jp.go.saga.common.message.MessageProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import bsmedia.system.config.ApplicationProperty;
import bsmedia.system.ibatis.PaginatedArrayList;
import bsmedia.system.ibatis.PaginatedList;
import bsmedia.system.util.StringUtil;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
@SuppressWarnings({"unchecked", "rawtypes"})
public class CommonDao extends SqlMapClientTemplate {
	@Autowired
	MessageProperty messageProperty;

	@Autowired
	public CommonDao(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	/**
	 * ページング処理に必要なPaginatedList形のListをリターン
	 *
	 * @param statementName statementName
	 * @param paramMap paramMap
	 * @return PaginatedList
	 */
	public PaginatedList queryForPaginatedList(String statementName, Map paramMap) {
		int pageSize;
		if(paramMap.get(Constants.PAGE_SIZE) == null){
			pageSize = Integer.parseInt(ApplicationProperty.get("page.size"));
		} else {
			pageSize = (Integer)paramMap.get(Constants.PAGE_SIZE);
			// 悪意のpageSizeを防ぐ
			if(pageSize > Integer.parseInt(ApplicationProperty.get("page.maxSize"))){
				throw new IllegalStateException(messageProperty.getMessage("F009"));
			}
		}

		return queryForPaginatedList(statementName,paramMap,pageSize);
	}

	/**
	 * ページング処理に必要なPaginatedList形のListをリターン
	 *
	 * @param statementName statementName
	 * @param paramMap paramMap
	 * @param pageSize pageSize
	 * @return PaginatedList
	 */

	public PaginatedList queryForPaginatedList(String statementName, Map paramMap, int pageSize) {

		int pageCurr = StringUtil.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String) paramMap.get("page"));
		int pageTotal = this.queryForInteger(statementName + "Count", paramMap);
		int startIdx = pageSize * (pageCurr - 1);
		int endIdx = startIdx + pageSize;

		//画面表示用パラメータ
		paramMap.put("TOTAL_CNT", pageTotal);
		paramMap.put("START_SNO", startIdx + 1);
		paramMap.put("END_SNO",   endIdx < pageTotal? endIdx:pageTotal);

		//SQL実行用パラメータ
		paramMap.put("startIdx", startIdx);
		paramMap.put("endIdx", endIdx);

		return new PaginatedArrayList(super.queryForList(statementName, paramMap), pageSize, pageTotal);
	}


	public Map queryForMap(String statementName) {
		return (Map) super.queryForObject(statementName);
	}
	public Map queryForMap(String statementName, Object parameterObject) {
		return (Map) super.queryForObject(statementName, parameterObject);
	}

	public Integer queryForInteger(String statementName) {
		return (Integer) super.queryForObject(statementName);
	}
	public Integer queryForInteger(String statementName, Object parameterObject) {
		return (Integer) super.queryForObject(statementName, parameterObject);
	}

	public String queryForString(String statementName) {
		return (String) super.queryForObject(statementName);
	}
	public String queryForString(String statementName, Object parameterObject) {
		return (String) super.queryForObject(statementName, parameterObject);
	}

	/**
	 * paramMap中のString[]を繰り返しinsert
	 *
	 * @param queryname クェリ名
	 * @param paramMap パラメータ
	 * @return されたrow数
	 * @throws SQLException
	 */
	public Integer insertMuliply(String queryname, Map<String, Object> paramMap) throws SQLException {
		return insertOrUpdateMultiply(queryname, paramMap, true);
	}
	/**
	 * paramMap中のString[]を繰り返しupdate
	 *
	 * @param queryname クェリ名
	 * @param paramMap パラメータ
	 * @return updateされたrow数
	 * @throws SQLException
	 */
	public Integer updateMuliply(String queryname, Map<String, Object> paramMap) throws SQLException {
		return insertOrUpdateMultiply(queryname, paramMap, false);
	}
	/**
	 *
	 * @param queryname クェリ名
	 * @param paramMap パラメータ
	 * @param insert insert/updateフラグ
	 * @return insert/updateされたrow数
	 */

	private Integer insertOrUpdateMultiply(String queryname, Map<String, Object> paramMap, boolean insert) throws SQLException {

		super.getSqlMapClient().startBatch();


		// 繰り返すクェリのサイズ。一番目のString[]のサイズと一致する。
		int loopSize = 0;
		// String[] 配列リスト
		List<Map> arrayValueMapList = new ArrayList();

		Iterator<String> itr = paramMap.keySet().iterator();
		while(itr.hasNext()){
			String key = itr.next();
			if(paramMap.get(key) instanceof String[]){

				String[] arrayValue = (String[])paramMap.get(key);

				if(loopSize != 0 && loopSize != arrayValue.length){
					throw new IllegalStateException(messageProperty.getMessage("F002"));
				}
				if(loopSize == 0){
					loopSize = arrayValue.length;
				}
				Map arrayValueMap = new HashMap();

				arrayValueMap.put("key", key);
				arrayValueMap.put("value", arrayValue);

				arrayValueMapList.add(arrayValueMap);
			}
		}

		if(arrayValueMapList.isEmpty()){
			return 0;
		}


		for (int i = 0; i < loopSize; i++) {

			Map param = new HashMap(paramMap);

			for (Map<String,String[]> arrayValueMap : arrayValueMapList) {
				param.put(arrayValueMap.get("key"), arrayValueMap.get("value")[i]);
			}

			if (insert) {
				insert(queryname, param);
			} else {
				update(queryname, param);
			}
		}

		int result = super.getSqlMapClient().executeBatch();
		return result;
	}
}