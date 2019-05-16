package com.ftn.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ftn.model.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by Alex on 5/20/17.
 */
@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class BaseDTO {

    private long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updated;

    public BaseDTO(BaseModel baseModel) {
        this.id = baseModel.getId();
        this.created = baseModel.getCreated();
        this.updated = baseModel.getUpdated();
    }
}
