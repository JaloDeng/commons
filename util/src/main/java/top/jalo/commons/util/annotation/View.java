package top.jalo.commons.util.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation : Set the view name for the controller.
 * 
 * @author JALO
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface View {
	String value() default "";
}
