package com.techjagannath.digitalidentification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nfc_reader_device_master")
public class NfcReaderDeviceMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "school")
    @ManyToOne(fetch = FetchType.EAGER)
    private SchoolMaster school;

    @Column(name = "room_number")
    private String roomNumber;

}
