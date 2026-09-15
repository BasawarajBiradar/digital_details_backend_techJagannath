package com.techjagannath.digitalidentification.service.teacher.impl;

import com.techjagannath.digitalidentification.entity.NfcUidMaster;
import com.techjagannath.digitalidentification.entity.UserMaster;
import com.techjagannath.digitalidentification.exception.ResourceNotFoundException;
import com.techjagannath.digitalidentification.models.student.verifyuid.VerifyNfcUidResultModel;
import com.techjagannath.digitalidentification.repository.NfcUidMasterRepository;
import com.techjagannath.digitalidentification.service.teacher.TeacherService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final NfcUidMasterRepository nfcUidMasterRepository;

    private static final String UID_NOT_VALID = "Invalid UID";

    public TeacherServiceImpl(NfcUidMasterRepository nfcUidMasterRepository) {
        this.nfcUidMasterRepository = nfcUidMasterRepository;
    }

    @Override
    public VerifyNfcUidResultModel serviceEntryPointForVerifyTeacherNfcUid(String uid) {
        Optional<NfcUidMaster> result = this.nfcUidMasterRepository.findByUid(uid);
        if (result.isEmpty())
            throw new ResourceNotFoundException(UID_NOT_VALID);
        UserMaster user = result.get().getMappedUser();
        return new VerifyNfcUidResultModel( user != null ? user.getId() : null);
    }
}
