package com.campingmapping.team4.spring.t401member.model.entity;

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
public class UserName {
    // 名
    private String firstname;
    // 姓
    private String lastname;
}