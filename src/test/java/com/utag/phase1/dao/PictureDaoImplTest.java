package com.utag.phase1.dao;

import com.utag.phase1.dao.DaoService.PictureDao;
import com.utag.phase1.domain.Picture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

public class PictureDaoImplTest {

    private PictureDao pictureDao = new PictureDaoImpl();

    private Picture picture = new Picture(1, "cat", "worker1");

    @Test
    public void tagPicture() {
        assertEquals(true, pictureDao.tagPicture("1-1-py"));
    }

    @Test
    public void listUntaggedPicture() {

    }

    @Test
    public void listPictureName(){
        assertEquals(7, pictureDao.listPictureName(1).size());
    }

    @Test
    public void savePicture(){
        assertEquals(true, pictureDao.savePicture(1, "py", "1"));
    }

    @Test
    public void testIsTagged(){
        assertEquals(true, pictureDao.isTagged(1, "1", "py"));
    }
}