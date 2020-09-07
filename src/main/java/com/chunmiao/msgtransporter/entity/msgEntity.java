package com.chunmiao.msgtransporter.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class msgEntity implements Serializable {
    @GeneratedValue
    @Column(unique = true)
    @Id
    Integer id;

    @CreationTimestamp
    @JsonFormat(pattern = "MM-dd HH-mm-ss")
    Date createTime;

    @Column(columnDefinition = "TEXT")
    String msg;
}
