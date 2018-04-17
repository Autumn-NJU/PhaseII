package com.utag.phase1.dao;

import com.utag.phase1.dao.DaoService.PictureDao;
import com.utag.phase1.domain.Picture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class PictureDaoImplTest {
    @Autowired
    private PictureDao pictureDao;

    private Picture picture = new Picture(1, "cat", "worker1");

    @Test
    public void tagPicture() {
        assertEquals(true, pictureDao.tagPicture(picture.getId()));
    }

    @Test
    public void listUntaggedPicture() {

    }

    @Test
    public void listPictureName(){
        assertEquals(9, pictureDao.listPictureName().size());
    }
}