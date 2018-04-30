package com.utag.phase1.dao;

import com.utag.phase1.dao.DaoService.TagPartDao;
import com.utag.phase1.domain.TagPart;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.junit.Assert.*;

public class TagPartDaoImplTest {
    private TagPartDao tagPartDao = new TagPartDaoImpl();

    @Test
    public void saveTagPart() throws IOException{
        assertEquals(true, tagPartDao.saveTagPart("1", 0, 1, 0, 1, "test1"));
        assertEquals(true, tagPartDao.saveTagPart("2", 0, 2, 0, 0, "test2"));
        assertEquals(true, tagPartDao.saveTagPart("3", 0, 3, 0, 3, "test3"));
    }

    @Test
    public void showTagPart() throws IOException{
        assertEquals(0, tagPartDao.showTagPart("1").size());
        assertEquals(0, tagPartDao.showTagPart("2").size());
        assertEquals(0, tagPartDao.showTagPart("3").size());

    }

    @Test
    public void deleteTagPart() throws IOException{
        assertEquals(true, tagPartDao.deleteTagPart("src/main/resources/static/task/files/IMG_8503.JPG"));
    }

    @Test
    public void updateTagPart() throws IOException{
        assertEquals(true, tagPartDao.updateTagPart("1", 0, 1, 0, 1, "update"));
        assertEquals(true, tagPartDao.updateTagPart("2", 0, 2, 0, 0, "update"));
        assertEquals(true, tagPartDao.updateTagPart("3", 0, 3, 0, 3, "update"));
    }

    @Test
    public void getDescriptionLengthTest() throws IOException{
        assertEquals(0, tagPartDao.getDescriptionLength("1", 0, 1, 0, 1));
        assertEquals(0, tagPartDao.getDescriptionLength("1", 1, 0, 1, 0));
    }

    @Test
    public void listTagPart() throws IOException{
        assertEquals(3, tagPartDao.listPartTag("src/main/resources/static/task/files/照片图库.png").size());
    }
}