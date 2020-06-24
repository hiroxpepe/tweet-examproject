/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.examproject.tweet.util;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * @author h.adachi
 */
public class IsEnglishSentencePredicateTest {
    
    public IsEnglishSentencePredicateTest() {
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
     * Test of evaluate method, of class IsEnglishSentencePredicate.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        
        IsEnglishSentencePredicate instance = new IsEnglishSentencePredicate();
        
        boolean expResult1 = true;
        boolean result1 = instance.evaluate("This is a pen.");
        assertEquals(expResult1, result1);
        
        boolean expResult2 = false;
        boolean result2 = instance.evaluate("これはペンです。");
        assertEquals(expResult2, result2);
    }
}
