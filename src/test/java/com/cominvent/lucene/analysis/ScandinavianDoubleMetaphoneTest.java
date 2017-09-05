package com.cominvent.lucene.analysis;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author magnus.ebbesson
 */
public class ScandinavianDoubleMetaphoneTest {

    ScandinavianDoubleMetaphone edm;
    String testName1;
    String testName2;

    public ScandinavianDoubleMetaphoneTest() {
    }

    @Before
    public void setUp() {
        edm = new ScandinavianDoubleMetaphone();
        edm.setMaxCodeLen(10);
    }

    @Test
    public void testMultiple() {
        testName1 = "Rooth";
        ArrayList<String> results = edm.encode(testName1, 4, true);
        assertTrue("Did not get two results", results.size() == 2);
        assertFalse("The two results where equal", results.get(0).equals(results.get(1)));

    }

    @Test
    public void falsifyMetephone() {
        testName1 = "Chlaes";
        testName2 = "Karl";
        assertFalse("Names did match", metaphoneEquals(testName1, testName2));
    }

    @Test
    public void validateSS() {
        testName1 = "bernson";
        testName2 = "bernsson";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));
    }

    @Test
    public void validateSilentT() {
        testName1 = "berntsson";
        testName2 = "bernsson";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));
    }

    @Test
    public void validateAE() {
        testName1 = "Älander";
        testName2 = "Elander";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));
    }

    @Test
    public void validateOo() {
        testName1 = "Root";
        testName2 = "Rot";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));

    }

    @Test
    public void validateTh() {
        testName1 = "Roth";
        testName2 = "Rot";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));

    }

    @Test
    public void validateQv() {
        testName1 = "Bergqvist";
        testName2 = "Bergkvist";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));

    }

    @Test
    public void validateQu() {
        testName1 = "Bergquist";
        testName2 = "bergkvist";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));

    }

    @Test
    public void validateKC() {
        testName1 = "Kristina";
        testName2 = "cristina";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));

    }

    @Test
    public void validateCk() {
        testName1 = "kvick";
        testName2 = "kvik";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));

    }

    @Test
    public void validateGj() {
        testName1 = "Jertrud";
        testName2 = "Gjertrud";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));

    }

    @Test
    public void validateHj() {
        testName1 = "Jalmar";
        testName2 = "Hjalmar";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));

    }

    @Test
    public void validateLj() {
        testName1 = "Jungmann";
        testName2 = "Ljungmann";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));

    }

    @Test
    public void validateCKK() {
        testName1 = "Joackim";
        testName2 = "Joakim";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));

    }

    @Test
    public void validatdepf() {
        testName1 = "Christopfer";
        testName2 = "Christoffer";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));

    }

    @Test
    public void validateCh() {
        testName1 = "Kristoffer";
        testName2 = "Christoffer";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));

    }

    @Test
    public void validateCK() {
        testName1 = "Joacim";
        testName2 = "Joakim";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));
    }

    @Test
    public void validateAsch() {
        testName1 = "Färs";
        testName2 = "Färsch";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));
    }

    @Test
    public void validateSilentDJ() {
        testName1 = "Jur";
        testName2 = "Djur";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));
    }

    @Test
    public void validateKQ() {
        testName1 = "Joaqim";
        testName2 = "Joakim";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));

    }
    
    
    @Test
    public void validateSS_S() {
        
        testName1 = "ebbesson";
        testName2 = "ebbeson";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));
        
        testName1 = "Färss";
        testName2 = "Färs";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));

      

    }
    
   

    @Test
    public void validateTJsound() {
        testName1 = "körarvägen";
        testName2 = "tjörarvägen";
        String testName3 = "chörarvägen";
        String testName4 = "schörarvägen";
        String testName5 = "shörarvägen";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));
        assertTrue("Names did not match", metaphoneEquals(testName1, testName3));
        assertTrue("Names did not match", metaphoneEquals(testName1, testName4));
        assertTrue("Names did not match", metaphoneEquals(testName1, testName5));
    }

    @Test
    public void validateKJsound() {
        testName1 = "Kjerstin";
        testName2 = "Kerstin";
        String testName3 = "Kjärstin";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));
        assertTrue("Names did not match", metaphoneEquals(testName1, testName3));
    }

    @Test
    public void validatePh() {
        testName1 = "Kristopher";
        testName2 = "Christoffer";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));

    }

    @Test
    public void validateSilentDS() {
        testName1 = "Mads";
        testName2 = "Mas";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));
    }
    
    @Test
    public void validateDanishPaul_au_av() {
        testName1 = "Pau";
        testName2 = "Pav";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));
    }
    
    @Test
    public void validateDanishPaul_au_aw() {
        testName1 = "Pau";
        testName2 = "Paw";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));
    }
    
    @Test
    public void validateDanishPaul_aw_aw() {
        testName1 = "Pav";
        testName2 = "Paw";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));
    }
    
    @Test
    public void validateDanishNyan() {
        testName1 = "Anja";
        testName2 = "Anya";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));
    }
    
    @Test
    public void validateDanishNyanNyan() {
        testName1 = "Enjo";
        testName2 = "Enyo";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));
    }
    
    @Test
    public void validateDanishEieEje() {
        testName1 = "Meier";
        testName2 = "Mejer";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));
    }
    
    @Test
    public void validateDanishEieEye() {
        testName1 = "Meier";
        testName2 = "Meyer";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));
    }

    @Test
    public void validateDanishEjeEye() {
        testName1 = "Meier";
        testName2 = "Mejer";
        assertTrue("Names did not match", metaphoneEquals(testName1, testName2));
    }
    
    private boolean metaphoneEquals(String testName1, String testName2) {

        ArrayList<String> results1 = edm.encode(testName1, 4, true);
        ArrayList<String> results2 = edm.encode(testName2, 4, true);

        for (String result1 : results1) {
            for (String result2 : results2) {
                if (result1.equals(result2)) {
                    return true;
                }
            }
        }

        return false;
    }

    @After
    public void tearDown() {
    }
}
