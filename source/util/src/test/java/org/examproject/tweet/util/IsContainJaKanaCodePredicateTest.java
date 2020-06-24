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
public class IsContainJaKanaCodePredicateTest {
    
    public IsContainJaKanaCodePredicateTest() {
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
     * Test of evaluate method, of class IsContainJaKanaCodePredicate.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        IsContainJaKanaCodePredicate instance = new IsContainJaKanaCodePredicate();
        
        String str1 = "あいうえお";
        boolean expResult1 = true;
        boolean result1 = instance.evaluate(str1);
        assertEquals(expResult1, result1);
        
        String str2 = "アイウエオ";
        boolean expResult2 = true;
        boolean result2 = instance.evaluate(str2);
        assertEquals(expResult2, result2);
        
        String str3 = "ｱｲｳｴｵ";
        boolean expResult3 = true;
        boolean result3 = instance.evaluate(str3);
        assertEquals(expResult3, result3);
    }
}