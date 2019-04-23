package com.lovo.hospital.util;


import com.lovo.hospital.entity.UserEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;



@Aspect
public class LogAspect {

    public Integer id=null;


    /**
     * 管理员登录方法的切入点
     */
    @Pointcut("execution(* com.lovo.hospital.service.impl.*.login(..))")
    public void loginCell(){
    }

    /**
     * 添加业务逻辑方法切入点
     */
    @Pointcut("execution(* com.lovo.hospital.service.*.save(..))")
    public void insertCell() {
    }

    /**
     * 修改业务逻辑方法切入点
     */
    @Pointcut("execution(* com.lovo.hospital.service.*.update(..))")
    public void updateCell() {
    }

    /**
     * 删除业务逻辑方法切入点
     */
    @Pointcut("execution(* com.lovo.hospital.service.*.delete(..))")
    public void deleteCell() {
    }

    /**
     * 登录操作(后置通知)
     * @param joinPoint
     * @param object
     * @throws Throwable
     */
    @AfterReturning(value = "loginCell()",    returning = "object")
    public void loginLog(JoinPoint joinPoint, Object object) throws Throwable {
        UserEntity user=(UserEntity)object;
        if (user==null) {
            return;
        }
        if (joinPoint.getArgs() == null) {// 没有参数
            return;
        }
//         String username=user.getUsername();
        // 获取方法名
//        String methodName = joinPoint.getSignature().getName();
        // 获取操作内容
//        String opContent = optionContent(joinPoint.getArgs(), methodName);

//        LogEntity log = new LogEntity();

//        logService.insertLog(log);

//        System.out.println(username);
    }



}
