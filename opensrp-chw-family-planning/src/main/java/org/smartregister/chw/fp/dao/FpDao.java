package org.smartregister.chw.fp.dao;

import org.jetbrains.annotations.Nullable;
import org.smartregister.chw.fp.domain.FpAlertObject;
import org.smartregister.chw.fp.domain.FpMemberObject;
import org.smartregister.dao.AbstractDao;

import java.util.List;

public class FpDao extends AbstractDao {

    public static boolean isRegisteredForFp(String baseEntityID) {
        String sql = String.format(
                "select count(ec_family_planning.base_entity_id) count\n" +
                        "from ec_family_planning\n" +
                        "where base_entity_id = '%s'\n" +
                        "  and ec_family_planning.is_closed = 0\n" +
                        "  and ec_family_planning.ecp = 1", baseEntityID);

        DataMap<Integer> dataMap = cursor -> getCursorIntValue(cursor, "count");

        List<Integer> res = readData(sql, dataMap);
        if (res == null || res.size() != 1)
            return false;

        return res.get(0) > 0;
    }

    public static FpMemberObject getMember(String baseEntityID) {
        String sql = "select m.base_entity_id , m.unique_id , m.relational_id , m.dob , m.first_name , m.middle_name , m.last_name , m.gender , m.phone_number , m.other_phone_number , f.first_name family_name ,f.primary_caregiver , f.family_head , f.village_town ,fh.first_name family_head_first_name , fh.middle_name family_head_middle_name , fh.last_name family_head_last_name, fh.phone_number family_head_phone_number, pcg.first_name pcg_first_name , pcg.last_name pcg_last_name , pcg.middle_name pcg_middle_name , pcg.phone_number  pcg_phone_number , mr.* from ec_family_member m inner join ec_family f on m.relational_id = f.base_entity_id inner join ec_family_planning mr on mr.base_entity_id = m.base_entity_id left join ec_family_member fh on fh.base_entity_id = f.family_head left join ec_family_member pcg on pcg.base_entity_id = f.primary_caregiver where m.base_entity_id ='" + baseEntityID + "' ";

        DataMap<FpMemberObject> dataMap = cursor -> {
            FpMemberObject memberObject = new FpMemberObject();

            memberObject.setFirstName(getCursorValue(cursor, "first_name", ""));
            memberObject.setMiddleName(getCursorValue(cursor, "middle_name", ""));
            memberObject.setLastName(getCursorValue(cursor, "last_name", ""));
            memberObject.setAddress(getCursorValue(cursor, "village_town"));
            memberObject.setGender(getCursorValue(cursor, "gender"));
            memberObject.setUniqueId(getCursorValue(cursor, "unique_id", ""));
            memberObject.setAge(getCursorValue(cursor, "dob"));
            memberObject.setFamilyBaseEntityId(getCursorValue(cursor, "relational_id", ""));
            memberObject.setRelationalId(getCursorValue(cursor, "relational_id", ""));
            memberObject.setPrimaryCareGiver(getCursorValue(cursor, "primary_caregiver"));
            memberObject.setFamilyName(getCursorValue(cursor, "family_name", ""));
            memberObject.setPhoneNumber(getCursorValue(cursor, "phone_number", ""));
            memberObject.setBaseEntityId(getCursorValue(cursor, "base_entity_id", ""));
            memberObject.setFamilyHead(getCursorValue(cursor, "family_head", ""));
            memberObject.setFamilyHeadPhoneNumber(getCursorValue(cursor, "pcg_phone_number", ""));
            memberObject.setFamilyHeadPhoneNumber(getCursorValue(cursor, "family_head_phone_number", ""));

            String familyHeadName = getCursorValue(cursor, "family_head_first_name", "") + " "
                    + getCursorValue(cursor, "family_head_middle_name", "");

            familyHeadName =
                    (familyHeadName.trim() + " " + getCursorValue(cursor, "family_head_last_name", "")).trim();
            memberObject.setFamilyHeadName(familyHeadName);

            String familyPcgName = getCursorValue(cursor, "pcg_first_name", "") + " "
                    + getCursorValue(cursor, "pcg_middle_name", "");

            familyPcgName =
                    (familyPcgName.trim() + " " + getCursorValue(cursor, "pcg_last_name", "")).trim();
            memberObject.setPrimaryCareGiverName(familyPcgName);

            return memberObject;
        };

        List<FpMemberObject> res = readData(sql, dataMap);
        if (res == null || res.size() != 1)
            return null;

        return res.get(0);
    }

    @Nullable
    public static List<FpAlertObject> getFpDetails(String baseEntityID) {
        String sql = "select fp_method_accepted, no_pillcycles, fp_reg_date from ec_family_planning where base_entity_id = '" + baseEntityID + "'" +
                "and is_closed is 0 and ecp = 1";

        DataMap<FpAlertObject> dataMap = c -> new FpAlertObject(
                getCursorValue(c, "fp_method_accepted"),
                getCursorIntValue(c, "no_pillcycles"),
                getCursorValueAsDate(c, "fp_reg_date", getNativeFormsDateFormat())
        );
        List<FpAlertObject> fpAlertObjects = readData(sql, dataMap);
        if (fpAlertObjects.size() == 0) {
            return null;
        }
        return fpAlertObjects;
    }
}
