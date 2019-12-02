package org.smartregister.chw.fp.domain;

import org.smartregister.chw.fp.util.FamilyPlanningConstants;
import org.smartregister.commonregistry.CommonPersonObjectClient;

import java.io.Serializable;

public class MemberObject implements Serializable {

    private String familyHeadName;
    private String familyHeadPhoneNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private String address;
    private String gender;
    private String uniqueId;
    private String age;
    private String relationalid;
    private String details;
    private String baseEntityId;
    private String relationalId;
    private String primaryCareGiver;
    private String primaryCareGiverName;
    private String primaryCareGiverPhone;
    private String familyHead;
    private String familyBaseEntityId;
    private String familyName;
    private String phoneNumber;

    public MemberObject() {
    }

    public MemberObject(CommonPersonObjectClient client) {
        firstName = client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.FIRST_NAME) != null ? client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.FIRST_NAME) : "";
        middleName = client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.MIDDLE_NAME) != null ? client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.MIDDLE_NAME) : "";
        lastName = client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.LAST_NAME) != null ? client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.LAST_NAME) : "";
        address = client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.VILLAGE_TOWN) != null ? client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.VILLAGE_TOWN) : "";
        gender = client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.GENDER) != null ? client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.GENDER) : "";
        age = client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.DOB) != null ? client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.DOB) : "";
        uniqueId = client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.UNIQUE_ID);
        baseEntityId = client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.BASE_ENTITY_ID);
        relationalId = client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.RELATIONAL_ID);
        primaryCareGiver = client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.PRIMARY_CARE_GIVER);
        familyHead = client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.FAMILY_HEAD);
        familyBaseEntityId = client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.RELATIONALID);
        relationalid = client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.RELATIONALID);
        details = client.getColumnmaps().get(FamilyPlanningConstants.DBConstants.DETAILS);
    }

    public String getFamilyHeadName() {
        return familyHeadName;
    }

    public void setFamilyHeadName(String familyHeadName) {
        this.familyHeadName = familyHeadName;
    }

    public String getFamilyHeadPhoneNumber() {
        return familyHeadPhoneNumber;
    }

    public void setFamilyHeadPhoneNumber(String familyHeadPhoneNumber) {
        this.familyHeadPhoneNumber = familyHeadPhoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRelationalid() {
        return relationalid;
    }

    public void setRelationalid(String relationalid) {
        this.relationalid = relationalid;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getBaseEntityId() {
        return baseEntityId;
    }

    public void setBaseEntityId(String baseEntityId) {
        this.baseEntityId = baseEntityId;
    }

    public String getRelationalId() {
        return relationalId;
    }

    public void setRelationalId(String relationalId) {
        this.relationalId = relationalId;
    }

    public String getPrimaryCareGiver() {
        return primaryCareGiver;
    }

    public void setPrimaryCareGiver(String primaryCareGiver) {
        this.primaryCareGiver = primaryCareGiver;
    }

    public String getPrimaryCareGiverName() {
        return primaryCareGiverName;
    }

    public void setPrimaryCareGiverName(String primaryCareGiverName) {
        this.primaryCareGiverName = primaryCareGiverName;
    }

    public String getPrimaryCareGiverPhone() {
        return primaryCareGiverPhone;
    }

    public void setPrimaryCareGiverPhone(String primaryCareGiverPhone) {
        this.primaryCareGiverPhone = primaryCareGiverPhone;
    }

    public String getFamilyHead() {
        return familyHead;
    }

    public void setFamilyHead(String familyHead) {
        this.familyHead = familyHead;
    }

    public String getFamilyBaseEntityId() {
        return familyBaseEntityId;
    }

    public void setFamilyBaseEntityId(String familyBaseEntityId) {
        this.familyBaseEntityId = familyBaseEntityId;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
