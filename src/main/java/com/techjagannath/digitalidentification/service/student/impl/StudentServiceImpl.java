package com.techjagannath.digitalidentification.service.student.impl;

import com.techjagannath.digitalidentification.entity.*;
import com.techjagannath.digitalidentification.exception.ResourceNotFoundException;
import com.techjagannath.digitalidentification.models.student.attendancepage.calendarview.GetStudentAttendancePageCalendarViewRequestModel;
import com.techjagannath.digitalidentification.models.student.attendancepage.calendarview.GetStudentAttendancePageCalendarViewResultModel;
import com.techjagannath.digitalidentification.models.student.attendancepage.overview.GetStudentAttendancePageOverviewRequestModel;
import com.techjagannath.digitalidentification.models.student.attendancepage.overview.GetStudentAttendancePageOverviewResultModel;
import com.techjagannath.digitalidentification.models.student.getstudentattendance.GetStudentAttendanceDataRequestModel;
import com.techjagannath.digitalidentification.models.student.getstudentattendance.GetStudentAttendanceDataResponseModel;
import com.techjagannath.digitalidentification.models.student.homepageinfocard.RetrieveStudentHomePageInfoCardDetailsResultModel;
import com.techjagannath.digitalidentification.models.student.homeworkpage.overview.GetStudentHomeworkOverviewResultModel;
import com.techjagannath.digitalidentification.models.student.homeworkpage.table.GetStudentHomeworkTableResultModel;
import com.techjagannath.digitalidentification.models.student.homeworkpage.udpatestatus.GetStudentHomeworkUpdateStatusRequestModel;
import com.techjagannath.digitalidentification.models.student.homeworkpage.udpatestatus.GetStudentHomeworkUpdateStatusResultModel;
import com.techjagannath.digitalidentification.models.student.nfccardtap.RetrieveStudentNfcTapDetailsRequestModel;
import com.techjagannath.digitalidentification.models.student.nfccardtap.RetrieveStudentNfcTapResultModel;
import com.techjagannath.digitalidentification.models.student.recordnfctap.RecordNfcTapResultModel;
import com.techjagannath.digitalidentification.models.student.registerstudentnfc.RegisterStudentUidRequestModel;
import com.techjagannath.digitalidentification.models.student.registerstudentnfc.RegisterStudentUidResultModel;
import com.techjagannath.digitalidentification.models.student.retrieveschoollist.RetrieveSchoolListResultModel;
import com.techjagannath.digitalidentification.models.student.tapphotopage.overview.GetStudentTapPhotoPageOverviewRequestModel;
import com.techjagannath.digitalidentification.models.student.tapphotopage.overview.GetStudentTapPhotoPageOverviewResultModel;
import com.techjagannath.digitalidentification.models.student.todayentries.RetrieveStudentHomePageTodayEntriesResultModel;
import com.techjagannath.digitalidentification.models.student.todayupdates.RetrieveStudentHomePageTodayUpdatesResultModel;
import com.techjagannath.digitalidentification.models.student.uploadprofilephoto.StudentProfilePhotoUploadResultModel;
import com.techjagannath.digitalidentification.models.student.verifyuid.VerifyNfcUidResultModel;
import com.techjagannath.digitalidentification.repository.*;
import com.techjagannath.digitalidentification.repository.customrepositories.StudentHomeWorkStatusRepository;
import com.techjagannath.digitalidentification.service.student.StudentService;
import com.techjagannath.digitalidentification.service.whatsappservice.WhatsAppService;
import com.techjagannath.digitalidentification.utils.CommonMethods;
import com.techjagannath.digitalidentification.utils.dateutils.DateUtils;
import com.techjagannath.digitalidentification.utils.dateutils.StudentResponsePopulateDateUtils;
import com.techjagannath.digitalidentification.utils.s3fileupload.S3Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final CommonMethods commonMethods;
    private final NfcCardTapsHistoryRepository nfcCardTapsHistoryRepository;
    private final UserMasterRepository userMasterRepository;
    private final NfcUidMasterRepository nfcUidMasterRepository;
    private final SchoolMasterRepository schoolMasterRepository;
    private final RoleMasterRepository roleMasterRepository;
    private final AddressMasterRepository addressMasterRepository;
    private final StudentDetailsMasterRepository studentDetailsMasterRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Utils s3Utils;
    private final StudentProfilePhotoRepoRepository studentProfilePhotoRepoRepository;
    private final SchoolLogoRepoRepository schoolLogoRepoRepository;
    private final NfcReaderDeviceMasterRepository nfcReaderDeviceMasterRepository;
    private final WhatsAppService whatsAppService;
    private final AttendanceRecordsTableRepository attendanceRecordsTableRepository;
    private final YearlySchoolStartDateMasterRepository yearlySchoolStartDateMasterRepository;
    private final HomeWorkDetailRecordsRepository homeWorkDetailRecordsRepository;
    private final HomeWorkStatusRepository homeWorkStatusRepository;
    private final StudentHomeWorkStatusRepository studentHomeWorkStatusRepository;

    private static final String USER_NOT_FOUND = "User not found";
    private static final String UID_NOT_VALID = "Invalid UID";
    private static final String RESOURCE_NOT_FOUND = "Resource Not Found";

    public StudentServiceImpl(CommonMethods commonMethods, NfcCardTapsHistoryRepository nfcCardTapsHistoryRepository,
                              UserMasterRepository userMasterRepository, NfcUidMasterRepository nfcUidMasterRepository,
                              SchoolMasterRepository schoolMasterRepository, RoleMasterRepository roleMasterRepository,
                              AddressMasterRepository addressMasterRepository, StudentDetailsMasterRepository studentDetailsMasterRepository,
                              StudentProfilePhotoRepoRepository studentProfilePhotoRepoRepository, S3Utils s3Utils,
                              PasswordEncoder passwordEncoder, SchoolLogoRepoRepository schoolLogoRepoRepository, StudentHomeWorkStatusRepository studentHomeWorkStatusRepository,
                              NfcReaderDeviceMasterRepository nfcReaderDeviceMasterRepository, WhatsAppService whatsAppService,
                              AttendanceRecordsTableRepository attendanceRecordsTableRepository, HomeWorkDetailRecordsRepository homeWorkDetailRecordsRepository,
                              YearlySchoolStartDateMasterRepository yearlySchoolStartDateMasterRepository, HomeWorkStatusRepository homeWorkStatusRepository) {
        this.commonMethods = commonMethods;
        this.nfcCardTapsHistoryRepository = nfcCardTapsHistoryRepository;
        this.userMasterRepository = userMasterRepository;
        this.nfcUidMasterRepository = nfcUidMasterRepository;
        this.schoolMasterRepository = schoolMasterRepository;
        this.roleMasterRepository = roleMasterRepository;
        this.addressMasterRepository = addressMasterRepository;
        this.studentDetailsMasterRepository = studentDetailsMasterRepository;
        this.passwordEncoder = passwordEncoder;
        this.s3Utils = s3Utils;
        this.studentProfilePhotoRepoRepository = studentProfilePhotoRepoRepository;
        this.schoolLogoRepoRepository = schoolLogoRepoRepository;
        this.nfcReaderDeviceMasterRepository = nfcReaderDeviceMasterRepository;
        this.whatsAppService = whatsAppService;
        this.attendanceRecordsTableRepository = attendanceRecordsTableRepository;
        this.yearlySchoolStartDateMasterRepository = yearlySchoolStartDateMasterRepository;
        this.homeWorkDetailRecordsRepository = homeWorkDetailRecordsRepository;
        this.homeWorkStatusRepository = homeWorkStatusRepository;
        this.studentHomeWorkStatusRepository = studentHomeWorkStatusRepository;
    }

    @Override
    public RetrieveStudentHomePageInfoCardDetailsResultModel serviceEntryPointForRetrieveHomePageInfoCardDetails(HttpServletRequest request) {
        UserMaster user = this.commonMethods.extractUser(request);
        StringBuilder fullName = new StringBuilder(user.getFirstName());
        if (user.getMiddleName() != null)
            fullName.append(" ").append(user.getMiddleName());
        if (user.getLastName() != null)
            fullName.append(" ").append(user.getLastName());

        SchoolMaster school = user.getSchool();
        StudentDetailsMaster student = user.getStudentDetails();

        StringBuilder studentAddress = new StringBuilder();
        if (student.getStudentAddress().getAddressLineOne() != null)
            studentAddress.append(student.getStudentAddress().getAddressLineOne());
        if (student.getStudentAddress().getAddressLineTwo() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getAddressLineTwo());
        if (student.getStudentAddress().getCity() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getCity());
        if (student.getStudentAddress().getPinCode() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getPinCode());
        if (student.getStudentAddress().getState() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getState());
        if (student.getStudentAddress().getCountry() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getCountry());

        StudentProfilePhotoRepo studentProfile = this.studentProfilePhotoRepoRepository.findByMappedUserAndIsActive(user, true);
        SchoolLogoRepo schoolLogo = this.schoolLogoRepoRepository.findBySchoolAndIsActive(school, true);

        return new RetrieveStudentHomePageInfoCardDetailsResultModel(
                school.getSchoolName(), schoolLogo != null ? this.s3Utils.generatePreSignedUrl(schoolLogo.getFileUrl()) : null,
                studentProfile != null ? this.s3Utils.generatePreSignedUrl(studentProfile.getFileUrl()) : null
                , fullName.toString(), student.getClassLevel(), student.getDivision(),
                student.getBloodGroup(), user.getMobileNumber(), user.getEmailId(),
                student.getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), studentAddress.toString(),
                student.getEmergencyContactName(), student.getEmergencyContactNumber(),
                student.getEmergencyContactRelation(), student.getAlternateContactNumber(), user.getUid()
        );
    }

    @Override
    public List<RetrieveStudentHomePageTodayEntriesResultModel> serviceEntryPointForRetrieveHomePageTodayEntries(HttpServletRequest request) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        UserMaster user = this.commonMethods.extractUser(request);
        List<NfcCardTapsHistory> entries = this.nfcCardTapsHistoryRepository.retrieveLoggedInUserTodayEntries(user);
        List<RetrieveStudentHomePageTodayEntriesResultModel> resultModels = new ArrayList<>();
        for (NfcCardTapsHistory entry : entries)
            resultModels.add(new RetrieveStudentHomePageTodayEntriesResultModel(
                    entry.getTimeStamp().format(dateFormatter), entry.getTimeStamp().format(timeFormatter),
                    entry.getDevice().getRoomNumber() != null ? entry.getDevice().getSchool().getSchoolName() + entry.getDevice().getRoomNumber() : entry.getDevice().getSchool().getSchoolName(),
                    this.s3Utils.generatePreSignedUrl(entry.getFileUrl())));

        return resultModels;
    }

    @Override
    public RetrieveStudentNfcTapResultModel serviceEntryPointForRetrieveStudentNfcTapDetails(String uid, RetrieveStudentNfcTapDetailsRequestModel requestModel) {
        UserMaster user = this.userMasterRepository.findById(requestModel.getUserId()).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));// this.userMasterRepository.findByUid(uid);
        if (user == null)
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        StringBuilder fullName = new StringBuilder(user.getFirstName());
        if (user.getMiddleName() != null)
            fullName.append(" ").append(user.getMiddleName());
        if (user.getLastName() != null)
            fullName.append(" ").append(user.getLastName());

        SchoolMaster school = user.getSchool();
        StudentDetailsMaster student = user.getStudentDetails();

        StringBuilder studentAddress = new StringBuilder();
        if (student.getStudentAddress().getAddressLineOne() != null)
            studentAddress.append(student.getStudentAddress().getAddressLineOne());
        if (student.getStudentAddress().getAddressLineTwo() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getAddressLineTwo());
        if (student.getStudentAddress().getCity() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getCity());
        if (student.getStudentAddress().getPinCode() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getPinCode());
        if (student.getStudentAddress().getState() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getState());
        if (student.getStudentAddress().getCountry() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getCountry());

        StudentProfilePhotoRepo studentProfile = this.studentProfilePhotoRepoRepository.findByMappedUserAndIsActive(user, true);
        SchoolLogoRepo schoolLogo = this.schoolLogoRepoRepository.findBySchoolAndIsActive(school, true);

        return new RetrieveStudentNfcTapResultModel(
                school.getSchoolName(), schoolLogo != null ? this.s3Utils.generatePreSignedUrl(schoolLogo.getFileUrl()) : null,
                studentProfile != null ? this.s3Utils.generatePreSignedUrl(studentProfile.getFileUrl()) : null
                , fullName.toString(), student.getClassLevel(), student.getDivision(),
                student.getBloodGroup(), user.getMobileNumber(), user.getEmailId(),
                student.getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), studentAddress.toString(),
                student.getEmergencyContactName(), student.getEmergencyContactNumber(),
                student.getEmergencyContactRelation(), student.getAlternateContactNumber()
        );
    }

    @Override
    public VerifyNfcUidResultModel serviceEntryPointForVerifyStudentNfcUid(String uid) {
        Optional<NfcUidMaster> result = this.nfcUidMasterRepository.findByUid(uid);
        if (result.isEmpty())
            throw new ResourceNotFoundException(UID_NOT_VALID);
        UserMaster user = result.get().getMappedUser();
        return new VerifyNfcUidResultModel( user != null ? user.getId() : null);
    }

    @Override
    @Transactional
    public RegisterStudentUidResultModel serviceEntryPointForRegisterStudentNfcUid(String uid, RegisterStudentUidRequestModel requestModel) {
        Optional<NfcUidMaster> result = this.nfcUidMasterRepository.findByUid(uid);
        if (result.isEmpty())
            throw new ResourceNotFoundException(UID_NOT_VALID);
        if (result.get().getMappedUser() != null)
            throw new DataIntegrityViolationException(UID_NOT_VALID);

        SchoolMaster schoolMaster = this.schoolMasterRepository.findById(requestModel.getSchoolId()).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
        RoleMaster role = this.roleMasterRepository.findById(3).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));

        AddressMaster studentAddress = new AddressMaster(null, requestModel.getAddressLineOne(),
                requestModel.getAddressLineTwo(), requestModel.getCity(), requestModel.getState(), requestModel.getPinCode(), requestModel.getCountry());
        AddressMaster savedStudentAddress = this.addressMasterRepository.save(studentAddress);

        StudentDetailsMaster studentDetails = new StudentDetailsMaster(null, requestModel.getClassLevel(), requestModel.getDivision(),
                savedStudentAddress, requestModel.getBloodGroup(), requestModel.getBirthDate(), requestModel.getEmergencyContactName(),
                requestModel.getEmergencyContactRelation(), requestModel.getEmergencyContactNumber(), requestModel.getAlternateNumber(),
                null, LocalDateTime.now());
        StudentDetailsMaster savedStudentDetails = this.studentDetailsMasterRepository.save(studentDetails);

        UserMaster newUser = new UserMaster(null, requestModel.getFirstName(), requestModel.getLastName(), requestModel.getMiddleName(),
                requestModel.getMobileNumber(), passwordEncoder.encode(requestModel.getPassword()),
                requestModel.getEmailId(), role, true, schoolMaster, savedStudentDetails, null, uid, false,
                null, LocalDateTime.now());
        UserMaster savedUser = this.userMasterRepository.save(newUser);

        NfcUidMaster nfcUidMaster = result.get();
        nfcUidMaster.setMappedUser(savedUser);
        this.nfcUidMasterRepository.save(nfcUidMaster);

        return new RegisterStudentUidResultModel(savedUser.getId());
    }

    @Override
    public List<RetrieveSchoolListResultModel> serviceEntryPointForRetrieveSchoolList() {
        List<SchoolMaster> schools = this.schoolMasterRepository.findAll();
        List<RetrieveSchoolListResultModel> resultModels = new ArrayList<>();
        for (SchoolMaster schoolMaster : schools)
            resultModels.add(new RetrieveSchoolListResultModel(schoolMaster.getId(), schoolMaster.getSchoolName()));
        return resultModels;
    }

    @Override
    @Transactional
    public StudentProfilePhotoUploadResultModel serviceEntryPointForUploadStudentProfileImages(HttpServletRequest request, MultipartFile file) {
        UserMaster user = this.commonMethods.extractUser(request);
        StudentProfilePhotoRepo oldImage = this.studentProfilePhotoRepoRepository.findByMappedUserAndIsActive(user, true);

        if (oldImage != null) {
            oldImage.setIsActive(false);
            this.studentProfilePhotoRepoRepository.save(oldImage);
            this.s3Utils.deleteFile(oldImage.getFileUrl());
        }

        String fileKey = this.s3Utils.uploadStudentProfileImage(file);

        StudentProfilePhotoRepo profilePhoto = new StudentProfilePhotoRepo(null, user,
                fileKey, file.getOriginalFilename(), file.getContentType(), LocalDateTime.now(), true);
        this.studentProfilePhotoRepoRepository.save(profilePhoto);

        return new StudentProfilePhotoUploadResultModel(true);
    }

    @Override
    @Transactional
    public RecordNfcTapResultModel serviceEntryPointForRecordNfcTap(String uid, Long deviceId, MultipartFile image) {
        NfcReaderDeviceMaster deviceMaster = null;

        if (deviceId != null)
            deviceMaster = this.nfcReaderDeviceMasterRepository
                        .findById(deviceId).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));

        String imageUrl = this.s3Utils.uploadTapRecordImage(image);

        NfcUidMaster nfc = this.nfcUidMasterRepository.findByUid(uid).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
        NfcCardTapsHistory history = new NfcCardTapsHistory(null, nfc.getMappedUser(),
                nfc.getUid(), deviceMaster, LocalDateTime.now(), imageUrl, image.getOriginalFilename(),
                image.getContentType(), ".png", LocalDateTime.now());

        if (!Boolean.TRUE.equals(nfc.getMappedUser().getIsPresent())) {
            nfc.getMappedUser().setIsPresent(true);
            this.userMasterRepository.save(nfc.getMappedUser());
//            this.whatsAppService.sendEntryAlert(nfc.getMappedUser().getMobileNumber(), nfc.getMappedUser().getFirstName(),
//                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")));
        }

        this.nfcCardTapsHistoryRepository.save(history);

        return new RecordNfcTapResultModel(true);
    }

    @Override
    public List<GetStudentAttendanceDataResponseModel> serviceEntryPointForRetrieveAttendanceData(HttpServletRequest request, GetStudentAttendanceDataRequestModel requestModel) {
        UserMaster user = commonMethods.extractUser(request);
        List<AttendanceRecordsTable> resultList = this.attendanceRecordsTableRepository.retrieveAttendanceData(
                requestModel.getParsedFromDate(), requestModel.getParsedToDate(), user.getSchool(),  user, user.getRole());
        List<GetStudentAttendanceDataResponseModel> response = new LinkedList<>();
        for (AttendanceRecordsTable res : resultList)
            response.add(new GetStudentAttendanceDataResponseModel(
                    DateUtils.dateFormatter(res.getDate()),
                    res.getStatus().getStatus(),
                    DateUtils.formatTimeTo12Hour(res.getInTime()),
                    DateUtils.formatTimeTo12Hour(res.getOutTime())));
        return response;
    }

    @Override
    public RetrieveStudentHomePageTodayUpdatesResultModel serviceEntryPointForRetrieveHomePageTodayUpdates(HttpServletRequest request) {
        RetrieveStudentHomePageTodayUpdatesResultModel result = new RetrieveStudentHomePageTodayUpdatesResultModel();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        UserMaster user = commonMethods.extractUser(request);
        YearlySchoolStartDateMaster schoolStartDate = this.yearlySchoolStartDateMasterRepository.findFirstBySchoolMasterOrderBySchoolStartDateDesc(user.getSchool());
        NfcCardTapsHistory tapHistory = this.nfcCardTapsHistoryRepository.findTodayFirstEntry(user);
        if (tapHistory == null)
            result.setAttendanceStatus("ABSENT");
        else {
            result.setAttendanceStatus("PRESENT");
            result.setEntryTime(tapHistory.getTimeStamp().format(timeFormatter));
        }
        if (schoolStartDate != null) {
            Long pendingHomeworkCount = this.studentDetailsMasterRepository.retrieveCountOfPendingHomework(user, schoolStartDate.getSchoolStartDate());
            result.setPendingHomeWorkCount(pendingHomeworkCount);
        }
        // get total notice count date >= school start date  - school
        // get teacher feedback title recent - user
        // last week performance grade and percentage - user
        return result;
    }

    @Override
    public GetStudentAttendancePageOverviewResultModel serviceEntryPointForAttendancePageOverviewData(HttpServletRequest request, GetStudentAttendancePageOverviewRequestModel requestModel) {
        UserMaster user = this.commonMethods.extractUser(request);
        GetStudentAttendancePageOverviewResultModel response = new GetStudentAttendancePageOverviewResultModel();
        Integer presentDaysCount = this.attendanceRecordsTableRepository.retrieveCountByStatusBetweenDates(1, requestModel.getParsedFromDate(), requestModel.getParsedToDate(), user.getId());
        Integer absentDaysCount = this.attendanceRecordsTableRepository.retrieveCountByStatusBetweenDates(2, requestModel.getParsedFromDate(), requestModel.getParsedToDate(), user.getId());
        response.setPresentDays(presentDaysCount);
        response.setAbsentDays(absentDaysCount);
        if (presentDaysCount != 0 || absentDaysCount != 0)
            response.setAttendancePercentage(presentDaysCount * 100.0 / (presentDaysCount + absentDaysCount));
        return response;
    }

    @Override
    public List<GetStudentAttendancePageCalendarViewResultModel> serviceEntryPointForAttendancePageCalendarViewData(HttpServletRequest request, GetStudentAttendancePageCalendarViewRequestModel requestModel) {
        UserMaster user = this.commonMethods.extractUser(request);
        List<Object[]> resultList = this.attendanceRecordsTableRepository.retrieveCalendarViewData(requestModel.getParsedFromDate(), requestModel.getParsedToDate(), user.getId());
        List<GetStudentAttendancePageCalendarViewResultModel> response = new LinkedList<>();
        for (Object[] res : resultList) {
            String date = res[0].toString();
            String status = res[1].toString();
            response.add(new GetStudentAttendancePageCalendarViewResultModel(date, status));
        }
        return StudentResponsePopulateDateUtils.populateDatesInCalendarView(requestModel.getParsedFromDate(), requestModel.getParsedToDate(), response);
    }

    @Override
    public List<GetStudentTapPhotoPageOverviewResultModel> serviceEntryPointForTapPhotoPageOverview(HttpServletRequest request, GetStudentTapPhotoPageOverviewRequestModel requestModel) {
        UserMaster user = this.commonMethods.extractUser(request);
        List<Object[]> resultList = this.nfcCardTapsHistoryRepository.retrievePhotoTapRecordsByUser(user.getId(), requestModel.getParsedFromDate(), requestModel.getParsedToDate());
        List<GetStudentTapPhotoPageOverviewResultModel> response = new LinkedList<>();
        for (Object[] res : resultList) {
            String timeStamp = res[0].toString();
            String date = timeStamp.split(" ")[0];
            String time = timeStamp.split(" ")[1];
            String url = res[1] != null ? this.s3Utils.generatePreSignedUrl(res[1].toString()) : null;

            response.add(new GetStudentTapPhotoPageOverviewResultModel(date, time, url));
        }
        return response;
    }

    @Override
    public GetStudentHomeworkOverviewResultModel serviceEntryPointForRetrieveHomeworkOverviewData(HttpServletRequest request) {
        UserMaster user = this.commonMethods.extractUser(request);
        YearlySchoolStartDateMaster schoolStartDateMaster = this.yearlySchoolStartDateMasterRepository.findFirstBySchoolMasterOrderBySchoolStartDateDesc(user.getSchool());
        LocalDate schoolStartDate = LocalDate.now().withDayOfYear(1);
        if (schoolStartDateMaster != null)
            schoolStartDate = schoolStartDateMaster.getSchoolStartDate();
        Long completedCount = this.homeWorkDetailRecordsRepository.retrieveHomeworkCompletedCount(
                user.getStudentDetails().getClassLevel(), user.getStudentDetails().getDivision(), user.getId(), schoolStartDate);
        Long totalCount = this.homeWorkDetailRecordsRepository.retrieveHomeworkTotalCount(
                user.getStudentDetails().getClassLevel(), user.getStudentDetails().getDivision(), user.getSchool().getId(), schoolStartDate);
        return new GetStudentHomeworkOverviewResultModel(totalCount - completedCount, completedCount);
    }

    @Override
    public List<GetStudentHomeworkTableResultModel> serviceEntryPointForHomeworkTableData(HttpServletRequest request) {
        UserMaster user = this.commonMethods.extractUser(request);
        YearlySchoolStartDateMaster schoolStartDateMaster = this.yearlySchoolStartDateMasterRepository.findFirstBySchoolMasterOrderBySchoolStartDateDesc(user.getSchool());
        LocalDate schoolStartDate = LocalDate.now().withDayOfYear(1);
        if (schoolStartDateMaster != null)
            schoolStartDate = schoolStartDateMaster.getSchoolStartDate();
        List<Object[]> resultList = this.homeWorkDetailRecordsRepository.retrieveHomeworkTableData(
                user.getId(), user.getStudentDetails().getClassLevel(), user.getStudentDetails().getDivision(), user.getSchool().getId(), schoolStartDate);
        List<GetStudentHomeworkTableResultModel> resultModel = new LinkedList<>();
        for (Object[] res : resultList) {
            Long homeworkId = Long.parseLong(res[0].toString());
            String title = res[1].toString();
            String assignedDateAndTime = res[2].toString().split(" ")[0];
            String deadlineDate = res[3] == null ? null : res[3].toString();
            String status = res[4] == null ? null : res[4].toString();
            String subject = res[5] == null ? null : res[5].toString();
            String description = res[6] == null ? null : res[6].toString();

            resultModel.add(new GetStudentHomeworkTableResultModel(homeworkId, title, assignedDateAndTime, deadlineDate, status, subject, description));
        }
        return resultModel;
    }

    @Override
    public GetStudentHomeworkUpdateStatusResultModel serviceEntryPointForHomeworkPageUpdateStatus(HttpServletRequest request, GetStudentHomeworkUpdateStatusRequestModel requestModel) {
        UserMaster user = this.commonMethods.extractUser(request);
        HomeWorkDetailRecords homework = this.homeWorkDetailRecordsRepository.findById(requestModel.getHomeworkId()).get();
        HomeWorkStatus status = this.homeWorkStatusRepository.findById(requestModel.getStatus()).get();
        StudentHomeworkStatus studentHomeworkStatus = this.studentHomeWorkStatusRepository.findByHomeWorkDetailsAndStudent(homework, user);
        if (studentHomeworkStatus == null)
            studentHomeworkStatus = new StudentHomeworkStatus(null, user, homework, status, LocalDateTime.now());
        studentHomeworkStatus.setStatus(status);
        this.studentHomeWorkStatusRepository.save(studentHomeworkStatus);
        return new GetStudentHomeworkUpdateStatusResultModel(true);
    }
}
