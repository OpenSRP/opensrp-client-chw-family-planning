package org.smartregister.chw.fp.util;

public interface FamilyPlanningConstants {

    int REQUEST_CODE_GET_JSON = 2244;
    String ENCOUNTER_TYPE = "encounter_type";

    interface JsonFromExtra {
        String JSON = "json";
    }

    interface EventType {
        String FAMILY_PLANNING_REGISTRATION = "Family Planning Registration";
    }

    interface Forms {
        String FAMILY_PLANNING_REGISTRATION_FORM = "family_planning_registration";
    }

    interface DBConstants {
        String FAMILY_PLANNING_TABLE = "ec_family_planning";

        String FIRST_NAME = "first_name";
        String MIDDLE_NAME = "middle_name";
        String LAST_NAME = "last_name";
        String BASE_ENTITY_ID = "base_entity_id";
        String DOB = "dob";
        String DOD = "dod";
        String UNIQUE_ID = "unique_id";
        String LAST_INTERACTED_WITH = "last_interacted_with";
        String VILLAGE_TOWN = "village_town";
        String DATE_REMOVED = "date_removed";
    }
}
