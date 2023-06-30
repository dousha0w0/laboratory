package com.senior.domain.model;

import java.util.List;

import com.senior.common.base.BaseDo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 实验室(Meeting) Do
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingDo implements BaseDo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 实验名称
     */
    private String name;

    /**
     * 内容
     */
    private String content;

    /**
     * 实验室
     */
    private Long roomId;

    /**
     * 人员名单
     */
    private List<Long> accountIds;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

    /**
     *  人脸数据
     */
    private String faceImage;

}
