package com.utag.phase1.service.Impl;

import com.utag.phase1.dao.DaoService.TagWholeDao;
import com.utag.phase1.service.TagWholeService;
import com.utag.phase1.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class TagWholeServiceImpl implements TagWholeService {

    @Autowired
    private TagWholeDao tagWholeDao;

    @Override
    public Response<Boolean> saveTagWhole(String imageID, String description) {
        try {
            tagWholeDao.saveTagWhole(imageID, description);
            return new Response<>(true, "Succeed to save whole tag!");
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(false, "Fail to save whole tag!");
        }
    }

    @Override
    public Response<Boolean> deleteTagWhole(String imageID) {
        try{
            tagWholeDao.deleteTagWhole(imageID);
            return new Response<>(true, "Succeed to delete whole tag!");
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(false, "Fail to delete whole tag!");
        }
    }

    @Override
    public Response<Boolean> updateTagWhole(String imageID, String description) {
        try{
            tagWholeDao.updateTagWhole(imageID, description);
            return new Response<>(true, "Succeed to update whole tag!");
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(false, "Fail to update whole tag!");
        }
    }

    @Override
    public Response<Integer> getDescriptionLength(String imageID) {
        try {
            return new Response<>(true, tagWholeDao.getDescriptionLength(imageID), "Succeed " +
                    "to get length!");
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(false, "Fail to get length!");
        }
    }

}
