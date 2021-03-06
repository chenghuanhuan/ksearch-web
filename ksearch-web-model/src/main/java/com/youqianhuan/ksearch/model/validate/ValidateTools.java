package com.youqianhuan.ksearch.model.validate;

import com.youqianhuan.ksearch.util.util.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参数较验器<br>
 * 用法：需要较验的属性上用@validate()作注解，然后调用ValidateTools.validate(object)做较验。<br>
 * <p>
 *	step 1:<br>
 *  	\\@Validate(required=true, maxValue="1", minValue="3")<br>
 * 		private Integer status;<br>
 * </p>
 * <p>
 *  step 2:<br>
 * 		String errorMsg = ValidateTools.validate(object);<br>
 * 		if (errorMsg != null) {<br>
 * 			log.error(errorMsg);<br>
 * 		}<br>
 * </p>
 * @author chenghuanhuan
 */
public class ValidateTools {

	private static Map<Class<?>, List<FieldValidator>> classValidators = new ConcurrentHashMap<Class<?>, List<FieldValidator>>();
	
    /**
     * 验证对象的属性值是否符合注解的要求，
     * 如果符合要求返回null，否则返回错误信息。
     * 
     * @param req 对像
     * @return 错误信息，成功返回null
     * @throws ParseException 类的注解格式错误，或值转成
     * @throws IllegalAccessException
     */
    public static String validate(Object req) throws ParseException, IllegalAccessException {
    	String errorMsg = null;
    	if (req instanceof List<?>) {
    		for (Object value : (List<?>)req) {
    			errorMsg = validateClass(value); 
    			if (errorMsg != null) {
    				return errorMsg;
    			}
    		}
    	} else if (req instanceof Map<?, ?>) {
    		for (Object value : ((Map<?, ?>)req).values()) {
    			errorMsg = validateClass(value); 
    			if (errorMsg != null) {
    				return errorMsg;
    			}
    		}
    	} else {
    		errorMsg = validateClass(req);
    	}
    	
    	return errorMsg;
    }
    
    private static String validateClass(Object req) throws ParseException, IllegalAccessException {
    	List<FieldValidator> validators = classValidators.get(req.getClass());
    	if (validators == null) {
    		validators = initClassValidators(req.getClass());
    	}

    	for (FieldValidator fieldValidator : validators) {
    		String errorMsg = validField(req, fieldValidator);
    		if (errorMsg != null) {
    			return errorMsg;
    		}
    	}
        return null;
    }
    
    private static synchronized List<FieldValidator> initClassValidators(Class<?> clazz) throws ParseException {
    	List<FieldValidator> validators = classValidators.get(clazz);
    	if (validators != null) {
    		return validators;
    	}
    	
    	validators = new ArrayList<FieldValidator>();
    	initClassValidators(clazz, validators); 
    	classValidators.put(clazz, validators);
    	
    	return validators;
    }

    private static void initClassValidators(Class<?> clazz, List<FieldValidator> validators) throws ParseException {
    	 Field[] fields = clazz.getDeclaredFields();
         for (Field field : fields) {
         	if (field.isAnnotationPresent(Validate.class)) {	// 只处理包含注解为Validate的属性名称
                 Validate validate = field.getAnnotation(Validate.class);
                 FieldValidator fieldValidator = new FieldValidator(validate, field);
                 if (!validators.contains(fieldValidator)) {	// 优先使用子类的field
                	 validators.add(fieldValidator);
                 }
         	}
         }
         
	     // 父类字段
	     if (clazz.getGenericSuperclass() != null) {
	         initClassValidators(clazz.getSuperclass(), validators); // 处理父类字段
	     }
    }
    
    private static String validField(Object req, FieldValidator fieldValidator) throws IllegalAccessException {
        Validate validate = fieldValidator.getValidate();
        Field field = fieldValidator.getField();
        Object value = FieldUtils.readField(field, req, true);
        //String name = req.getClass().getSimpleName() + "." + field.getName();
		String name = field.getName();
		String desc = validate.desc();
		if ("".equals(desc)){
			desc = name;
		}
        // 设置默认值
        if (value == null && fieldValidator.getDefaultValue() != null) {
        	FieldUtils.writeField(field, req, fieldValidator.getDefaultValue(), true);
        	value = fieldValidator.getDefaultValue();
        }
                
    	// 较验字符串对象是否为blank
    	if (validate.isNotBlank() && value instanceof String && StringUtils.isBlank((String)value)) {
        	return "parameter[" + desc + "] can not blank";
        }
    	
    	// 当对象为空时，如果不是必传参数，则后面的校验不用做。
    	if (value == null) {
    		if (validate.required()) {
    			return "parameter[" + desc + "] is required";
    		} else {
    			return null;
    		}
    	} 

    	// 较验最大长度
        if (validate.maxLength() > 0 && (value instanceof String || value instanceof List<?> || value.getClass().isArray())) {
        	if (value instanceof String && ((String)value).length() > validate.maxLength()) {
    			return "parameter[" + desc + "] length[" + ((String)value).length() + "] is greater than " + validate.maxLength();
        	}
        	if (value instanceof List<?> && ((List<?>)value).size() > validate.maxLength()) {
        		return "parameter[" + desc + "] size[" + ((List<?>)value).size() + "] is greater than " + validate.maxLength();
        	}
        	if (value.getClass().isArray() && Array.getLength(value) > validate.maxLength()) {
        		return "parameter[" + desc + "] length[" + Array.getLength(value) + "] is greater than " + validate.maxLength();
        	}
        }
        
        // 较验最小长度
        if (validate.minLength() >0 && (value instanceof String || value instanceof List<?> || value.getClass().isArray())) {
        	if (value instanceof String && ((String)value).length() < validate.minLength()) {
    			return "parameter[" + desc + "] length[" + ((String)value).length() + "] is less than " + validate.minLength();
        	}
        	if (value instanceof List<?> && ((List<?>)value).size() < validate.minLength()) {
        		return "parameter[" + desc + "] size[" + ((List<?>)value).size() + "] is less than " + validate.minLength();
        	}
        	if (value.getClass().isArray() && Array.getLength(value) < validate.minLength()) {
        		return "parameter[" + desc + "] length[" + Array.getLength(value) + "] is less than " + validate.minLength();
        	}
        }
        
        // 最大值校验，只较验数值类型
        if (fieldValidator.getMaxValue() != null && value instanceof Number && ((Number)value).doubleValue() > fieldValidator.getMaxValue().doubleValue()) {
			return "parameter[" + desc + "] value[" + value + "] is greater than " + validate.maxValue();
        }
        
        // 最小值校验，只较验数值类型
        if (fieldValidator.getMinValue() != null && value instanceof Number && ((Number)value).doubleValue() < fieldValidator.getMinValue().doubleValue()) {
			return "parameter[" + desc + "] value[" + value + "] is less than " + validate.minValue();
        }
        
        if (fieldValidator.getRegexpPattern() != null && value instanceof String) {
        	Matcher matcher = fieldValidator.getRegexpPattern().matcher((String)value);
        	if (!matcher.matches()) {
        		return "parameter[" + desc + "] value[" + value + "] format error, must regexp:" + validate.regexp();
        	}
        }
        
        if (fieldValidator.getTypePattern() != null && value instanceof String) {
        	Matcher matcher = fieldValidator.getTypePattern().matcher((String)value);
        	if (!matcher.matches()) {
        		return "parameter[" + desc + "] value[" + value + "] format error, must be " + validate.type();
        	}
        }
        
        if (fieldValidator.getEnumValues() != null && !fieldValidator.getEnumValues().contains(value.toString())) {
    		return "parameter[" + desc + "] value[" + value + "] format error, must in " + fieldValidator.getEnumValues();
        }

        // Collection 类型校验
		if (value instanceof Collection && fieldValidator.getRegexpPattern() != null){
			Type fc = field.getGenericType();
			if(fc instanceof ParameterizedType) // 如果是泛型参数的类型
			{
				ParameterizedType pt = (ParameterizedType) fc;
				Class genericClazz = (Class)pt.getActualTypeArguments()[0]; //得到泛型里的class类型对象。
				if (genericClazz == String.class){
					for (String val:(Collection<String>)value){
						Matcher matcher = fieldValidator.getRegexpPattern().matcher(val);
						if (!matcher.matches()) {
							return "parameter[" + desc + "] value[" + val + "] format error, must regexp:" + validate.regexp();
						}
					}
				}
			}
		}
		return null;
    }

	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("^(?!_)[a-zA-Z0-9_]+");
		Matcher matcher = pattern.matcher("1z_ssdd_dd23");
		System.out.println(matcher.matches());
		List<String> list = new ArrayList<>();
		System.out.println(list instanceof Collection);
		//System.out.println(ClassUtils.getGenericTypes(list.getClass()));
		Type [] types = ClassUtils.getGenericTypes(list.getClass());
		System.out.println(types);
	}

}
