package es.upm.grise.profundizacion.wc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AppTest {

    private static Path testFile = Paths.get("ejemplo.txt");
    private static PrintStream originalOut;

    @BeforeClass
    public static void setup() throws IOException {
        originalOut = System.out;
        String content = "kjdbvws wonvwofjw\n sdnfwijf ooj kjndfohwouer 21374 vehf\n jgfosj\n\nskfjwoief ewjf\n\n\ndkfgwoihgpw vs wepfjwfin";
        Files.write(testFile, content.getBytes("UTF-8"));
    }

    @AfterClass
    public static void teardown() {
        System.setOut(originalOut);
        try {
            Files.deleteIfExists(testFile);
        } catch (IOException e) {
            // si falla el borrado no es crítico para la lógica
        }
    }

    @Test
    public void testUsageMessageWhenNoArgs() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        App.main(new String[] {});
        assertEquals("Usage: wc [-clw file]".trim(), output.toString().trim());
    }

    @Test
    public void testWrongArgumentsMoreThanTwo() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        App.main(new String[] {"-clw", "file1.txt", "extra"});
        assertEquals("Wrong arguments!", output.toString().trim());
    }

    @Test
    public void testFileNotFound() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        App.main(new String[] {"-clw", "no_existe.txt"});
        assertEquals("Cannot find file: no_existe.txt", output.toString().trim());
    }

    @Test
    public void testCommandsWithoutDash() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        App.main(new String[] {"clw", testFile.toString()});
        assertEquals("The commands do not start with -", output.toString().trim());
    }

    @Test
    public void testValidCommandsClw() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        App.main(new String[] {"-clw", testFile.toString()});
        String result = output.toString().trim();

        assertTrue(result.endsWith(testFile.toString()));
        String[] parts = result.split("\\t");
        assertEquals(4, parts.length);
    }

    @Test
    public void testUnrecognizedCommand() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        App.main(new String[] {"-cx", testFile.toString()});
        assertEquals("Unrecognized command: x", output.toString().trim());
    }
}
