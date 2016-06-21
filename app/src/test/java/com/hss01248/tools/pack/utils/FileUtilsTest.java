package com.hss01248.tools.pack.utils;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public class FileUtilsTest {

    @Test
    public void testGetFileName() throws Exception {
       String dd =  FileUtils.getFileName("https://americanbridgepac.org/app/uploads/unnamed-6.gif?imageomre/jdsj/dse=iui") ;
        Assert.assertEquals("error","unnamed-6.gif",dd);

    }
}