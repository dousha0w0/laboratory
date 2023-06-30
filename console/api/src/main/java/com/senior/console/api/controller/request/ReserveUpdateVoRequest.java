package com.senior.console.api.controller.request;

import javax.validation.constraints.NotNull;

import com.senior.common.base.BaseVoRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 预约记录(Reserve) UpdateVoRequest
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveUpdateVoRequest implements BaseVoRequest {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 实验室
     */
    @ApiModelProperty(value = "实验室")
    @NotNull
    private Long roomId;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * 预约人
     */
    @ApiModelProperty(value = "预约人")
    @NotNull
    private Long accountId;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @NotNull
    private Long reserveStartTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @NotNull
    private Long reserveEndTime;

    /**
     * 审核状态;1:待审核 2:审核通过 3:审核不通过
     */
    @ApiModelProperty(value = "审核状态")
    private Integer status;

}
