package com.quantumdataengines.app.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationModule {
	
	public String name();
	public String description();
	//	URL will be used only for those modules which needs to be opened in a page.
	//	URL must be same as the request mapping.
	public String url() default "";
	public boolean isLinkEnabled() default false;
	public String icon() default "";
	public String graphType() default "";
	public boolean isEnabled() default true;
	public boolean isDefaultModule() default false;
	public int order() default 0;
	public String presentationCategory() default "";
}
