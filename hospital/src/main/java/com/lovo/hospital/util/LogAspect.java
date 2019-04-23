package com.lovo.hospital.util;


import com.lovo.hospital.entity.CarEntity;
import com.lovo.hospital.entity.LogEntity;
import com.lovo.hospital.entity.UserEntity;
import com.lovo.hospital.service.CarService;
import com.lovo.hospital.service.ILogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


@Aspect
@Component
public class LogAspect {
    private String username;
    @Autowired
    private ILogService logService;
    @Autowired
    private CarService carService;

    /**
     * 管理员登录方法的切入点
     */
    @Pointcut("execution(* com.lovo.hospital.service.*.login*(..))")
    public void loginCell(){
    }

    /**
     * 添加业务逻辑方法切入点
     */
    @Pointcut("execution(* com.lovo.hospital.service.*.save*(..))")
    public void insertCell() {
    }

    /**
     * 修改业务逻辑方法切入点
     */
    @Pointcut("execution(* com.lovo.hospital.service.*.update*(..))")
    public void updateCell() {
    }

    /**
     * 删除业务逻辑方法切入点
     */
    @Pointcut("execution(* com.lovo.hospital.service.*.delete*(..))")
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
        username=user.getUserName();
        //保存日志信息
        String description="登录了";
        addlogin(description);
    }

    /**
     * 添加操作日志(后置通知)
     *
     * @param joinPoint
     * @param object
     */
    @AfterReturning(value = "insertCell()", returning = "object")
    public void insertLog(JoinPoint joinPoint, Object object) throws Throwable {
        // 判断参数
        if (joinPoint.getArgs() == null) {// 没有参数
            return;
        }
        //获取添加的信息
        String log = getLog(joinPoint, object);
        //添加日志
        addlogin("添加了"+log);
    }

    /**
     *修改日志操作（环绕通知）
     * @param joinPoint
     * @throws Throwable
     */
    @Around(value = "updateCell()")
    public Object updateLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //修改之前的对象
        CarEntity carEntity=null;
        Object[] args = joinPoint.getArgs();
        //修改以后的对象
        CarEntity s = (CarEntity)args[0];
        String id = s.getId();
        carEntity = carService.infoCarById(id);
        Object proceed  = joinPoint.proceed();
        String description="修改前:"+carEntity.getDriver()+"修改后:"+s.getDriver();
        addlogin(description);
        return proceed;
    }




    /**
     *
     * @param description 拼接日志详细信息
     * @return  返回新的日志对象
     */
    public LogEntity addlogin(String description){
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        LogEntity log = new LogEntity();
        log.setUsername(username);
        log.setTime(ts);
        log.setDescription(description);
        logService.add(log);
        return log;
    }

    /**
     *
     * @param joinPoint
     * @param object
     * @return 返回操作的相关的信息
     */
    public String getLog(JoinPoint joinPoint,Object object){
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 获取操作内容,判断增加的对象
        String description=null;
        if(methodName.contains("Car")){
            CarEntity car=(CarEntity)object;
            description=car.getCarNum();
            return description;
        }
        return null;
    }

}
