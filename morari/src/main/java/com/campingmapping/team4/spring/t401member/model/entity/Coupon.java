package com.campingmapping.team4.spring.t401member.model.entity;

import java.sql.Blob;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// coupon
//折價券
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coupon")
@Component
public class Coupon {
    // couponid
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couponid")
    private Integer couponid;
    // couponcode
    // 折扣碼
    @Column(name = "couponcode")
    private String couponcode;
    // couponname
    // 券名
    @Column(name = "couponname")
    private String couponname;
    // coupontype
    // 類型
    @Column(name = "coupontype")
    private int coupontype;
    // couponamount
    // 發放數量
    @Column(name = "couponamount")
    private int couponamount;
    // couponused
    // 使用數量
    @Column(name = "couponused")
    private int couponused;
    // couponrule
    // 規則
    @Column(name = "couponrule")
    private String couponrule;
    // startdate
    // 開始日期
    @Column(name = "startdate")
    private Date startdate;
    // enddate
    // 結束日期
    @Column(name = "enddate")
    private Date enddate;
    // creatdate
    // 建置日期
    @Column(name = "creatdate")
    private Date creatdate;
    // show
    // 是否顯示
    @Column(name = "showcoupon")
    private String showcoupon;
    // state
    @Column(name = "couponstate")
    private String couponstate;
    // couponphoto
    // 票券圖案
    @Column(name = "couponphoto")
    private Blob couponphoto;

    @JsonIgnore
    @JsonIgnoreProperties("coupon")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "coupon")
    @OrderBy("cwid desc")
    @Builder.Default
    private Set<CouponWallet> couponWallet = new LinkedHashSet<CouponWallet>();

}
