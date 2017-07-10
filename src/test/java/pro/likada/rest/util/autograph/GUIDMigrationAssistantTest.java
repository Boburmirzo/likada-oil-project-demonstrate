package pro.likada.rest.util.autograph;


import org.junit.Assert;
import org.junit.Test;


/**
 * Created by abuca on 24.03.17.
 */

public class GUIDMigrationAssistantTest {
    private GUIDMigrationAssistant assistant = new GUIDMigrationAssistant();

    @Test
    public void govNumberConverterTest(){
        String oldGovNumber = "968туе";
        String newGovNumber = "Т968УЕ 116";
        String testGovNumber = assistant.convertGovNumber(oldGovNumber);
        Assert.assertEquals(newGovNumber,testGovNumber);
    }
}