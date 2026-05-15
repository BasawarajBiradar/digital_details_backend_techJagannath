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
@Table(name = "nfc_uid_master")
public class NfcUidMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid", unique = true)
    private String uid;

    @JoinColumn(name = "mapped_user")
    @ManyToOne(fetch = FetchType.EAGER)
    private UserMaster mappedUser;

    @JoinColumn(name = "added_by")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserMaster addedBy;

    @Column(name = "added_at")
    private LocalDateTime addedAt;
}
