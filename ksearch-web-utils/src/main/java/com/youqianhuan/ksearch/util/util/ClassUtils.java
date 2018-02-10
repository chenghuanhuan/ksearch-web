/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.util.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.*;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年08月10日 下午3:19 $
 */
public class ClassUtils {
    private static Logger logger = LoggerFactory.getLogger(ClassUtils.class);
    /**
     * 获取某个类锁指定的泛型参数数组
     *
     * @param c
     *
     * @return
     */
    public final static Type[] getGenericTypes(Class<?> c) {

        Class<?> myClass = c;

        if (!(c.getGenericSuperclass() instanceof ParameterizedType)) {
            myClass = c.getSuperclass();
        }

        Type superClass = myClass.getGenericSuperclass();
        ParameterizedType type = (ParameterizedType) superClass;
        return type.getActualTypeArguments();
    }

    /**
     * 获取一个类的所有字段
     *
     * @param entityClass
     *
     * @return
     */
    public static Set<Field> getAllFiled(Class<?> entityClass) {

        // 获取本类的所有字段
        Set<Field> fs = new HashSet<Field>();
        for (Field f : entityClass.getFields()) {
            fs.add(f);
        }
        for (Field f : entityClass.getDeclaredFields()) {
            //System.out.println(f);
            fs.add(f);
        }

        // 递归获取父类的所有字段
        Class<?> superClass = entityClass.getSuperclass();
        if (!superClass.equals(Object.class)) {
            Set<Field> superFileds = getAllFiled(superClass);
            fs.addAll(superFileds);
        }

        return fs;
    }

    public static Set<Field> getThisClassFiled(Class<?> entityClass) {

        // 获取本类的所有字段
        Set<Field> fs = new HashSet<Field>();
        for (Field f : entityClass.getFields()) {
            fs.add(f);
        }
        for (Field f : entityClass.getDeclaredFields()) {
            System.out.println(f);
            fs.add(f);
        }
        return fs;
    }

    /**
     * 获取一个类的所有方法
     *
     * @param entityClass
     *
     * @return
     */
    public static Set<Method> getAllMethod(Class<?> entityClass) {

        // 获取本类的所有的方法
        Set<Method> ms = new HashSet<Method>();
        for (Method m : entityClass.getMethods()) {
            ms.add(m);
        }
        for (Method m : entityClass.getDeclaredMethods()) {
            ms.add(m);
        }

        // 递归获取父类的所有方法
        Class<?> superClass = entityClass.getSuperclass();
        if (!superClass.equals(Object.class)) {
            Set<Method> superFileds = getAllMethod(superClass);
            ms.addAll(superFileds);
        }

        return ms;
    }



    /**
     * 将from的属性copy到to中
     *
     * @param from
     * @param to   下午4:30:58 created by Darwin(Tianxin)
     */
    public final static void copyProperties(Object from, Object to) {

        Set<Field> fromSet = getAllFiled(from.getClass());
        Set<Field> toSet = getAllFiled(to.getClass());

        Map<String, Field> toMap = new HashMap<String, Field>();
        for (Field f : toSet) {
            toMap.put(f.getName(), f);
        }

        for (Field f : fromSet) {
            if (Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            String name = f.getName();
            Field toField = toMap.get(name);
            if (toField == null) {
                continue;
            }

            toField.setAccessible(true);
            f.setAccessible(true);
            try {
                toField.set(to, f.get(from));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取一个类的field
     *
     * @param field
     * @param clazz
     *
     * @return 下午3:01:19 created by Darwin(Tianxin)
     */
    public static Field getFieldFromClass(String field, Class<? extends Object> clazz) {
        try {
            return clazz.getDeclaredField(field);
        } catch (Exception e) {
            try {
                return clazz.getField(field);
            } catch (Exception ex) {
            }
        }
        return null;
    }


    /**
     * 循环向上转型, 获取对象的 DeclaredMethod
     * @param object : 子类对象
     * @param methodName : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @return 父类中的方法对象
     */

    public static Method getDeclaredMethod(Object object, String methodName, Class<?> ... parameterTypes){
        Method method = null ;

        for(Class<?> clazz = object.getClass() ; clazz != Object.class ; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes) ;
                return method ;
            } catch (NoSuchMethodException e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
                logger.error("getDeclaredMethod error!",e);
            }
        }

        return null;
    }

    /**
     * 直接调用对象方法, 而忽略修饰符(private, protected, default)
     * @param object : 子类对象
     * @param methodName : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @param parameters : 父类中的方法参数
     * @return 父类中方法的执行结果
     */

    public static Object invokeMethod(Object object, String methodName, Class<?> [] parameterTypes,
                                      Object [] parameters) {
        //根据 对象、方法名和对应的方法参数 通过反射 调用上面的方法获取 Method 对象
        Method method = getDeclaredMethod(object, methodName, parameterTypes) ;



        try {
            if(method != null) {
                //抑制Java对方法进行检查,主要是针对私有方法而言
                method.setAccessible(true) ;

                //调用object 的 method 所代表的方法，其方法的参数是 parameters
                return method.invoke(object, parameters) ;
            }
        } catch (Exception e) {
            logger.error("invokeMethod error!",e);
        }

        return null;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     * @param object : 子类对象
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */

    public static Field getDeclaredField(Object object, String fieldName){
        Field field = null ;

        Class<?> clazz = object.getClass() ;

        for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName) ;
                return field ;
            } catch (NoSuchFieldException e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
                //logger.error("getDeclaredField error!",e);
            }
        }

        return null;
    }

    /**
     * 获取所有属性包含父类属性
     * @param clazz
     * @return
     */
    public static Field[] getDeclaredFields(Class<?> clazz){
        Field [] result ;
        List<Field> fieldList = new ArrayList<>();
        for (;clazz !=Object.class;clazz = clazz.getSuperclass()){

            Field [] fields1 = clazz.getDeclaredFields();
            if (fields1.length>0){
                fieldList.addAll(Arrays.asList(fields1));
            }
        }
        result = fieldList.toArray(new Field[]{});
        return result;
    }

    /**
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
     * @param object : 子类对象
     * @param fieldName : 父类中的属性名
     * @param value : 将要设置的值
     */

    public static void setFieldValue(Object object, String fieldName, Object value){

        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object, fieldName) ;

        //抑制Java对其的检查
        field.setAccessible(true) ;

        try {
            //将 object 中 field 所代表的值 设置为 value
            field.set(object, value) ;
        } catch (IllegalAccessException e) {
            logger.error("setFieldValue error!",e);
        }

    }

    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     * @param object : 子类对象
     * @param fieldName : 父类中的属性名
     * @return : 父类中的属性值
     */

    public static Object getFieldValue(Object object, String fieldName){

        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object, fieldName) ;

        if (field != null){
            //抑制Java对其的检查
            field.setAccessible(true) ;

            try {
                //获取 object 中 field 所代表的属性值
                return field.get(object) ;

            } catch(IllegalAccessException e) {
                //logger.error("getFieldValue error!",e);
            }
        }


        return null;
    }

    /**
     * 判断是否为基本类型或者包装类型
     * 包含 String
     * @param clazz
     * @return
     */
    public static boolean isPrimitive(Class<?> clazz){
        if (clazz.isPrimitive()){
            return clazz.isPrimitive();
        }else if(clazz == String.class){
            return true;
        }else {

            return isWrapClass(clazz);
        }
    }

    /**
     * 判断是否为基本类型的包装类型
     * @param clz
     * @return
     */
    public static boolean isWrapClass(Class clz) {
        try {
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

}
