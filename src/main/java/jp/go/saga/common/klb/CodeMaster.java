package jp.go.saga.common.klb;

/**
 * CodeMaster
 *
 */
public class CodeMaster {
	/*
		C001	사용자구분
		C002	요일
		C003	학기구분
		C004	학년
		M001	공개범위
		M002	난이도
		M003	미디어유형
		M004	응시상태
		M005	제출구분
		M006	추천구분
		M007	토론구분
		M008	토론상태
		M009	평가구분
		M010	문제순서
		M011	출제구분
		M012	출제기관
		M013	문제유형
		M014	평가상태
	 */
	
	/*
		0012	출석상태
				{'01', '04', '09', '12'} : 출석상태 표시 코드 {'출석', '결석', '조퇴', '기타'}
	*/

	// 공개범위
	public static final String OPEN_TYPE = "M001";
	// 난이도
	public static final String DIFFICULTY_TYPE = "M002";
	// 미디어유형
	public static final String MEDIA_TYPE = "M003";
	// 응시상태
	public static final String APPLY_STATUS_TYPE = "M004";
	// 추천구분
	public static final String RECOMMAND_TYPE = "M006";
	// 토론구분
	public static final String FORUM_TYPE = "M007";
	// 평가구분
	public static final String TEST_TYPE = "M009";
	// 문제순서
	public static final String QUESTION_ORDER = "M010";
	// 출제구분
	public static final String EXAM_TYPE = "M011";
	// 출제기관
	public static final String EXAM_OWNER = "M012";
	// 문제유형
	public static final String QUESTION_TYPE = "M013";
	// 문제유형
	public static final String TEST_STATUS = "M014";
    // 학년코드
    public static final String GRADE_TYPE = "C004";
    // 응시상태코드 
    // 3:완료
    public static final String APPLY_STATUS_CODE = "3";
	
	// 출석상태
	public static final String ATTENDANCE_STATUS = "0012";
	// 출석상태 상세
	public static final String[] ATTENDANCE_STATUS_DISPLAY = {"01", "04", "09", "12"};
}
