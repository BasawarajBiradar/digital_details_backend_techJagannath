package com.techjagannath.digitalidentification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nfc_card_taps_history")
public class NfcCardTapsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student")
    private UserMaster studentUser;

    private String uid;
    @Column(name = "device_id")
    private String deviceId;
    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
}
