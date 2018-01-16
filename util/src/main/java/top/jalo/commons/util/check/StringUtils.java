package top.jalo.commons.util.check;

/**
 *
 * @Author JALO
 *
 */
public class StringUtils {

	public static void isViewNameBlank(String viewName) throws Exception {
		if ("".equals(viewName) || viewName == "" || viewName == null) {
			throw new Exception("View's name can out be null.");
		}
	}
	
}
