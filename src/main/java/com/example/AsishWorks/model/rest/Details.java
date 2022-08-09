package com.example.AsishWorks.model.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Details {
    private HandlerMethod handlerMethod;
    private RequestMappingConditions requestMappingConditions;
}
