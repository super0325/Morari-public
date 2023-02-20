package com.campingmapping.team4.spring.utils.service.attributemapper;

import java.util.Map;

import com.campingmapping.team4.spring.t401member.model.entity.OAuth2Request;

// mapping interface
public interface AttributeMappable {
  OAuth2Request mapToDTO(Map<String, Object> attributes);
}
