package com.example.AsishWorks.controller;

import com.example.AsishWorks.model.EndPointDetails;
import com.example.AsishWorks.model.rest.AllEndPointModel;
import com.example.AsishWorks.model.EndpointModel;
import com.example.AsishWorks.model.rest.AvailableTags;
import com.example.AsishWorks.model.rest.EndPointDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.example.AsishWorks.model.rest.DispatcherServlet;

import java.util.*;


//Links

//Links for Actuator usages

//https://levelup.gitconnected.com/application-monitoring-using-spring-boot-actuators-part-1-dab8576f4db6

//Application Properties

//management.endpoints.web.exposure.include=*
//management.endpoints.web.exposure.include=mappings


@RestController
public class ActuatorController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hittedEndpointList")
    public AvailableTags getHitedEndpointList() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        EndpointModel endpointModel = restTemplate.exchange("http://localhost:8080/actuator/metrics/http.server.requests", HttpMethod.GET, entity, EndpointModel.class).getBody();
        return endpointModel.getAvailableTags().get(2);
    }


    @GetMapping("/endpointList")
    public AvailableTags endpointList() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        AllEndPointModel AllEndPointModel = restTemplate.exchange("http://localhost:8080/actuator/mappings", HttpMethod.GET, entity, AllEndPointModel.class).getBody();
        List<DispatcherServlet> dispatcherServletList = AllEndPointModel.getContexts().getApplication().getMappings().getDispatcherServlets().getDispatcherServlet();
        AvailableTags availableTags= new  AvailableTags();
        List<String> endPointList=new ArrayList<>();
        for (DispatcherServlet dispatcherServlet : dispatcherServletList) {
            try {
                if (dispatcherServlet.getDetails() != null) {
                    if (dispatcherServlet.getDetails().getRequestMappingConditions().getPatterns() != null) {
                        endPointList.add(dispatcherServlet.getDetails().getRequestMappingConditions().getPatterns().get(0));
                    }
                }
            }
            catch (Exception e){
                System.out.println("error");
            }
        }
        availableTags.setValues(endPointList);
        return availableTags;
    }



    @GetMapping("/allEndpointDetails")
    public EndPointDetailsModel allEndpointList() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        AllEndPointModel AllEndPointModel = restTemplate.exchange("http://localhost:8080/actuator/mappings", HttpMethod.GET, entity, AllEndPointModel.class).getBody();
        List<DispatcherServlet> dispatcherServletList = AllEndPointModel.getContexts().getApplication().getMappings().getDispatcherServlets().getDispatcherServlet();
        List<EndPointDetails> endPointDetailsList = new ArrayList<>();
            for (DispatcherServlet dispatcherServlet : dispatcherServletList) {
                EndPointDetails endPointDetails = new EndPointDetails();
                try {
                    if (dispatcherServlet.getDetails() != null) {
                        endPointDetails.setHandlerName(dispatcherServlet.getDetails().getHandlerMethod().getClassName());
                        if (dispatcherServlet.getDetails().getRequestMappingConditions().getPatterns() != null) {
                            endPointDetails.setEndPointName(dispatcherServlet.getDetails().getRequestMappingConditions().getPatterns().get(0));
                        }
                        if (dispatcherServlet.getDetails().getRequestMappingConditions().getMethods() != null) {
                            endPointDetails.setMethodName(dispatcherServlet.getDetails().getRequestMappingConditions().getMethods().get(0));
                        }
                        endPointDetailsList.add(endPointDetails);
                    }
                }
                catch (Exception e){
                }
            }
        EndPointDetailsModel endPointDetailsModel =new  EndPointDetailsModel();
        endPointDetailsModel.setEndPointDetails(endPointDetailsList);
        return endPointDetailsModel;
    }



    @GetMapping("unUsedApiList")
    public List<EndPointDetails> unUsedApiList(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        AvailableTags availableTags= restTemplate.exchange("http://localhost:8080/hittedEndpointList", HttpMethod.GET, entity,AvailableTags.class).getBody();
        AvailableTags availableTags1 = restTemplate.exchange("http://localhost:8080/endpointList", HttpMethod.GET, entity,AvailableTags.class).getBody();
        List<String> endPointList = availableTags1.getValues();
        List<String> hitEndPointList = availableTags.getValues();
        //Set<String> unUsedEndPointList = new HashSet<>();
        List<String> unUsedEndPointList = new ArrayList<>();

        int count=0;
        for (String endPoint : endPointList){
           for(String hitEndPoint : hitEndPointList){
               if(endPoint.equals(hitEndPoint)) {
                   count++;
               }
           }
            if(count==0){
                unUsedEndPointList.add(endPoint);
            }
           count=0;
        }


        EndPointDetailsModel endPointDetailsModel=restTemplate.exchange("http://localhost:8080/allEndpointDetails", HttpMethod.GET, entity, EndPointDetailsModel.class).getBody();
        List<EndPointDetails> endPointDetailsList = endPointDetailsModel.getEndPointDetails();
        List<EndPointDetails> endPointDetailsList1 = new ArrayList<>();
        for(String unUsedEndPoint :unUsedEndPointList){
            for(EndPointDetails endPointDetails :endPointDetailsList){
                if(unUsedEndPoint.equals(endPointDetails.getEndPointName())){
                    endPointDetailsList1.add(endPointDetails);
                    break;
                }
            }
        }

        return endPointDetailsList1;
    }
}