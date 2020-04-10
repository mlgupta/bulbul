package test;

import java.io.*;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import java.util.*;

public class SaveAsJPEG {

    public static void main(String [] args) throws Exception {

        // create a JPEG transcoder
        JPEGTranscoder t = new JPEGTranscoder();
        // set the transcoding hints
        t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY,
                             new Float(.8));
                             	
        t.addTranscodingHint(JPEGTranscoder.KEY_HEIGHT, new Float(500));

        // create the transcoder input
        String svgURI = new File("D:\\Sudheer\\Bulbul\\SVG Programing\\DesignEngine\\DesignEngine\\Results\\SampleSVG\\svgimage.svg").toURL().toString();
        TranscoderInput input = new TranscoderInput(svgURI);
        // create the transcoder output
        OutputStream ostream = new FileOutputStream("D:\\Sudheer\\Bulbul\\SVG Programing\\DesignEngine\\DesignEngine\\Results\\out_" + GregorianCalendar.getInstance().getTime().getTime()  + ".jpg");
        TranscoderOutput output = new TranscoderOutput(ostream);
        // save the image
        t.transcode(input, output);
        
        // flush and close the stream then exit
        ostream.flush();
        ostream.close();
        System.exit(0);
    }
}