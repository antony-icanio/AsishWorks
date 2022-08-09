package com.example.AsishWorks.model;

import com.example.AsishWorks.model.rest.MethodAndEndpoint;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FinalModelUnsedApi {
    private String handlerName;
    private List<MethodAndEndpoint> MethodAndEndpoint;
}
