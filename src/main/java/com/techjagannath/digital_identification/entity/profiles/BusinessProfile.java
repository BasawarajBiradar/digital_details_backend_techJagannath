package com.techjagannath.digital_identification.entity.profiles;

import com.techjagannath.digital_identification.entity.UserMaster;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "business_profile")
public class BusinessProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // business logo

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "business_description", length = 1000)
    private String businessDescription;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "gst_number")
    private String gstNumber;

    @Column(name = "business_phone")
    private String businessPhone;

    @Column(name = "business_address", length = 1000)
    private String businessAddress;

    @Column(name = "social_media_links")
    private String socialMediaLinks;

    @Column(name = "business_email")
    private String businessEmail;

    @Column(name = "website_url")
    private String websiteUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linked_account")
    private UserMaster linkedAccount;
}
