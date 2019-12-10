package com.paascloud.provider.core.aspect;

import com.paascloud.PubUtils;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.utils.RequestUtil;
import com.paascloud.provider.model.domain.*;
import com.paascloud.provider.model.dto.ImcItemChangeStatusDto;
import com.paascloud.provider.model.dto.ImcTaskChangeStatusDto;
import com.paascloud.provider.service.ImcInspectionTaskLogService;
import com.paascloud.wrapper.Wrapper;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import com.paascloud.provider.core.annotation.AnanLogAnnotation;
import com.paascloud.provider.service.ImcInspectionItemLogService;
import com.paascloud.provider.service.ImcInspectionItemService;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by rongshuai on 2019/12/9 17:42
 */
@Slf4j
@Aspect
@Component
public class AnanLogAspect {

    @Resource
    ImcInspectionTaskLogService imcInspectionTaskLogService;
    @Resource
    ImcInspectionItemLogService imcInspectionItemLogService;

    @Resource
    ImcInspectionItemService imcInspectionItemService;

    private ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    private static final int MAX_SIZE = 2000;

    /**
     * Log annotation.
     */
    @Pointcut("@annotation(com.paascloud.provider.core.annotation.AnanLogAnnotation)")//定义切点
    public void logAnnotation() {
    }

    /**
     * Do before.
     */
    @Before("logAnnotation()")//在切点方法之前执行
    public void doBefore() {
        this.threadLocal.set(new Date(System.currentTimeMillis()));
    }

    /**
     * Do after.
     *
     * @param joinPoint   the join point
     * @param returnValue the return value
     */
    @AfterReturning(pointcut = "logAnnotation()", returning = "returnValue")//在切点方法返回后执行
    public void doAfter(final JoinPoint joinPoint, final Object returnValue) {
        if (returnValue instanceof Wrapper) {//如果返回的值是经过Wrapper包装的
            Wrapper result = (Wrapper) returnValue;

            if (!PubUtils.isNull(result) && result.getCode() == Wrapper.SUCCESS_CODE) {//如果操作结果非空，并且成功
                this.handleLog(joinPoint, result);//处理操作日志
            }

        }

    }

    private void handleLog(final JoinPoint joinPoint, final Object result) {//日志处理
        final Date startTime = this.threadLocal.get();
        final Date endTime = new Date(System.currentTimeMillis());
        HttpServletRequest request = RequestUtil.getRequest();
        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        try{
            final Object[] args = joinPoint.getArgs();
            final String targetName = joinPoint.getTarget().getClass().getName();//获取被代理对象的类名
            final Class targetClass = Class.forName(targetName);//获取被代理的对象
            final Method[] methods = targetClass.getMethods();//获取被代理对象的全部方法
            final String targetMethod = joinPoint.getSignature().getName();//获取连接点的方法名
            String movement="";//当前操作的描述
            final Long taskId;//当前任务的ID
            final Long itemId;//当前任务子项的ID（如果有的话）
            final Integer status;//当前日志对应的巡检状态
            AnanLogAnnotation relog = giveController(joinPoint);
            if(relog == null){
                return ;
            }
            //获取客户端操作系统
            final String os = userAgent.getOperatingSystem().getName();
            //获取客户端浏览器
            final String browser = userAgent.getBrowser().getName();
            final String ipAddress = RequestUtil.getRemoteAddr(request);

            //根据被代理的接口传入参数的不同，进行不同的日志记录
            //获取当前操作的描述
            for(int i=0;i<methods.length;i++){
                Method method = methods[i];
                //判断是否是这个方法
                if(method.getName().equals(targetMethod)){
                    Class[] clazzs = method.getParameterTypes();
                    //判断参数是否一样
                    if(clazzs.length == args.length){
                        movement = method.getAnnotation(ApiOperation.class).value();
                        System.out.println(movement);
                    }
                }
            }
            //获取当前操作对应的任务ID
            Wrapper wrapper = (Wrapper) result;
            if(wrapper.getResult().getClass().getName().equals(ImcInspectionTask.class.getName())){
                //如果当前的日志是巡检任务的
                ImcInspectionTaskLog imcInspectionTaskLog = new ImcInspectionTaskLog();
                ImcInspectionTask imcInspectionTask = (ImcInspectionTask) wrapper.getResult();
                taskId = imcInspectionTask.getId();
                status = imcInspectionTask.getStatus();
                imcInspectionTaskLog.setTaskId(taskId);
                imcInspectionTaskLog.setCreatedTime(startTime);
                imcInspectionTaskLog.setUpdateTime(endTime);
                imcInspectionTaskLog.setMovement(movement);
                imcInspectionTaskLog.setStatusTimestamp(endTime);
                imcInspectionTaskLog.setStatus(status);
                imcInspectionTaskLog.setOs(os);
                imcInspectionTaskLog.setBrowser(browser);
                imcInspectionTaskLog.setIpAddress(ipAddress);
                LoginAuthDto loginUser = RequestUtil.getLoginUser();
                if(imcInspectionTaskLogService.createInspectionTaskLog(imcInspectionTaskLog,loginUser) == 1){
                    System.out.println("巡检任务日志创建成功" + imcInspectionTaskLog);
                }
            }else if(wrapper.getResult().getClass().getName().equals(ImcInspectionItem.class.getName())){
                //如果当前的日志是巡检任务子项的
                ImcInspectionItemLog imcInspectionItemLog = new ImcInspectionItemLog();
                ImcInspectionItem imcInspectionItem = (ImcInspectionItem) wrapper.getResult();
                taskId = imcInspectionItem.getInspectionTaskId();
                itemId = imcInspectionItem.getId();
                status = imcInspectionItem.getStatus();
                imcInspectionItemLog.setItemId(itemId);
                imcInspectionItemLog.setTaskId(taskId);
                imcInspectionItemLog.setCreatedTime(startTime);
                imcInspectionItemLog.setUpdateTime(endTime);
                imcInspectionItemLog.setMovement(movement);
                imcInspectionItemLog.setStatusTimestamp(endTime);
                imcInspectionItemLog.setStatus(status);
                imcInspectionItemLog.setOs(os);
                imcInspectionItemLog.setBrowser(browser);
                imcInspectionItemLog.setIpAddress(ipAddress);
                LoginAuthDto loginUser = RequestUtil.getLoginUser();
                if(imcInspectionItemLogService.createInspectionItemLog(imcInspectionItemLog,loginUser) == 1){
                    System.out.println("巡检任务子项日志创建成功" + imcInspectionItemLog);
                }
            }else if(wrapper.getResult().getClass().getName().equals(ImcDeviceOrder.class.getName())){
                ImcInspectionItemLog imcInspectionItemLog = new ImcInspectionItemLog();
                ImcDeviceOrder imcDeviceOrder = (ImcDeviceOrder) wrapper.getResult();
                taskId = imcDeviceOrder.getInspectionTaskId();
                itemId = imcDeviceOrder.getInspectionItemId();
                imcInspectionItemLog.setItemId(itemId);
                imcInspectionItemLog.setTaskId(taskId);
                imcInspectionItemLog.setCreatedTime(startTime);
                imcInspectionItemLog.setUpdateTime(endTime);
                imcInspectionItemLog.setMovement(movement);
                imcInspectionItemLog.setStatusTimestamp(endTime);
                imcInspectionItemLog.setOs(os);
                imcInspectionItemLog.setBrowser(browser);
                imcInspectionItemLog.setIpAddress(ipAddress);
                LoginAuthDto loginUser = RequestUtil.getLoginUser();
                if(imcInspectionItemLogService.createInspectionItemLog(imcInspectionItemLog,loginUser) == 1){
                    System.out.println("巡检任务子项日志创建成功" + imcInspectionItemLog);
                }
            }else if(wrapper.getResult().getClass().getName().equals(ImcInspectionReview.class.getName())){
                ImcInspectionTaskLog imcInspectionTaskLog = new ImcInspectionTaskLog();
                ImcInspectionReview imcInspectionReview = (ImcInspectionReview) wrapper.getResult();
                taskId = imcInspectionReview.getInspectionTaskId();
                imcInspectionTaskLog.setTaskId(taskId);
                imcInspectionTaskLog.setCreatedTime(startTime);
                imcInspectionTaskLog.setUpdateTime(endTime);
                imcInspectionTaskLog.setMovement(movement);
                imcInspectionTaskLog.setStatusTimestamp(endTime);
                imcInspectionTaskLog.setOs(os);
                imcInspectionTaskLog.setBrowser(browser);
                imcInspectionTaskLog.setIpAddress(ipAddress);
                LoginAuthDto loginUser = RequestUtil.getLoginUser();
                if(imcInspectionTaskLogService.createInspectionTaskLog(imcInspectionTaskLog,loginUser) == 1){
                    System.out.println("巡检任务日志创建成功" + imcInspectionTaskLog);
                }
            }else if(wrapper.getResult().getClass().getName().equals(ImcTaskChangeStatusDto.class.getName())){
                ImcInspectionTaskLog imcInspectionTaskLog = new ImcInspectionTaskLog();
                ImcTaskChangeStatusDto imcTaskChangeStatusDto = (ImcTaskChangeStatusDto) wrapper.getResult();
                taskId = imcTaskChangeStatusDto.getTaskId();
                status = imcTaskChangeStatusDto.getStatus();
                String statusMsg = imcTaskChangeStatusDto.getStatusMsg();
                imcInspectionTaskLog.setTaskId(taskId);
                imcInspectionTaskLog.setCreatedTime(startTime);
                imcInspectionTaskLog.setUpdateTime(endTime);
                imcInspectionTaskLog.setMovement(movement + "为：" + statusMsg);
                imcInspectionTaskLog.setStatusTimestamp(endTime);
                imcInspectionTaskLog.setStatus(status);
                imcInspectionTaskLog.setOs(os);
                imcInspectionTaskLog.setBrowser(browser);
                imcInspectionTaskLog.setIpAddress(ipAddress);
                LoginAuthDto loginUser = RequestUtil.getLoginUser();
                if(imcInspectionTaskLogService.createInspectionTaskLog(imcInspectionTaskLog,loginUser) == 1){
                    System.out.println("巡检任务日志创建成功" + imcInspectionTaskLog);
                }
            }else if(wrapper.getResult().getClass().getName().equals(ImcItemChangeStatusDto.class.getName())){
                ImcInspectionItemLog imcInspectionItemLog = new ImcInspectionItemLog();
                ImcItemChangeStatusDto imcItemChangeStatusDto = (ImcItemChangeStatusDto) wrapper.getResult();
                itemId = imcItemChangeStatusDto.getItemId();
                status = imcItemChangeStatusDto.getStatus();
                Example example = new Example(ImcInspectionItem.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("id",itemId);
                taskId = imcInspectionItemService.selectByExample(example).get(0).getInspectionTaskId();
                String statusMsg = imcItemChangeStatusDto.getStatusMsg();
                imcInspectionItemLog.setItemId(itemId);
                imcInspectionItemLog.setTaskId(taskId);
                imcInspectionItemLog.setStatus(status);
                imcInspectionItemLog.setCreatedTime(startTime);
                imcInspectionItemLog.setUpdateTime(endTime);
                imcInspectionItemLog.setMovement(movement + "为：" + statusMsg);
                imcInspectionItemLog.setStatusTimestamp(endTime);
                imcInspectionItemLog.setOs(os);
                imcInspectionItemLog.setBrowser(browser);
                imcInspectionItemLog.setIpAddress(ipAddress);
                LoginAuthDto loginUser = RequestUtil.getLoginUser();
                if(imcInspectionItemLogService.createInspectionItemLog(imcInspectionItemLog,loginUser) == 1){
                    System.out.println("巡检任务子项日志创建成功" + imcInspectionItemLog);
                }
            }

        }catch(Exception ex){
            log.error("获取注解类出现异常={}", ex.getMessage(), ex);
        }
    }

    /**
     * 是否存在注解, 如果存在就记录日志
     */
    private static AnanLogAnnotation giveController(JoinPoint joinPoint) {
        Method[] methods = joinPoint.getTarget().getClass().getDeclaredMethods();
        String methodName = joinPoint.getSignature().getName();
        if (null != methods && 0 < methods.length) {
            for (Method met : methods) {
                AnanLogAnnotation relog = met.getAnnotation(AnanLogAnnotation.class);
                if (null != relog && methodName.equals(met.getName())) {
                    return relog;
                }
            }
        }

        return null;
    }
}
