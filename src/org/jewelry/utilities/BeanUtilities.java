package org.jewelry.utilities;

import java.util.*;
import javax.servlet.http.*;
import org.apache.commons.beanutils.BeanUtils;

public class BeanUtilities {
	
	public static void populateBean(Object formBean, HttpServletRequest request){
		populateBean(formBean, request.getParameterMap());
	}
	
	/** Populates a bean based on a Map: Map keys are the
	* bean property names; Map values are the bean property
	* values. Type conversion is performed automatically as
	* described above.
	*/
	
	public static void populateBean(Object bean, Map propertyMap){
		try {
			BeanUtils.populate(bean, propertyMap);
			} catch(Exception e) {
			// Empty catch. The two possible exceptions are
			// java.lang.IllegalAccessException and
			// java.lang.reflect.InvocationTargetException.
			// In both cases, just skip the bean operation.
			}
	}

}
