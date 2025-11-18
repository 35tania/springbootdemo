package com.tania.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDetails {

    private String businessName;
    private String businessEmail;
    private String businessMobile;
    private String businessAddress;
    private String businessDescription;
    private String state;
    private String city;
    private String zipCode;
    private String logoUrl;     
    private String bannerUrl; 

}
