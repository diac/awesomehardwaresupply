package com.diac.awesomehardwaresupply.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final String message;
}