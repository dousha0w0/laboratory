package com.senior.domain.bo.request;

import com.senior.domain.model.MeetingDo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 实验室(Meeting) CreateBoRequest
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class MeetingCreateBoRequest extends MeetingDo {

}
