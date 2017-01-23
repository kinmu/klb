package jp.go.saga.service.klb;

import jp.go.saga.common.repository.CommonDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {
	// private static final Logger logger = LoggerFactory.getLogger(MainService.class);

	@Autowired
	CommonDao dao;


// 2015.01.06 jungsr delete
//	// 교수자의 강좌목록 조회
//	public List<?> selectTeacherLectureList(Map<String,Object> paramMap) {
//		return dao.queryForList("main.selectTeacherLectureList",paramMap);
//	}
//
//	public Map<String, Object> selectStudentClassInfo(Map<String,Object> paramMap) {
//		return dao.queryForMap("main.selectStudentClassInfo",paramMap);
//	}
//
//	// 교수자의 강좌목록 조회
//	public List<?> selectTeacherYearList(Map<String,Object> paramMap) {
//		return dao.queryForList("main.selectTeacherYearList",paramMap);
//	}
//
//	// 학생 시간표 목록 조회
//	@SuppressWarnings("unchecked")
//	public List<?> selectStudentTimeTableList(Map<String,Object> paramMap) {
//		List<Map<String, Object>> timeTableList = dao.queryForList("main.selectStudentTimeTableList",paramMap);
//
//		List<Map<String, Object>> selectStudentAllList = dao.queryForList("teach.selectStudentAllList",paramMap);
//
//		Map contMap = new HashMap();
//		List t = new ArrayList();
//		String temp = "";
//		int p = 0;
//		for(Map cont : selectStudentAllList) {
//			p++;
//			if(temp.equals("") || !temp.equals((String)cont.get("SCHEDULE_ID"))) {
//				if(!temp.equals("")&& !temp.equals((String)cont.get("SCHEDULE_ID"))) {
//					contMap.put(temp,t);
//					t = new ArrayList();
//					t.add(cont);
//				}
//				if(temp.equals("")) {
//					t.add(cont);
//				}
//				temp = (String)cont.get("SCHEDULE_ID");
//			} else {
//				t.add(cont);
//			}
//			if(p==selectStudentAllList.size()) {
//				contMap.put(temp,t);
//			}
//		}
//
//		for (int i = 0, j = timeTableList.size(); i < j; i++) {
//			Map<String, Object> tableMap = timeTableList.get(i);
//
//			String scheduleId = StringUtil.getStr((String) tableMap.get("SCHEDULE_ID"),"");
//
//			if (!"".equals(scheduleId)) {
//				Map<String, Object> scheduleMap = new HashMap<String, Object>();
//				paramMap.put("GLOBAL_CURRICULUM_CODE", StringUtil.getStr((String) tableMap.get("CURRICULUM_CODE"),""));
//				paramMap.put("SCHEDULE_ID", scheduleId);
//				int testCnt = 0;
//				int exerciseCnt = 0;
//				int forumCnt = 0;
//				List<Map<String, Object>> cntContList = (List<Map<String, Object>>)contMap.get(scheduleId);
//				if(cntContList == null) {
//					 testCnt = 0;
//					 exerciseCnt = 0;
//					 forumCnt = 0;
//				} else {
//					for(Map cntCont : cntContList) {
//						String div = (String)cntCont.get("DIV");
//						if(div.equals("testList")) {
//							testCnt = Integer.parseInt(((String)cntCont.get("CNT")));
//						}
//						if(div.equals("exerciseList")) {
//							exerciseCnt = Integer.parseInt(((String)cntCont.get("CNT")));
//						}
//						if(div.equals("forumList")) {
//							forumCnt = Integer.parseInt(((String)cntCont.get("CNT")));
//						}
//					}
//				}
//
//				scheduleMap.put("testCnt", 		testCnt); 		// 시험갯수
//				scheduleMap.put("exerciseCnt", 	exerciseCnt); 	// 교재갯수
//				scheduleMap.put("forumCnt", 	forumCnt); 		// 토론갯수
//
//				tableMap.put("SCHEDULE_ID_DETAIL", scheduleMap);
//				timeTableList.set(i, tableMap);
//			}
//		}
//
//		return timeTableList;
//	}
//
//	// 강좌명 조회
//	@SuppressWarnings("unchecked")
//	public Map<String, Object> selectLectureName(Map<String,Object> paramMap) {
//		return dao.queryForMap("main.selectLectureName",paramMap);
//	}
//
//	//학생 미응시 건수
//	public List<?> selectStudentNewAllCnt(Map<String,Object> paramMap) {
//		return dao.queryForList("main.selectStudentNewAllCnt", paramMap);
//	}
//
//	// 학생 미응시 온라인테스트 건수
//	public Integer selectStudentNewOnlineTestCount(Map<String,Object> paramMap) {
//		return dao.queryForInteger("onlineteststudent.selectStudentNewOnlineTestCount", paramMap);
//	}
//
//	// 학생 미제출 과제 건수
//	public Integer selectStudentNewHomworkCount(Map<String,Object> paramMap) {
//		return dao.queryForInteger("homework.selectStudentNewHomworkCount", paramMap);
//	}
//
//	// 학생 미참여 포럼 건수
//	public Integer selectStudentNewForumCount(Map<String,Object> paramMap) {
//		return dao.queryForInteger("forum.selectStudentNewForumCount", paramMap);
//	}
// 2015.01.06 jungsr delete
}