package com.ftn.model;

import com.ftn.constants.Sql;
import com.ftn.model.dto.BaseDTO;
import com.ftn.model.dto.BusinessYearDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 5/15/17.
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = Sql.UPDATE + "business_year" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class BusinessYear extends BaseModel {

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private boolean closed;

    @ManyToOne(optional = false)
    private Company company;

    @OneToMany(mappedBy = "businessYear", cascade = CascadeType.ALL)
    private List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy = "businessYear", cascade = CascadeType.ALL)
    private List<WarehouseCard> warehouseCards = new ArrayList<>();

    public BusinessYear(BaseDTO baseDTO) {
        super(baseDTO);
    }

    public void merge(BusinessYearDTO businessYearDTO) {
        this.year = businessYearDTO.getYear();
        this.closed = businessYearDTO.isClosed();
        this.company = businessYearDTO.getCompany().construct();
    }
}
