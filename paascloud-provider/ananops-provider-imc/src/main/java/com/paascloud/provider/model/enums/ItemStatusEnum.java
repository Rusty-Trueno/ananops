package com.paascloud.provider.model.enums;


import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.base.exception.BusinessException;

/**
 * Created by rongshuai on 2019/12/5 10:53
 */
public enum ItemStatusEnum {

    NO_SUCH_STATUS(-1,"没有这种状态"),

    WAITING_FOR_INSPECTION(1,"等待巡检"),

    IN_THE_INSPECTION(2,"巡检工巡检中"),

    INSPECTION_OVER(3,"该子项巡检结束，等待甲方负责人审核");
    /**
     * The statusNum.
     */
    int statusNum;

    /**
     * The statusMsg.
     */
    String statusMsg;

    ItemStatusEnum(int statusNum,String statusMsg){
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
        for(ItemStatusEnum ele:ItemStatusEnum.values()){
            if(statusNum == ele.getStatusNum()){
                return ele.getStatusMsg();
            }
        }
        throw new BusinessException(ErrorCodeEnum.GL9999094);
    }

    public static ItemStatusEnum getEnum(int statusNum){
        for(ItemStatusEnum ele:ItemStatusEnum.values()){
            if(statusNum == ele.getStatusNum()){
                return ele;
            }
        }
        return ItemStatusEnum.NO_SUCH_STATUS;
    }

}
