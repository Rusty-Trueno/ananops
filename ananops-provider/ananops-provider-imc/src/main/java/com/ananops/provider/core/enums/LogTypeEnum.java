package com.ananops.provider.core.enums;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by rongshuai on 2019/11/29 17:43
 */

public enum LogTypeEnum {
    /**
     * 操作日志
     */
    OPERATION_LOG("1", "操作日志"),
    /**
     * 巡检任务日志
     */
    INSPECTION_TASK_LOG("2","巡检任务日志"),

    /**
     * 巡检任务子项日志
     */
    INSPECTION_ITEM_LOG("3","巡检任务子项日志"),

    /**
     * 异常日志
     */
    EXCEPTION_LOG("4","异常日志");

    /**
     * The Type
     */
    String type;

    /**
     * The Name.
     */
    String name;

    LogTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets name.
     *
     * @param type the type
     *
     * @return the name
     */
    public static String getName(String type) {
        for (LogTypeEnum ele : LogTypeEnum.values()) {
            if (type.equals(ele.getType())) {
                return ele.getName();
            }
        }
        return null;
    }

    /**
     * Gets enum.
     *
     * @param type the type
     *
     * @return the enum
     */
    public static LogTypeEnum getEnum(String type) {
        for (LogTypeEnum ele : LogTypeEnum.values()) {
            if (type.equals(ele.getType())) {
                return ele;
            }
        }
        return LogTypeEnum.OPERATION_LOG;
    }

    /**
     * Gets list.
     *
     * @return the list
     */
    public static List<Map<String, Object>> getList() {
        List<Map<String, Object>> list = Lists.newArrayList();
        for (LogTypeEnum ele : LogTypeEnum.values()) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", ele.getType());
            map.put("value", ele.getName());
            list.add(map);
        }
        return list;
    }
}
