package com.ftn.model;

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
    @Column
    private long id;

    @Column(nullable = false)
    private Date created;

    @Column
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
}
