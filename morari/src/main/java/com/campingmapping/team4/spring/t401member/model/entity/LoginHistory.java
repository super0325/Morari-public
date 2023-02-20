package com.campingmapping.team4.spring.t401member.model.entity;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// loginhistory
//登入歷史
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loginhistory")
@Component
public class LoginHistory {

    // lhid
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loginhistoryid")
    private Integer loginhistoryid;
    // uid
    @JsonIgnoreProperties("loginhistory")
    @ManyToOne
    @JoinColumn(name = "uid")
    private UserProfiles userprofiles;
    // ip
    // IP位置
    @Column(name = "loginhistoryip")
    private String loginhistoryip;
    // logindate
    // 登入時間
    @Column(name = "logindate")
    private Date logindate;
    // show
    @Column(name = "showloginhistory")
    private String showloginhistory;

}
