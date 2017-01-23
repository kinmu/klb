package jp.go.saga.security;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

public class CommonUser extends User{

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private Map<String,Object> userDetails;
	private List<Map<String,Object>> affiliatedSchoolList;
	
	public CommonUser(String username, Collection<? extends GrantedAuthority> authorities,Map<String,Object> userDetails,List<Map<String,Object>> affiliatedSchoolList) {
		// パスワードは不要だが、設定しないと、エラーが発生する。 
		super(username, "dummy", authorities);
		this.userDetails = userDetails;
		this.affiliatedSchoolList = affiliatedSchoolList;
	}

	public String getName(){
		return StringUtils.isEmpty(super.getUsername()) ? null : super.getUsername();
	}

	/**
	 * USER DETAILS (EX: ユーザー、姓名などの基本情報)
	 * @return
	 */
	public Map<String, Object> getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(Map<String, Object> userDetails) {
		this.userDetails = userDetails;
	}
	
	/**
	 * 所属学校リスト(SCH_CD：学校ID、SCHOOL_NAME：学校名)
	 * @return
	 */
	public List<Map<String, Object>> getAffiliatedSchoolList() {
		return affiliatedSchoolList;
	}

	public void setAffiliatedSchoolList(List<Map<String, Object>> affiliatedSchoolList) {
		this.affiliatedSchoolList = affiliatedSchoolList;
	}
}