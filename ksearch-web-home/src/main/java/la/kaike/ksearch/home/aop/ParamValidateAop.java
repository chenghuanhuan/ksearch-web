/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.aop;

import la.kaike.ksearch.model.Request;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.validate.ValidateTools;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2016年08月16日 下午4:37 $
 */
@Aspect
@Component
public class ParamValidateAop {
    //定义一个切入点
    @Pointcut("execution(* la.kaike.ksearch.home.controller.*.*(..))")
    private void controllerMethod() {
    }

    /**
     * 参数校验
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("controllerMethod()")
    public Object validate(ProceedingJoinPoint pjp) throws Throwable {

        Object[] params = pjp.getArgs();
        if (params != null && params.length >0){
            for (Object param:params){
                if (param instanceof Request){
                    String errorMsg = ValidateTools.validate(param);
                    if (errorMsg != null) {
                        Response obj = new Response();
                        obj.setStatus(false);
                        obj.setData(null);
                        obj.setMsg(errorMsg);
                        return obj;
                    }
                }
            }
        }

        return pjp.proceed();
    }

}
