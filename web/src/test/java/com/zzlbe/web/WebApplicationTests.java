package com.zzlbe.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebApplicationTests {

    @Test
    public void contextLoads() {
    }

    private static void outputToFile(String message) throws IOException {
        File file = new File("E:\\duduFile\\temp\\area.json");
        boolean newFile = file.createNewFile();
        if (!newFile) {
            throw new RuntimeException("error");
        }
        BufferedWriter output = new BufferedWriter(new FileWriter(file));
        output.write(message);
        output.close();
    }

}
