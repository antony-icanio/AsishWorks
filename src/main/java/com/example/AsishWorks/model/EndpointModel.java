package com.example.AsishWorks.model;

import com.example.AsishWorks.model.rest.AvailableTags;
import com.example.AsishWorks.model.rest.Measurements;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EndpointModel {

    private String name;
    private String description;
    private String baseUnit;
    private List<Measurements> measurements;
    private List<AvailableTags> availableTags;
}
