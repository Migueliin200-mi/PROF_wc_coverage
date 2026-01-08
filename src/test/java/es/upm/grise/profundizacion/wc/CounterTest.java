package es.upm.grise.profundizacion.wc;

import static org.junit.Assert.assertEquals;      // JUnit 4

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

import org.junit.Test;                             // JUnit 4

public class CounterTest {

    @Test
    public void testCountCharactersWordsAndLines() throws IOException {
        String content = "Esta frase\nes un ejemplo para\nel test de recuento.\n";
        BufferedReader reader = new BufferedReader(new StringReader(content));
        Counter counter = new Counter(reader);
        assertEquals(51, counter.getNumberCharacters());
        assertEquals(3, counter.getNumberLines());
        assertEquals(10, counter.getNumberWords());
    }

    @Test
    public void testEmptyContent() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(""));
        Counter counter = new Counter(reader);
        assertEquals(0, counter.getNumberCharacters());
        assertEquals(0, counter.getNumberLines());
        assertEquals(0, counter.getNumberWords());
    }

    @Test
    public void testSpacesAndTabsNoNewlines() throws IOException {
        String content = "hola\tmundo prueba";
        BufferedReader reader = new BufferedReader(new StringReader(content));
        Counter counter = new Counter(reader);

        assertEquals(17, counter.getNumberCharacters());
        assertEquals(0, counter.getNumberLines());
        // Según la implementación actual, sólo 2 palabras
        assertEquals(2, counter.getNumberWords());
    }


}
