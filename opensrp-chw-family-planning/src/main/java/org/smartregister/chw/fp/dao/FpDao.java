package org.smartregister.chw.fp.dao;

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


}
