package org.smartregister.chw.fp.util;

public interface FamilyPlanningConstants {

    int REQUEST_CODE_GET_JSON = 2244;

    interface JsonFromExtra {
        String JSON = "json";
    }

    interface EventType {
        String FAMILY_PLANNING_REGISTRATION = "Family Planning Registration";
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
        String DOB = "dob";
        String LAST_INTERACTED_WITH = "last_interacted_with";
        String VILLAGE_TOWN = "village_town";
        String DATE_REMOVED = "date_removed";
        String RELATIONAL_ID = "relational_id";
        String FP_METHOD_ACCEPTED = "fp_method_accepted";

    }

    interface ACTIVITY_PAYLOAD {
        String BASE_ENTITY_ID = "BASE_ENTITY_ID";
        String ACTION = "ACTION";
        String FP_FORM_NAME = "FP_FORM_NAME";
        String REGISTRATION_PAYLOAD_TYPE = "REGISTRATION";
    }

}
