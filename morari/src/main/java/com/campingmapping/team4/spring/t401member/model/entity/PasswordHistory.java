package com.campingmapping.team4.spring.t401member.model.entity;

import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "passwordhistory")
@Component
public class PasswordHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passwordhistoryid")
    private Integer passwordhistoryid;

    @Column(name = "account")
    private String account;

    @Column(name = "oldpassword")
    private String oldpassword;

    @Column(name = "setpassworddate")
    private Date setpassworddate;

}