package com.techjagannath.digital_identification.entity.profiles;
import com.techjagannath.digital_identification.entity.ProfileTypesMaster;
import com.techjagannath.digital_identification.entity.UserMaster;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_profile_nfc_mapping")
public class UserProfileNfcMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_master")
    private UserMaster userMaster;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_type")
    private ProfileTypesMaster profileType;

    @Column(unique = true)
    private String uid;

    private Long profileId;

}
