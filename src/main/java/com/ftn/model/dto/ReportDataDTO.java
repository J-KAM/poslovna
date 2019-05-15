package com.ftn.model.dto;

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

    //TODO: mozda ovu klasu da preimenujemo samo u reportData posto sad koristi WarehouseCard
    private WarehouseCard warehouseCardDTO;

    private Date startDate;

    private Date endDate;
}
