package com.utag.phase1.util;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FileToolTest {

    @Test
    public void listPictureName() {
       List<String> list1 = FileTool.listPictureName(1 + "");
       assertEquals(7, list1.size());
       for(int i = 0; i < list1.size(); i++) {
           System.out.println(list1.get(i));
       }
    }
}