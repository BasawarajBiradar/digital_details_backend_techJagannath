package com.techjagannath.digitalidentification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address_master")
public class AddressMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address_line_one", length = 500)
    private String addressLineOne;

    @Column(name = "address_line_two", length = 500)
    private String addressLineTwo;

    private String city;
    private String state;

    @Column(name = "pin_code")
    private String pinCode;
    private String country;
}
