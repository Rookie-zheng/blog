package com.zheng.blog.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.cache.Cache;

import javax.servlet.http.HttpServletRequest;


@Aspect
@Component
public class NoRepeatSubmitAop{

    private Log logger = LogFactory.getLog(getClass());

    @Autowired
    private Cache<String, Integer> cache;

    @Around("execution(* com.zheng.blog.web.admin..*Controller.*(..)) && @annotation(nrs)")
    public Object arround(ProceedingJoinPoint pjp, NoRepeatSubmit nrs) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
            HttpServletRequest request = attributes.getRequest();
            String key = sessionId + "-" + request.getServletPath();
            System.out.println(sessionId+"-------------------------------");
            if (cache.getIfPresent(key) == null) {// 如果缓存中有这个url视为重复提交
                Object o = pjp.proceed();
                cache.put(key, 0);
                return o;
            } else {
                logger.error("重复提交");
                return pjp.proceed(); //"redirect:/admin/types"
            }
        } catch (Throwable e) {
            e.printStackTrace();
            logger.error("验证重复提交时出现未知异常!");
            return "{\"code\":-889,\"message\":\"验证重复提交时出现未知异常!\"}";
        }

    }
}
