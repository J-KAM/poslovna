package com.ftn.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by Olivera on 5.7.2017..
 */
@Data
@NoArgsConstructor
public class ReportDataDTO {

    private WarehouseCard warehouseCardDTO;

    private Date startDate;

    private Date endDate;
}
