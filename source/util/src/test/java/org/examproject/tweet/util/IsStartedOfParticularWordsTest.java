/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.examproject.tweet.util;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author SCN
 */
public class IsStartedOfParticularWordsTest {
    
    public IsStartedOfParticularWordsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of evaluate method, of class IsStartedOfParticularWords.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        IsStartedOfParticularWords instance = new IsStartedOfParticularWords();
        
        String str1 = "RT ";
        boolean expResult1 = true;
        boolean result1 = instance.evaluate(str1);
        assertEquals(expResult1, result1);
        
        String str2 = "@";
        boolean expResult2 = true;
        boolean result2 = instance.evaluate(str2);
        assertEquals(expResult2, result2);
        
        String str3 = ".";
        boolean expResult3 = true;
        boolean result3 = instance.evaluate(str3);
        assertEquals(expResult3, result3);
        
        String str4 = "#";
        boolean expResult4 = true;
        boolean result4 = instance.evaluate(str4);
        assertEquals(expResult4, result4);
        
        String str5 = "http://";
        boolean expResult5 = true;
        boolean result5 = instance.evaluate(str5);
        assertEquals(expResult5, result5);
        
        String str6 = "MT ";
        boolean expResult6 = true;
        boolean result6 = instance.evaluate(str6);
        assertEquals(expResult6, result6);
        
        String str7 = "https://";
        boolean expResult7 = true;
        boolean result7 = instance.evaluate(str7);
        assertEquals(expResult7, result7);
        
    }
}