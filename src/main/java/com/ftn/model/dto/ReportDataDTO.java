package com.ftn.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ftn.model.WarehouseCard;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by Olivera on 5.7.2017..
 */
@Data
@NoArgsConstructor
public class ReportDataDTO {

    private WarehouseCardDTO warehouseCardDTO;

    private Date startDate;

    private Date endDate;
}
