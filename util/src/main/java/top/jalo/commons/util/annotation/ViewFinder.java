package top.jalo.commons.util.annotation;

/**
 * 
 * @author JALO
 *
 */
public class ViewFinder {

	public static String getView(Class<?> clazz) {
		View view = clazz.getAnnotation(View.class);
		return view.value();
	}
	
}
