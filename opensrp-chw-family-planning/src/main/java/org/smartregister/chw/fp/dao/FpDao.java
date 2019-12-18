package org.smartregister.chw.fp.dao;

import org.jetbrains.annotations.Nullable;
import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.fp.domain.FpAlertObject;
import org.smartregister.chw.fp.domain.FpMemberObject;
import org.smartregister.dao.AbstractDao;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

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
        String sql = "select m.base_entity_id , m.unique_id , m.relational_id , m.dob , m.first_name , " +
                "m.middle_name , m.last_name , m.gender , m.phone_number , m.other_phone_number , " +
                "f.first_name family_name ,f.primary_caregiver , f.family_head , f.village_town ," +
                "fh.first_name family_head_first_name , fh.middle_name family_head_middle_name , " +
                "fh.last_name family_head_last_name, fh.phone_number family_head_phone_number, " +
                "pcg.first_name pcg_first_name , pcg.last_name pcg_last_name , pcg.middle_name pcg_middle_name , " +
                "pcg.phone_number  pcg_phone_number , mr.* from ec_family_member m " +
                "inner join ec_family f on m.relational_id = f.base_entity_id " +
                "inner join ec_family_planning mr on mr.base_entity_id = m.base_entity_id " +
                "left join ec_family_member fh on fh.base_entity_id = f.family_head " +
                "left join ec_family_member pcg on pcg.base_entity_id = f.primary_caregiver where m.base_entity_id ='" + baseEntityID + "' ";

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
            memberObject.setFpStartDate(getCursorValue(cursor, "fp_start_date", ""));
            memberObject.setPillCycles(Integer.parseInt(getCursorValue(cursor, "no_pillcycles", "0")));
            memberObject.setFpMethod(getCursorValue(cursor, "fp_method_accepted", ""));

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
        String sql = "select fp_method_accepted, no_pillcycles, fp_start_date from ec_family_planning where base_entity_id = '" + baseEntityID + "' " +
                "and is_closed is 0 and ecp = 1";

        List<FpAlertObject> fpAlertObjects = AbstractDao.readData(sql, getVisitDetailsDataMap());
        if (fpAlertObjects.size() == 0) {
            return null;
        }
        return fpAlertObjects;
    }

    private static DataMap<FpAlertObject> getVisitDetailsDataMap() {
        return c -> {
            FpAlertObject fpAlertObject = new FpAlertObject();
            try {
                fpAlertObject.setFpMethod(getCursorValue(c, "fp_method_accepted"));
                fpAlertObject.setFpPillCycles(getCursorIntValue(c, "no_pillcycles"));
                fpAlertObject.setFpStartDate(getCursorValue(c, "fp_start_date"));
            } catch (Exception e) {
                Timber.e(e.toString());
            }
            return fpAlertObject;
        };
    }

    @Nullable
    public static Visit getLatestFpVisit(String baseEntityId, String entityType, String fpMethod) {
        String sql = " SELECT visit_date, visit_id,visit_type, parent_visit_id " +
                "FROM Visits v " +
                "INNER JOIN ec_family_planning fp on fp.base_entity_id = v.base_entity_id " +
                " WHERE v.base_entity_id = '" + baseEntityId + "' COLLATE NOCASE " +
                " AND v.visit_type = '" + entityType + "' COLLATE NOCASE " +
                " AND fp.fp_method_accepted = '" + fpMethod + "' COLLATE NOCASE " +
                " AND strftime('%Y%d%m', (datetime(v.visit_date/1000, 'unixepoch')))  >= substr(fp.fp_start_date " +
                ",7,4) || substr(fp.fp_start_date " +
                ",4,2) || substr(fp.fp_start_date " +
                ",1,2) " +
                "ORDER BY v.visit_date DESC";

        List<Visit> visit = AbstractDao.readData(sql, getVisitDataMap());
        if (visit.size() == 0) {
            return null;
        }
        return visit.get(0);
    }

    private static DataMap<Visit> getVisitDataMap() {
        return c -> {
            Visit visit = new Visit();
            visit.setVisitId(getCursorValue(c, "visit_id"));
            visit.setParentVisitID(getCursorValue(c, "parent_visit_id"));
            visit.setVisitType(getCursorValue(c, "visit_type"));
            visit.setDate(getCursorValueAsDate(c, "visit_date"));

            return visit;
        };
    }


    @Nullable
    public static Visit getLatestInjectionVisit(String baseEntityId, String fpMethod) {
        String sql = "SELECT  substr(details,7,4) || '-' || substr(details,4,2) || '-' || substr(details,1,2) details " +
                " FROM visit_details vd " +
                " INNER JOIN visits v on vd.visit_id = v.visit_id " +
                " WHERE vd.visit_key = 'fp_refill_injectable' " +
                " AND v.base_entity_id = '" + baseEntityId + "' COLLATE NOCASE " +
                " AND strftime('%Y%d%m', (datetime(v.visit_date/1000, 'unixepoch'))) " +
                " >= ( SELECT substr(fp_start_date,7,4) || substr(fp_start_date,4,2) || substr(fp_start_date,1,2) FROM ec_family_planning WHERE base_entity_id = '" + baseEntityId + "' COLLATE NOCASE  AND fp_method_accepted = '" + fpMethod + "' COLLATE NOCASE) " +
                " ORDER BY vd.details DESC";

        List<Visit> visit = AbstractDao.readData(sql, getInjectionVisitDataMap());
        if (visit.size() == 0) {
            return null;
        }
        return visit.get(0);
    }

    private static DataMap<Visit> getInjectionVisitDataMap() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return c -> {
            Visit visit = new Visit();
            try {
                visit.setDate(sdf.parse(getCursorValue(c, "sorr")));
            } catch (Exception e) {
                Timber.e(e.toString());
            }
            return visit;
        };
    }

    public static Integer getFpWomenCount(String familyBaseEntityId) {
        String sql = "SELECT count(fp.base_entity_id) count " +
                "FROM ec_family_planning fp " +
                "INNER Join ec_family_member fm on fm.base_entity_id = fp.base_entity_id " +
                "WHERE fm.relational_id = '" + familyBaseEntityId + "' COLLATE NOCASE " +
                "AND fp.is_closed = 0 " +
                "AND fp.ecp = 1 ";

        DataMap<Integer> dataMap = cursor -> getCursorIntValue(cursor, "count");

        List<Integer> res = readData(sql, dataMap);
        if (res == null || res.size() == 0)
            return null;

        return res.get(0);
    }

    public static void closeFpMemberFromRegister(String baseEntityID) {
        String sql = "update ec_family_planning set is_closed = 1 where base_entity_id = '" + baseEntityID + "'";
        updateDB(sql);
    }
}
