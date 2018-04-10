package com.utag.phase1.service.Impl;

import com.utag.phase1.dao.DaoService.TagWholeDao;
import com.utag.phase1.service.TagWholeService;
import com.utag.phase1.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.crypto.RsaMd5CksumType;

import java.io.IOException;

@Service
public class TagWholeServiceImpl implements TagWholeService {

    @Autowired
    private TagWholeDao tagWholeDao;

    @Override
    public Response<Boolean> saveTagWhole(String imageID, String description) throws IOException{
        try {
            tagWholeDao.saveTagWhole(imageID, description);
            return new Response<>(true, "Succeed to save whole tag!");
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(false, "Fail to save whole tag!");
        }
    }

    @Override
    public Response<Boolean> deleteTagWhole(String imageID) throws IOException{
        try{
            tagWholeDao.deleteTagWhole(imageID);
            return new Response<>(true, "Succeed to delete whole tag!");
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(false, "Fail to delete whole tag!");
        }
    }

    @Override
    public Response<Boolean> updateTagWhole(String imageID, String description) throws IOException{
        try{
            tagWholeDao.updateTagWhole(imageID, description);
            return new Response<>(true, "Succeed to update whole tag!");
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(false, "Fail to update whole tag!");
        }
    }

    @Override
    public Response<Integer> getDescriptionLength(String imageID) throws IOException {
        try {
            return new Response<>(true, tagWholeDao.getDescriptionLength(imageID), "Succeed " +
                    "to get length!");
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(false, "Fail to get length!");
        }
    }
}
