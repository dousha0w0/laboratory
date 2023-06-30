package com.senior.console.api.controller.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.senior.common.base.BaseVoRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实验室(Room) CreateVoRequest
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomCreateVoRequest implements BaseVoRequest {

    /**
     * 实验室
     */
    @ApiModelProperty(value = "实验室")
    @Length(max = 255, message = "name长度不能超过255")
    @NotNull
    private String name;

    /**
     * 实景照片
     */
    @ApiModelProperty(value = "实景照片")
    private List<Long> roomImageIds;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String description;

}
