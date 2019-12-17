package org.smartregister.chw.fp.util;

public interface FamilyPlanningConstants {

    int REQUEST_CODE_GET_JSON = 2244;

    interface FamilyPlanningMemberObject {
        String MEMBER_OBJECT = "fpMemberObject";
    }

    interface JsonFromExtra {
        String JSON = "json";
    }

    interface EventType {
        String FAMILY_PLANNING_REGISTRATION = "Family Planning Registration";
        String FAMILY_PLANNING_CHANGE_METHOD = "Family Planning Change Method";
        String FP_FOLLOW_UP_VISIT = "Fp Follow Up Visit";
        String FP_FOLLOW_UP_VISIT_RESUPPLY = "Fp Follow Up Visit Resupply";
        String FP_FOLLOW_UP_VISIT_COUNSELLING = "Fp Follow Up Visit Counselling";
        String FP_FOLLOW_UP_VISIT_SIDE_EFFECTS = "Fp Follow Up Visit Side Effects";
    }

    interface Forms {
        String FAMILY_PLANNING_REGISTRATION_FORM = "family_planning_registration";
    }

    interface CONFIGURATION {
        String FAMILY_PLANNING_REGISTER = "family_planning_register";
    }

    interface DBConstants {
        String FAMILY_PLANNING_TABLE = "ec_family_planning";
        String FAMILY_MEMBER = "ec_family_member";
        String FAMILY = "ec_family";
        String FIRST_NAME = "first_name";
        String MIDDLE_NAME = "middle_name";
        String LAST_NAME = "last_name";
        String BASE_ENTITY_ID = "base_entity_id";
        String UNIQUE_ID = "unique_id";
        String GENDER = "gender";
        String DOB = "dob";
        String AGE = "age";
        String LAST_INTERACTED_WITH = "last_interacted_with";
        String VILLAGE_TOWN = "village_town";
        String DATE_REMOVED = "date_removed";
        String RELATIONALID = "relationalid";
        String FAMILY_HEAD = "family_head";
        String PRIMARY_CARE_GIVER = "primary_caregiver";
        String RELATIONAL_ID = "relational_id";
        String DETAILS = "details";
        String FP_METHOD_ACCEPTED = "fp_method_accepted";
        String FP_FP_START_DATE = "fp_start_date";
        String FP_PILL_CYCLES = "no_pillcycles";
        String REASON_STOP_FP_CHW = "reason_stop_fp_chw";

        String FP_POP = "POP";
        String FP_COC = "COC";
        String FP_FEMALE_CONDOM = "Female condom";
        String FP_MALE_CONDOM = "Male condom";
        String FP_INJECTABLE = "Injectable";
        String FP_IUCD = "IUCD";
        String FP_FEMALE_STERLIZATION = "Female sterilization";
        String FP_MALE_STERLIZATION = "Male sterilization";
    }

    interface ActivityPayload {
        String BASE_ENTITY_ID = "BASE_ENTITY_ID";
        String ACTION = "ACTION";
        String FP_FORM_NAME = "FP_FORM_NAME";
        String REGISTRATION_PAYLOAD_TYPE = "REGISTRATION";
        String CHANGE_METHOD_PAYLOAD_TYPE = "CHANGE_METHOD";
        String DOB = "DOB";
    }

}
