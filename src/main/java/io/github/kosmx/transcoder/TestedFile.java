package io.github.kosmx.transcoder;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class TestedFile {
    AtomicBoolean isValidUTF8 = null; //default to this.
    ByteBuffer buffer;

    /**
     * Create a new file to test
     * @param filePath the file, to read in
     * @param maxSize maximum size to read
     * @throws IOException if something goes wrong
     */
    public TestedFile(Path filePath, int maxSize) throws IOException {

        try (InputStream stream = Files.newInputStream(filePath)){
            buffer = ByteBuffer.wrap(stream.readNBytes(maxSize));

            //If it couldn't read the whole file, we have an issue...
            if(stream.read() != -1){
                throw new IOException("File is larger than maximum allowed size: " + maxSize);
            }
        }catch (IOException e){
            throw e;
        }catch (Exception e){
            throw new IOException("Exception occurred while opening/reading file: " + e.getMessage());
        }
    }

    public boolean isUTF8(){
        if(isValidUTF8 == null){
            testFile();
        }
        return Objects.requireNonNullElse(isValidUTF8, new AtomicBoolean(true)).get();
    }

    private void testFile(){
        //set it to true, and if we find an invalid character, we change it to false
        int bufPos = buffer.position();
        buffer.rewind();
        isValidUTF8 = new AtomicBoolean(true);

        while (buffer.hasRemaining() && isValidUTF8.get()){
            if(!validateNextChar(buffer)){
                isValidUTF8.set(false);
            }
        }
        buffer.position(bufPos);
    }

    /**
     * Checks the next character if it is valid
     * @param buffer the buffer to check
     * @return true, if valid
     */
    public static boolean validateNextChar(ByteBuffer buffer){
        byte b = buffer.get();
        int readPosition = buffer.position();
        if((b ^ 0xFF) == 0) return false;
        if((b & 0x80) == 0) return true;
        b <<= 1;
        while ((b & 0x80) != 0){
            b <<= 1;
            if(((buffer.get() ^ 0x80) & 0xC0) != 0){
                buffer.position(readPosition); //rewind it to next char
                return false; //not valid UTF8 char
            }
        }
        return true;
    }

    /**
     *
     * @param outputStream write data into file
     * @param oldCharset the old local charset
     */
    public void writeAncConvert(OutputStream outputStream, Charset oldCharset) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {

            try (InputStream inputStream = new ByteArrayInputStream(buffer.array())) {
                try (InputStreamReader reader = new InputStreamReader(inputStream, oldCharset)) {

                    while (true) {
                        int c = reader.read();
                        if (c == -1) break;

                        writer.write(c);
                    }
                }
            }
        }
    }

    /**
     *
     * @param outputPath write data into file
     * @param oldCharset the old local charset
     */
    public void writeAncConvert(Path outputPath, Charset oldCharset) throws IOException{
        try (OutputStream stream = Files.newOutputStream(outputPath)){
            writeAncConvert(stream, oldCharset);
        }
        //Don't catch anything, just close the stream...
    }
}
