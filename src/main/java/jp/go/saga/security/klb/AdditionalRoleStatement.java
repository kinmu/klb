package jp.go.saga.security.klb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdditionalRoleStatement {
	/**
	 * ibatis query statement name. Query result must be Integer.
	 * @return
	 */
	public String value();
}
