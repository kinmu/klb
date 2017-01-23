package jp.go.saga.common;

import bsmedia.system.config.ApplicationProperty;

public class Constants {
	public static final String CURRENT_ACTION = "javax.servlet.forward.request_uri";
	public static final String VELOCITY_ENGINE = "velocityEngine";
	public static final String SORT_KEY = "sortKey";
	public static final String SORT_ORDER = "sortOrder";
	public static final String EXCEPTION_KEY = "jcf.EXCEPTION";

	/*
	 * common sort by parameter name
	 */
	public static final String T_SORT_BY = "T_SORT_BY";

	/*
	 * ファイル関連
	 */
	public static final String FILE_PATH = "FILE_PATH";
	public static final String FILE_NAME = "FILE_NAME";

	/*
	 * ASYNC RESPONSE関連
	 */

	public static final String SUCCESS_FLAG = "SUCCESS_FLAG";
	public static final String URL_FOR_REDIRECT = "URL_FOR_REDIRECT";
	public static final String MSG_FOR_ALERT = "MSG_FOR_ALERT";

	/*
	 * 基本メッセージ
	 */
	public static final String saveOk = "N000";
	public static final String modifyOk = "N001";
	public static final String deleteOk = "N002";
	public static final String error = "E999";

	// 経路情報はヘッダ名は「IW_ROUTE」
	public static final String HTTP_IW_ROUTE = ApplicationProperty.get("master.key.route");

	// IssuerのDNをヘッダ「IW_SSL_CLIENT_I_DN」で送信
	public static final String IW_SSL_CLIENT_I_DN = ApplicationProperty.get("master.key.ssl-cli-i-dn");

	// IssuerのDNをヘッダ「IW_SSL_CLIENT_I_DN_CN」で送信
	public static final String IW_SSL_CLIENT_I_DN_CN = ApplicationProperty.get("master.key.ssl-cli-i-dn-cn");

	// IssuerのOをヘッダ「IW_SSL_CLIENT_I_DN_O」で送信
	public static final String IW_SSL_CLIENT_I_DN_O = ApplicationProperty.get("master.key.ssl-cli-i-dn-o");

	// SubjectのDNをヘッダ「IW_SSL_CLIENT_S_DN」で送信
	public static final String IW_SSL_CLIENT_S_DN = ApplicationProperty.get("master.key.ssl-cli-s-dn");

	// SubjectのCNをヘッダ「IW_SSL_CLIENT_S_DN_CN」で送信
	public static final String SSL_CLIENT_S_DN_CN = ApplicationProperty.get("master.key.ssl-cli-s-dn-cn");

	// クライアント証明書の認証結果をヘッダ「IW_SSL_CLIENT_VERIFY」で送信
	public static final String SSL_CLIENT_VERIFY = ApplicationProperty.get("master.key.ssl-cli-verify");

	// SSOログインしたIDは「IW_UID」で送信
	public static final String HTTP_UID = ApplicationProperty.get("master.key.uid");

	// SSOセッションIDは［IW_SESSION］で送信
	public static final String IW_SESSION = ApplicationProperty.get("master.key.session");

	/**
	 * メールパスワード
	 */
	public static final String HTTP_IW_MAILPASS = "HTTP_IW_MAILPASS";

	/*
	 * USER DETAILS関連
	 */
	/**
	 * 実ログインID
	 */
	public static final String LOGIN_ID = "LOGIN_ID";
	/**
	 * 現在ユーザーID
	 */
	public static final String USER_ID = "USER_ID";
	public static final String USER_NM = "USER_NM";
	public static final String USER_TYPE = "USER_TYPE";
	/**
	 * 代行者ID
	 */
	public static final String AGENT_ID = "AGENT_ID";
	/**
	 * 県コード
	 */
	public static final String PRFC_CD = "PRFC_CD";
	/**
	 * 学校ID
	 */
	public static final String SCH_CD = "SCH_CD";
	/**
	 * ユーザーIP
	 */
	public static final String USER_IP = "USER_IP";

	public static final String LOGIN_IP = "LOGIN_IP";
	/**
	 * STAFF ID
	 */
	public static final String AID = "AID";

/* Lim delete 2015/01/19
	public static final String AUTH_CODE = "AUTHORITY_CODE";
*/
	public static final String SCHOOL_TYPE = "SCHOOL_TYPE";

	public static final String YEAR = "YEAR";


	public static final String CURRICULUM_CODE = "CURRICULUM_CODE";
	public static final String LECTURE_CODE = "LECTURE_CODE";
	public static final String TEACH_ID = "TEACH_ID";
	public static final String GRADE_CODE = "GRADE_CODE";

	public static final String SCHEDULE_DATE = "SCHEDULE_DATE";
	public static final String PERIOD_ID = "PERIOD_ID";
	public static final String GRADE_CLASS_ID = "GRADE_CLASS_ID";
	public static final String PLURAL_CLASS_ID = "PLURAL_CLASS_ID";


	// @RequestParam 에 들어갈 강좌 정보. 중복방지를 위해 PREFIX 붙임.
	public static final String GLOBAL_CURRICULUM_CODE = "GLOBAL_CURRICULUM_CODE";
	public static final String GLOBAL_LECTURE_CODE = "GLOBAL_LECTURE_CODE";
	public static final String GLOBAL_TEACH_ID = "GLOBAL_TEACH_ID";
	public static final String GLOBAL_GRADE_CODE = "GLOBAL_GRADE_CODE";

	public static final String GLOBAL_SCHEDULE_DATE = "GLOBAL_SCHEDULE_DATE";
	public static final String GLOBAL_PERIOD_ID = "GLOBAL_PERIOD_ID";
	public static final String GLOBAL_GRADE_CLASS_ID = "GLOBAL_GRADE_CLASS_ID";
	public static final String GLOBAL_PLURAL_CLASS_ID = "GLOBAL_PLURAL_CLASS_ID";

	public static final String GLOBAL_TEACH_PLAN_INFO = "GLOBAL_TEACH_PLAN_INFO";
	/*
	 * ROLE関連
	 */
	// 선생
	public static final String ROLE_TEACHER = "ROLE_TEACHER";
	// 학생
	public static final String ROLE_STUDENT = "ROLE_STUDENT";

	////////////////
	// 논리 ROLE
	////////////////

	// 강의 담당 선생
	public static final String ROLE_TEACHER_IN_CHARGE = "ROLE_TEACHER_IN_CHARGE";
	// 글쓴이
	public static final String ROLE_DOCUMENT_OWNER = "ROLE_DOCUMENT_OWNER";
	// 과거년도 여부
	public static final String BEFORE_YEAR = "BEFORE_YEAR";

	/*
	 * 認証関連
	 */
	public static final String TOO_MANY_USER_RESULTS_EXCEPTION = "F008";
	public static final String JXLS_RESULT = "JXLS_RESULT";

	public static final String PAGE_SIZE = "pageSize";



	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	// ↓↓↓↓教材ばる～んのコンスタント定義　↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	//
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////

	//コードID（ポータル）
	public static final String CODE_PORTAL_GRADE			= "0018";	//学年
	//コードID（KLB）
	public static final String CODE_KLB_AUTHORITY			= "K001";	//権限
	public static final String CODE_KLB_OPEN_CONDITION		= "K002";	//公開状況
	public static final String CODE_KLB_LIBRARY_TYPE		= "K003";	//教材登録区分
	public static final String CODE_KLB_REGISTER_LIST_SORT	= "K004";	//登録教材リストソート順
	public static final String CODE_KLB_CATEGORY_TYPE_SORT	= "K005";	//学年別カテゴリソート順
	public static final String CODE_KLB_OPENED_PERIOD_SORT	= "K006";	//公開開始期間ソート順
	public static final String CODE_KLB_ANALYZE_TYPE		= "K007";	//統計情報区分

	//フラグ状態
	public static final String FLAG_ON	= "1";
	public static final String FLAG_OFF	= "0";

	//教材情報登録方法
	public static final String LIBRARY_TYPE_FILE	= "01";		//ファイル添付
	public static final String LIBRARY_TYPE_LINK	= "02";		//リンク先入力

	//登録処理状態
	public static final String REGIST_STATUS_INSERT		= "insert";	//教材新規登録
	public static final String REGIST_STATUS_UPDATE		= "update";	//教材情報修正
	public static final String REGIST_STATUS_MULTI		= "multi";	//一括新規登録
	public static final String REGIST_STATUS_FILE_READ	= "fileRead";	//ファイル読込

	//排他状態
	public static final String USE_STATUS_DOWNLOAD	= "download";	//教材ダウンロード中
	public static final String USE_STATUS_UPDATE	= "update";		//教材修正中
	public static final String USE_STATUS_DELETE	= "delete";		//教材削除中


	/* ユーザの権限コード */
	public static final String AUTH_CODE = "AUTH_CODE";
	// "01"管理者、
	public static final String AUTH_CODE_ADMIN = "01";
	// "02" 教育事務所
	public static final String AUTH_CODE_OFFICE = "02";
	// "03" 教育委員会
	public static final String AUTH_CODE_COMMIT = "03";
	// "05" 教職員、
	public static final String AUTH_CODE_TEACHER = "05";
	// "06" 学生
	public static final String AUTH_CODE_STUDENT = "06";

	/*  学校種別*/
	// 小学校
	public static final String SCHOOL_TYPE_ELEMENT = "01";
	// 中学校
	public static final String SCHOOL_TYPE_MIDDLE = "02";
	// 高等学校
	public static final String SCHOOL_TYPE_HIGH = "03";
	// 特別支援
	public static final String SCHOOL_TYPE_SPECIAL = "04";
	// 教育事務所
	public static final String SCHOOL_TYPE_OFFICE = "06";
	// 県教育委員会
	public static final String SCHOOL_TYPE_KEN_COMMIT = "07";
	// 市町教育委員会
	public static final String SCHOOL_TYPE_CITY_COMMIT = "08";

	/* 学種別カテゴリソート : 親カテゴリIDをDESC*/
	public static final String PARENT_CATEGORY_DESC = "PARENT_CATEGORY_DESC";


	/* 画面ID（画面遷移用）*/
	// メイン画面ID
	public static final String GAMEN_ID_MAIN = "KLB001";
	// 検索画面ID
	public static final String GAMEN_ID_SEARCH = "KLB111";
	// 教材一覧画面ID
	public static final String GAMEN_ID_LIBRARY_LIST = "KLB112";
	// 教材詳細画面ID
	public static final String GAMEN_ID_LIBRARY_DETAIL = "KLB113";
	// 新しい教材画面ID
	public static final String GAMEN_ID_NEW_LIBRARY = "KLB121";
	// 注目の教材画面ID
	public static final String GAMEN_ID_PICKUP_LIBRARY = "KLB131";
	// よく利用されている教材画面ID
	public static final String GAMEN_ID_DOWNLOAD_LIBRARY = "KLB141";
	// あなたが今まで見た教材画面ID
	public static final String GAMEN_ID_HISTORY_LIBRARY = "KLB151";
	// 登録教材一覧画面ID
	public static final String GAMEN_ID_REGISTER_LIST = "KLB211";
	// 教材登録画面ID
	public static final String GAMEN_ID_REGISTER = "KLB221";
	// 教材一括登録画面ID
	public static final String GAMEN_ID_REGISTER_BINARY = "KLB231";
	// 教材一括登録画面ID
	public static final String GAMEN_ID_ANALYZE = "KLB311";
	// 管理者一覧画面ID
	public static final String GAMEN_ID_DELETE_MANAGER = "KLB411";
	// 管理者登録画面ID
	public static final String GAMEN_ID_REGISTER_MANAGER = "KLB421";


}
