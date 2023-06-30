package com.senior.domain.model;

import com.senior.common.base.BaseDo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 预约记录(Reserve) Do
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveDo implements BaseDo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 实验室
     */
    private Long roomId;

    /**
     * 内容
     */
    private String content;

    /**
     * 预约人
     */
    private Long accountId;

    /**
     * 开始时间
     */
    private Long reserveStartTime;

    /**
     * 结束时间
     */
    private Long reserveEndTime;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

    /**
     * 实验
     */
    private Long meetingId;

    /**
     * 状态;1:审核中 2.审核通过 3.审核不通过
     */
    private Integer status;


    /**
     * 人脸数据
     */
    private String faceImage;

}
