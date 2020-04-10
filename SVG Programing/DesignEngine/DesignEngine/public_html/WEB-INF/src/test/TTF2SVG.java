package test;
import org.apache.batik.svggen.font.*;

public class TTF2SVG  {
  public static void main(String[] args) {
   String ttfFilePath = "D:\\wingding.ttf";
   String forceAscii= "-ascii";
   String outputArg = "-o";
   String outputPath = "D:\\wingding.svg";
   String testCard = "-testcard";
   args = new String[5];
   args[0]=ttfFilePath;
   args[1]=forceAscii;
   args[2]=outputArg;
   args[3]=outputPath;
   args[4]=testCard;
   SVGFont.main(args); 
  }
}