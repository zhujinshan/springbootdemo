package com.zhujs;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by jinshan.zhu@luckincoffee.com
 * Date: 2018/10/10
 * Time: 16:57
 */
public class Test {

    public static void main(String[] args) {
        Path path = Paths.get("D:/phones.txt");

        Path pathOut = Paths.get("D:/phonesOut.txt");

        if (Files.notExists(pathOut)) {
            try {
                Files.createFile(pathOut);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (Files.exists(path)) {
            try {
                List<String> phones = Files.readAllLines(path);

                BufferedWriter writer = Files.newBufferedWriter(pathOut, StandardCharsets.UTF_8);
                for (int i = 0; i < phones.size(); i++) {
                    String p = phones.get(i);
                    if(StringUtils.isEmpty(p)){
                        continue;
                    }
                    String md5 = DigestUtils.md5DigestAsHex(p.getBytes());
                    System.out.println(i + ":" + p + ":" + md5);
                    writer.write(md5);
                    writer.newLine();
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
