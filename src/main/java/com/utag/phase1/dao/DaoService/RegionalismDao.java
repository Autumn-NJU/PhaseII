package com.utag.phase1.dao.DaoService;

public interface RegionalismDao {


    boolean saveRegionalism();

    /**
     *
     * @param imageID
     * @return
     */
    boolean deleteRegionalism(int imageID);
}
