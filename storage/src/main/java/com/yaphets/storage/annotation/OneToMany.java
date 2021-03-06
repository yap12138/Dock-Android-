package com.yaphets.storage.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Deprecated
@Retention(RUNTIME)
@Target(FIELD)
public @interface OneToMany {
	/**
	 * 	当前表主键列名
	 * @return
	 * 	主键列名
	 */
	String name();
	
	/**
	 * 	引用表的外键列名
	 * @return
	 * 	引用表的外键名
	 */
	String mappedBy();
	
	Class<?> referedEntity();
}
