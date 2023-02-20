package com.campingmapping.team4.spring.t401member.model.entity;

import java.util.Date;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPrivacy {
    private String phone;
    private Date birthday;
    private String address;
}