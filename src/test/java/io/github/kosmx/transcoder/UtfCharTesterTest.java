package io.github.kosmx.transcoder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class UtfCharTesterTest {

    @Test
    public void testUtf8(){
        String utf8str = "some random ๐ฐ๏ธstring๐ฆ with๐ฅ๐๐ฐ๐ฎ๐๐งฆ๐ UTF-8 characters";
        ByteBuffer buffer = ByteBuffer.wrap(utf8str.getBytes(StandardCharsets.UTF_8));

        while (buffer.hasRemaining()) {
            Assertions.assertTrue(TestedFile.validateNextChar(buffer));
        }
    }


}
