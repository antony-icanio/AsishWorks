package com.example.AsishWorks.model.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Mappings {
   private DispatcherServlets dispatcherServlets;
   private List<ServletFilters> servletFilters;
   private List<Servlets> servlets;
}
