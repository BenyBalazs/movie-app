package com.benyovszki.user.model;

import java.time.LocalDateTime;

import com.benyovszki.user.utils.IdGenerator;
import jakarta.annotation.Generated;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class BaseModel {

    @Id
    @Generated(value = IdGenerator.GENERATOR_NAME)
    @GenericGenerator(name = IdGenerator.GENERATOR_NAME, strategy = "uuid")
    private String id;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;
}
