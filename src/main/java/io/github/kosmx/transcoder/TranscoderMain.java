package io.github.kosmx.transcoder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.file.Path;

public class TranscoderMain {
    public static void main(String[] args) {
        if(args.length < 2){
            System.out.println("Usage:" +
                    "program [file regex] [old charset] [(optional) work directory]");
        }


        try {
            Charset charset = Charset.forName(args[1]);

            new Transcoder(args[0], charset, args.length < 3 ? Path.of("") : Path.of(args[2])).process();

        }catch (IllegalCharsetNameException | IOException e) {
            System.out.println(args[1] + " is not a valid charset: " + e.getMessage());
        }

    }
}
