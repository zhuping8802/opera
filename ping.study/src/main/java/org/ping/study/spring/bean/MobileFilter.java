package org.ping.study.spring.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"info"})
public interface MobileFilter {

}
