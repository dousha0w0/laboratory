package com.senior.domain.bo.request;

import com.senior.domain.model.ReserveDo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class ReserveCreateBoRequest extends ReserveDo {

}
