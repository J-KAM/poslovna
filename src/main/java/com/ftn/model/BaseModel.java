package com.ftn.model;

import com.ftn.model.dto.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Alex on 4/16/17.
 */
@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class BaseModel {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private Date created;

    private Date updated;

    @Column(nullable = false)
    private boolean active = true;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }

    public BaseModel(BaseDTO baseDTO) {
        this.id = baseDTO.getId();
    }
}
