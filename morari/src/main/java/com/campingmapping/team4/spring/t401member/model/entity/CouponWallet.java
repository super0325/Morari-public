package com.campingmapping.team4.spring.t401member.model.entity;

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

//票券夾
// couponwallet
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "couponwallet")
@Component
public class CouponWallet {

    // cwid
    // 票券夾ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cwid")
    private Integer cwid;
    // uid
    // 擁有者ID
    @JsonIgnoreProperties("couponwallet")
    @ManyToOne
    @JoinColumn(name = "uid")
    private UserProfiles userprofiles;
    // couponid
    // 票券ID
    @JsonIgnoreProperties("couponwallet")
    @ManyToOne
    @JoinColumn(name = "couponid")
    private Coupon coupon;
    // state
    // 狀態(1.未使用 2.已使用 3.已過期 4.暫停)
    @Column(name = "couponwalletstate")
    private Integer couponwalletstate;
    // show
    @Column(name = "showcouponwallet")
    private String showcouponwallet;

}
