/*

 ============================================================================
                   The Apache Software License, Version 1.1
 ============================================================================

 Copyright (C) 1999-2003 The Apache Software Foundation. All rights reserved.

 Redistribution and use in source and binary forms, with or without modifica-
 tion, are permitted provided that the following conditions are met:

 1. Redistributions of  source code must  retain the above copyright  notice,
    this list of conditions and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

 3. The end-user documentation included with the redistribution, if any, must
    include  the following  acknowledgment:  "This product includes  software
    developed  by the  Apache Software Foundation  (http://www.apache.org/)."
    Alternately, this  acknowledgment may  appear in the software itself,  if
    and wherever such third-party acknowledgments normally appear.

 4. The names "Batik" and  "Apache Software Foundation" must  not  be
    used to  endorse or promote  products derived from  this software without
    prior written permission. For written permission, please contact
    apache@apache.org.

 5. Products  derived from this software may not  be called "Apache", nor may
    "Apache" appear  in their name,  without prior written permission  of the
    Apache Software Foundation.

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS  FOR A PARTICULAR  PURPOSE ARE  DISCLAIMED.  IN NO  EVENT SHALL  THE
 APACHE SOFTWARE  FOUNDATION  OR ITS CONTRIBUTORS  BE LIABLE FOR  ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL,  EXEMPLARY, OR CONSEQUENTIAL  DAMAGES (INCLU-
 DING, BUT NOT LIMITED TO, PROCUREMENT  OF SUBSTITUTE GOODS OR SERVICES; LOSS
 OF USE, DATA, OR  PROFITS; OR BUSINESS  INTERRUPTION)  HOWEVER CAUSED AND ON
 ANY  THEORY OF LIABILITY,  WHETHER  IN CONTRACT,  STRICT LIABILITY,  OR TORT
 (INCLUDING  NEGLIGENCE OR  OTHERWISE) ARISING IN  ANY WAY OUT OF THE  USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 This software  consists of voluntary contributions made  by many individuals
 on  behalf of the Apache Software  Foundation. For more  information on the
 Apache Software Foundation, please see <http://www.apache.org/>.

*/

//Commented By Sudheer 

//package org.apache.batik.transcoder.wmf.tosvg;

//Added By Sudheer
package batik4bulbul;

//Added By Sudheer 
import org.apache.batik.transcoder.wmf.tosvg.*;

import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.batik.dom.svg.ExtensibleSVGDOMImplementation;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.AbstractTranscoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.util.SVGConstants;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.XMLFilter;
import org.apache.batik.swing.*;
import javax.swing.*;
import org.w3c.dom.svg.*;
import java.io.*;



/**
 * This class implements the <tt>Transcoder</tt> interface and
 * can convert a WMF input document into an SVG document.
 *
 * It can use <tt>TranscoderInput</tt> that are either a URI
 * or a <tt>InputStream</tt> or a <tt>Reader</tt>. The
 * <tt>XMLReader</tt> and <tt>Document</tt> <tt>TranscoderInput</tt>
 * types are not supported.
 *
 * This transcoder can use <tt>TranscoderOutputs</tt> that are
 * of any type except the <tt>XMLFilter</tt> type.
 *
 * @version $Id: WMFTranscoder.java,v 1.5 2003/08/08 11:39:26 vhardy Exp $
 * @author <a href="mailto:luano@asd.ie">Luan O'Carroll</a>
 */
public class BulbulWMFTranscoder extends AbstractTranscoder
    implements SVGConstants{

    /**
     * Error codes for the WMFTranscoder
     */
    public static final int WMF_TRANSCODER_ERROR_BASE = 0xff00;
    public static final int ERROR_NULL_INPUT = WMF_TRANSCODER_ERROR_BASE + 0;
    public static final int ERROR_INCOMPATIBLE_INPUT_TYPE = WMF_TRANSCODER_ERROR_BASE + 1;
    public static final int ERROR_INCOMPATIBLE_OUTPUT_TYPE = WMF_TRANSCODER_ERROR_BASE + 2;

    /**
     * Default constructor
     */
    public BulbulWMFTranscoder(){
    }

    /**
     * Transcodes the specified input in the specified output.
     * @param input the input to transcode
     * @param output the ouput where to transcode
     * @exception TranscoderException if an error occured while transcoding
     */
    public void transcode(TranscoderInput input, TranscoderOutput output )
        throws TranscoderException {
        //
        // Extract the input
        //
        DataInputStream is = getCompatibleInput(input);

        //
        // Build a RecordStore from the input
        //
        WMFRecordStore currentStore = new WMFRecordStore();
        try{
            currentStore.read(is);
        }catch(IOException e){
            handler.fatalError(new TranscoderException(e));
            return;
        }

        //
        // Build a painter for the RecordStore
        //
        WMFPainter painter = new WMFPainter(currentStore);

        //
        // Use SVGGraphics2D to generate SVG content
        //

//        DOMImplementation domImpl
//            = ExtensibleSVGDOMImplementation.getDOMImplementation();

        DOMImplementation domImpl = SVGDOMImplementation.getDOMImplementation();    
            
       
        SVGDocument doc = (SVGDocument)domImpl.createDocument(SVG_NAMESPACE_URI,
                                              SVG_SVG_TAG, null);

        
        SVGGraphics2D svgGenerator = new SVGGraphics2D(doc);
        painter.paint(svgGenerator);
        
        
            
        //
        // Set the size and viewBox on the output document
        //
        int vpX = currentStore.getVpX();
        int vpY = currentStore.getVpY();
        int vpW = currentStore.getVpW();
        int vpH = currentStore.getVpH();
        svgGenerator.setSVGCanvasSize(new Dimension(vpW, vpH));
        
        Element svgRoot = svgGenerator.getRoot(doc.getDocumentElement());   
        svgRoot.setAttributeNS(null, SVG_VIEW_BOX_ATTRIBUTE,
                               "" + vpX + " " + vpY + " " +
                               vpW + " " + vpH );

       
        //
        // Now, write the SVG content to the output
        //
        if (output.getDocument()!=null){
          output.setDocument(doc);  
        }else{
          writeSVGToOutput(svgGenerator, svgRoot, output);
        }

    }

    /**
     * Writes the SVG content held by the svgGenerator to the
     * <tt>TranscoderOutput</tt>.
     */
    private void writeSVGToOutput(SVGGraphics2D svgGenerator,
                                  Element svgRoot,
                                  TranscoderOutput output)
        throws TranscoderException {
        // XMLFilter
        XMLFilter xmlFilter = output.getXMLFilter();
        if(xmlFilter != null){
            handler.fatalError(new TranscoderException("" + ERROR_INCOMPATIBLE_OUTPUT_TYPE));
        }
        // <!> FIX ME: SHOULD HANDLE DOCUMENT INPUT
        Document doc = output.getDocument();
        if(doc != null){
          handler.fatalError(new TranscoderException("" + ERROR_INCOMPATIBLE_OUTPUT_TYPE));
        }
        try{

            // Output stream
            OutputStream os = output.getOutputStream();
            if( os != null ){
                svgGenerator.stream(svgRoot, new OutputStreamWriter(os));
                return;
            }

            // Writer
            Writer wr = output.getWriter();
            if( wr != null ){
                svgGenerator.stream(svgRoot, wr);
                return;
            }

            // URI
            String uri = output.getURI();
            if( uri != null ){
                try{
                    URL url = new URL(uri);
                    URLConnection urlCnx = url.openConnection();
                    os = urlCnx.getOutputStream();
                    svgGenerator.stream(svgRoot, new OutputStreamWriter(os));
                    return;
                }catch(MalformedURLException e){
                    handler.fatalError(new TranscoderException(e));
                }catch(IOException e){
                    handler.fatalError(new TranscoderException(e));
                }
            }
        }catch(IOException e){
            throw new TranscoderException(e);
        }

        throw new TranscoderException("" + ERROR_INCOMPATIBLE_OUTPUT_TYPE);

    }

    /**
     * Checks that the input is one of URI or an <tt>InputStream</tt>
     * returns it as a DataInputStream
     */
    private DataInputStream getCompatibleInput(TranscoderInput input)
        throws TranscoderException {
        // Cannot deal with null input
        if(input == null){
            handler.fatalError(new TranscoderException("" + ERROR_NULL_INPUT));
        }

        // Can deal with InputStream
        InputStream in = input.getInputStream();
        if(in != null){
            return new DataInputStream(new BufferedInputStream(in));
        }

        // Can deal with URI
        String uri = input.getURI();
        if(uri != null){
            try{
                URL url = new URL(uri);
                in = url.openStream();
                return new DataInputStream(new BufferedInputStream(in));
            }catch(MalformedURLException e){
                handler.fatalError(new TranscoderException(e));
            }catch(IOException e){
                handler.fatalError(new TranscoderException(e));
            }
        }
   
        handler.fatalError(new TranscoderException("" + ERROR_INCOMPATIBLE_INPUT_TYPE));
        return null;
    }

    public static final String USAGE = "The WMFTranscoder converts a WMF document into an SVG document. \n" +
        "This simple application generates SVG documents that have the same name, but a where the .wmf extension \n" +
        "is replaced with .svg. To run the application, type the following at the command line: \n" +
        "java org.apache.batik.transcoder.wmf.tosvg.WMFTranscoder fileName [fileName]+";

    public static final String WMF_EXTENSION = ".wmf";
    public static final String SVG_EXTENSION = ".svg";

    /**
     * Unit testing : Illustrates how the transcoder might be used.
     */
    public static void main(String args[]) throws TranscoderException {
        if(args.length < 1){
            System.err.println(USAGE);
            System.exit(1);
        }

        WMFTranscoder transcoder = new WMFTranscoder();
        int nFiles = args.length;

        for(int i=0; i<nFiles; i++){
            String fileName = args[i];
            if(!fileName.toLowerCase().endsWith(WMF_EXTENSION)){
                System.err.println(args[i] + " does not have the " + WMF_EXTENSION + " extension. It is ignored");
            }
            else{
                System.out.print("Processing : " + args[i] + "...");
                String outputFileName = fileName.substring(0, fileName.toLowerCase().indexOf(WMF_EXTENSION)) + SVG_EXTENSION;
                File inputFile = new File(fileName);
                File outputFile = new File(outputFileName);
                try{
                    TranscoderInput input = new TranscoderInput(inputFile.toURL().toString());
                    TranscoderOutput output = new TranscoderOutput(new FileOutputStream(outputFile));
                    transcoder.transcode(input, output);
                }catch(MalformedURLException e){
                    throw new TranscoderException(e);
                }catch(IOException e){
                    throw new TranscoderException(e);
                }
                System.out.println(".... Done");
            }
        }

        System.exit(0);
    }
}
