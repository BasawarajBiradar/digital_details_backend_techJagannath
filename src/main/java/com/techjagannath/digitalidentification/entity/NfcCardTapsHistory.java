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

    @JoinColumn(name = "device")
    @ManyToOne(fetch = FetchType.EAGER)
    private NfcReaderDeviceMaster device;

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
}
