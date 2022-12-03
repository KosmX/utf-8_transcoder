package io.github.kosmx.transcoder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class Transcoder {
    final String regex;
    final Charset oldCharset;
    final Path workDir;


    public Transcoder(String regex, Charset charset, Path path) {
        this.regex = regex;
        this.oldCharset = charset;
        this.workDir = path;
    }

    public void process() throws IOException {
        var iterator = Files.find(workDir, Integer.MAX_VALUE, (path, basicFileAttributes) -> path.getFileName().toString().matches(regex)).iterator();
        while (iterator.hasNext()){
            //var file = iterator.next();
            Path path = iterator.next();
            //System.out.println("Opening " + path.getFileName());

            try{
                var open = new TestedFile(path, 1024*1024);
                if(!open.isUTF8()){
                    System.out.println(path.getFileName() + "\tis local encoded, recoding to UTF-8");
                    open.writeAncConvert(path, oldCharset);
                }
                else {
                    System.out.println(path.getFileName() + "\tis UTF-8 encoded");
                }

            }catch (IOException e){
                System.out.println(e.getMessage());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
