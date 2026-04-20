package com.techjagannath.digital_identification.models.usermanagement.vehicleProfile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterCardUserVehicleDetailsRequestModel {
    /* vehicle details */
    @NotBlank(message = "Pin code is required")
    private String vehicleNumber;
    @NotBlank(message = "Pin code is required")
    private String vehicleType;
    private String brand;
    private String model;
    private String color;
    private String yearOfManufacture;
    @NotBlank(message = "Pin code is required")
    private String ownerName;
    @NotBlank(message = "Pin code is required")
    private String ownerContact;
    private String alternateContact;
    private String rcNumber;
    private String insuranceNumber;
    private String insuranceExpiry;
    private String chassisNumber;
}
