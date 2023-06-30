package com.senior.console.api.controller.response;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.senior.common.base.BaseVoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实验室(Meeting) QueryVoResponse
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingQueryVoResponse implements BaseVoResponse {

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 实验名称
     */
    @ExcelProperty(value = "实验名称")
    private String name;

    /**
     * 内容
     */
    @ExcelProperty(value = "内容")
    private String content;

    /**
     * 实验室
     */
    @ExcelIgnore
    private Long roomId;

    /**
     * 人员名单
     */
    @ExcelIgnore
    private List<Long> accountIds;

    @ExcelProperty(value = "人员名单")
    private String accountNames;

    @ExcelIgnore
    private List<AccountQueryVoResponse> accounts;

    /**
     * 创建时间
     */
    @ExcelIgnore
    private Long createTime;
    @ExcelProperty(value = "创建时间")
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeDate;
    /**
     * 修改时间
     */
    @ExcelIgnore
    private Long updateTime;
    @ExcelProperty(value = "修改时间")
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private Date updateTimeDate;
    /**
     * 实验室
     */
    @ExcelProperty(value = "实验室")
    private String roomName;

    @ExcelProperty(value = "人脸数据")
    private String faceImage;

    public Date getCreateTimeDate() {
        return Optional.ofNullable(createTime).map(Date::new).orElse(null);
    }

    public Date getUpdateTimeDate() {
        return Optional.ofNullable(updateTime).map(Date::new).orElse(null);
    }

}
