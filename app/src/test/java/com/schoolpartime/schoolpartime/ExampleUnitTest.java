package com.schoolpartime.schoolpartime;

import com.schoolpartime.schoolpartime.util.DateUtils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(30,DateUtils.getMaxDayInOneMonth(2019,4) );
    }
}