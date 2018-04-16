package com.utag.phase1.util;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FileToolTest {

    @Test
    public void listPictureName() {
        List<String> list = FileTool.listPictureName("data/");
        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
        }
        assertEquals(5, list.size());
    }
}