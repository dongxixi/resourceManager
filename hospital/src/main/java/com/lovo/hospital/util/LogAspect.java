package com.lovo.hospital.util;


import com.lovo.hospital.entity.CarEntity;
import com.lovo.hospital.entity.LogEntity;
import com.lovo.hospital.entity.PersonnelEntity;
import com.lovo.hospital.entity.UserEntity;
import com.lovo.hospital.service.CarService;
import com.lovo.hospital.service.ILogService;
import com.lovo.hospital.service.IUserService;
import com.lovo.hospital.service.PersonnelService;
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
    @Autowired
    private IUserService userService;
    @Autowired
    private PersonnelService personnelService;

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
        addlogin("添加"+log);
    }

    /**
     *修改日志操作（环绕通知）
     * @param joinPoint
     * @throws Throwable
     */
    @Around(value = "updateCell()")
    public Object updateLog(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object proceed=null;
        String description=null;
        Object[] args = joinPoint.getArgs();
        if(methodName.contains("Car")){
            //修改之前的对象
            CarEntity carEntity=null;
            //修改以后的对象
            CarEntity s = (CarEntity)args[0];
            carEntity = carService.infoCarById(s.getId());
            proceed  = joinPoint.proceed();
            //拼接日志信息
            description="修改前:"+carEntity.getDriver()+"修改后:"+s.getDriver();
        }else if(methodName.contains("User")){
            String id=args[0].toString();
            UserEntity user = userService.findUserById(id);
            String userName = user.getUserName();
            description="修改前:"+userName+"修改后:"+args[1].toString();
        }else if(methodName.contains("Person")){
            //修改之前的对象
            PersonnelEntity personnelEntity=null;
            //修改以后的对象
            PersonnelEntity p = (PersonnelEntity)args[0];
            personnelEntity = personnelService.selectOnePerson(p.getId());
            proceed  = joinPoint.proceed();
            //拼接日志信息
            description="修改前:"+personnelEntity.getName()+"修改后:"+personnelEntity.getName();
        }

        addlogin(description);
        return proceed;
    }


    /**
     *删除日志操作（环绕通知）
     * @param joinPoint
     * @throws Throwable
     */
    @Around(value = "deleteCell()")
    public Object deleteLog(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object proceed=null;
        String description=null;
        Object[] args = joinPoint.getArgs();
        if(methodName.contains("Car")){
            //删除之前的对象
            CarEntity carEntity=null;
            CarEntity s = (CarEntity)args[0];
            carEntity = carService.infoCarById(s.getId());
            proceed  = joinPoint.proceed();
            description="删除车辆:"+carEntity.getCarNum();
        }else if(methodName.contains("User")){
            //获取删除前的ID
            String id=args[0].toString();
            UserEntity user = userService.findUserById(id);
            String userName = user.getUserName();
            proceed  = joinPoint.proceed();
            description="删除用户:"+userName;
        }else if(methodName.contains("Person")){
            //获取删除前的ID
            String id=args[0].toString();
            PersonnelEntity personnelEntity = personnelService.selectOnePerson(id);
            String personnelName = personnelEntity.getName();
            proceed  = joinPoint.proceed();
            description="删除人员:"+personnelName;
        }

        addlogin(description);
        return proceed;
    }


    /**
     *  获取增加的内容
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
            description="车牌"+car.getCarNum();
        }else if(methodName.contains("User")){
            Object[] args = joinPoint.getArgs();
            String username=args[0].toString();
            description="新增用户名:"+username;
        }else if(methodName.contains("Personnel")){
            Object[] args = joinPoint.getArgs();
            String name=args[0].toString();
            description="新增人员名:"+name;
        }
        return description;
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

}
