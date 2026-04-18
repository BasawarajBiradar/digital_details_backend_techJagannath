package com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.profiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleProfileResultModel {
    private String vehicleNumber;
    private String vehicleType;
    private String brand;
    private String model;
    private String color;
    private String yearOfManufacture;
    private String ownerName;
    private String ownerContact;
    private String alternateContact;
    private String rcNumber;
    private String insuranceNumber;
    private String insuranceExpiry;
    private String chassisNumber;
}
