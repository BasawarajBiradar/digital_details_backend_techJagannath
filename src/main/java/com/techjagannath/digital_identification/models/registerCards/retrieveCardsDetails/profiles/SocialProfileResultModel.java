package com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.profiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialProfileResultModel {
    private String fullName;
    private String nickname;
    private String instagramHandle;
    private String facebookProfile;
    private String linkedinProfile;
    private String twitterHandle;
    private String emergencyContactName;
    private String emergencyContactNumber;
    private String messageToFinder;
}
