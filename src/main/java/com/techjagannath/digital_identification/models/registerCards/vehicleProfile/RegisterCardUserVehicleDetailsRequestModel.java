package com.techjagannath.digital_identification.models.registerCards.vehicleProfile;

import lombok.Data;

@Data
public class RegisterCardUserVehicleDetailsRequestModel {
    /* register user details */
    private Long userId;

    /* vehicle details */
    private String vehicleNumber;
    private String vehicleType;
    private String brand;
    private String model;
    private String ownerName;
    private String ownerContact;
    private String alternateContact;
}
