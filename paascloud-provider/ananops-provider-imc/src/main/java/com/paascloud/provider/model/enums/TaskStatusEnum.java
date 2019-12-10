package com.paascloud.provider.model.enums;


import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.base.exception.BusinessException;

/**
 * Created by rongshuai on 2019/12/5 9:18
 */
public enum TaskStatusEnum {

    NO_SUCH_STATUS(-1,"没有这种状态"),

    WAITING_FOR_FACILITATOR(1,"等待服务商接单"),

    FACILITATOR_TOOK_ORDER(2,"服务商已接单"),

    WORKERS_TOOK_ORDER(3,"巡检工已全部接单"),

    IN_THE_INSPECTION(4,"巡检工巡检中"),

    WAITING_FOR_PRINCIPAL(5,"巡检结束，等待甲方负责人审查"),

    WAITING_FOR_PAY(6,"甲方负责人审查结束，等待付款"),

    WAITING_FOR_REVIEW(7,"已付款，等待甲方负责人评价"),

    INSPECTION_OVER(8,"已完成评价，巡检结束");

    /**
     * The statusNum.
     */
    int statusNum;

    /**
     * The statusMsg.
     */
    String statusMsg;

    TaskStatusEnum(int statusNum,String statusMsg){
        this.statusNum = statusNum;
        this.statusMsg = statusMsg;
    }

    public int getStatusNum() {
        return statusNum;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public static String getStatusMsg(int statusNum){
        for(TaskStatusEnum ele:TaskStatusEnum.values()){
            if(statusNum == ele.getStatusNum()){
                return ele.getStatusMsg();
            }
        }
        throw new BusinessException(ErrorCodeEnum.GL9999095);
    }

    public static TaskStatusEnum getEnum(int statusNum){
        for(TaskStatusEnum ele:TaskStatusEnum.values()){
            if(statusNum == ele.getStatusNum()){
                return ele;
            }
        }
        return TaskStatusEnum.NO_SUCH_STATUS;
    }

}
