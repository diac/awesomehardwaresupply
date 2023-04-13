package com.diac.awesomehardwaresupply.gateway.model;

import com.diac.awesomehardwaresupply.domain.enumeration.Authority;
import org.springframework.http.HttpMethod;

import java.util.List;

public record AclRecord(String uri, List<HttpMethod> httpMethods, Authority requiredAuthority) {

}