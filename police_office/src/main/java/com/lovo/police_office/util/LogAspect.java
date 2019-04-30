package com.lovo.police_office.util;



import com.lovo.police_office.entity.CarEntity;
import com.lovo.police_office.entity.LogEntity;
import com.lovo.police_office.entity.PersonnelEntity;
import com.lovo.police_office.entity.UserEntity;
import com.lovo.police_office.service.CarService;
import com.lovo.police_office.service.ILogService;
import com.lovo.police_office.service.IUserService;
import com.lovo.police_office.service.PersonnelService;
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
        if (object==null) {//没有返回对象
            return;
        }
        //获取添加的信息
        String log = getLog(joinPoint, object);
        //添加日志
        if(log!=null){
            addlogin("添加"+log);
        }
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
            CarEntity oldCarEntity=new CarEntity();
            CarEntity s = (CarEntity)args[0];
            String id=s.getId();
            s = carService.infoCarById(id);
            String id1 = s.getId();
            String carNum = s.getCarNum();
            String driver = s.getDriver();
            int state = s.getState();
            oldCarEntity.setState(state);
            oldCarEntity.setDriver(driver);
            oldCarEntity.setCarNum(carNum);
            oldCarEntity.setId(id1);
            proceed  = joinPoint.proceed();
            CarEntity newCarEntity = carService.infoCarById(id);
            //拼接日志信息
            Compare<CarEntity> carEntityCompare = new Compare<>();
            description = carEntityCompare.contrastObj(oldCarEntity,newCarEntity);
            if(description!=null&&!"".equals(description)){
                description="修改："+oldCarEntity.getCarNum()+"内容："+description;
                addlogin(description);
            }
        }else if(methodName.contains("User")){
            String id=args[0].toString();
            UserEntity user = userService.findUserById(id);
            UserUtil oldUserEntity = new UserUtil(user.getuId(),user.getUserName(),user.getPassword());
            proceed  = joinPoint.proceed();
            UserEntity UserEntity =userService.findUserById(id);
            UserUtil newUserEntity = new UserUtil(UserEntity.getuId(),UserEntity.getUserName(),UserEntity.getPassword());
            //拼接日志信息
            Compare<UserEntity> userEntityCompare = new Compare<>();
            description = userEntityCompare.contrastObj(oldUserEntity,newUserEntity);
            if(description!=null&&!"".equals(description)){
                description="修改用户id："+newUserEntity.getName()+"内容："+description;
                addlogin(description);
            }
        }else if(methodName.contains("Person")){
            //修改之前的对象
            PersonnelEntity p = (PersonnelEntity)args[0];
            String id=p.getId();
            PersonnelEntity  ps = personnelService.selectOnePerson(id);
            //修改之前的对象
            PersonnelEntity oldPersonnelEntity=new PersonnelEntity();
            oldPersonnelEntity.setId(id);
            oldPersonnelEntity.setName(ps.getName());
            oldPersonnelEntity.setPnum(ps.getPnum());
            oldPersonnelEntity.setPosition(ps.getPosition());
            oldPersonnelEntity.setSex(ps.getSex());
            oldPersonnelEntity.setState(ps.getState());
            oldPersonnelEntity.setTel(ps.getTel());
            oldPersonnelEntity.setWorkTime(ps.getWorkTime());
            proceed  = joinPoint.proceed();
            //修改后的对象
            PersonnelEntity newPersonnelEntity=personnelService.selectOnePerson(id);
            //拼接日志信息
            Compare<UserEntity> userEntityCompare = new Compare<>();
            description = userEntityCompare.contrastObj(oldPersonnelEntity,newPersonnelEntity);
            if(description!=null&&!"".equals(description)){
                description="修改人员id："+id+"内容："+description;
                addlogin(description);
            }
        }

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
            String id= args[0].toString();
            carEntity = carService.infoCarById(id);
            String carNum=carEntity.getCarNum();
            proceed  = joinPoint.proceed();
            description="删除车辆:"+carNum;
            addlogin(description);
        }else if(methodName.contains("User")){
            //获取删除前的ID
            String id=args[0].toString();
            UserEntity user = userService.findUserById(id);
            String userName = user.getUserName();
            proceed  = joinPoint.proceed();
            description="删除用户:"+userName;
            addlogin(description);
        }else if(methodName.contains("Person")){
            //获取删除前的ID
            String id=args[0].toString();
            PersonnelEntity personnelEntity = personnelService.selectOnePerson(id);
            String personnelName = personnelEntity.getName();
            proceed  = joinPoint.proceed();
            description="删除人员:"+personnelName;
            addlogin(description);
        }


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
