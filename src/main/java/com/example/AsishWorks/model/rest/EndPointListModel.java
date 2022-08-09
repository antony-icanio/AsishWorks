package com.example.AsishWorks.model.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EndPointListModel {
    private List<String> endPointName;
}
