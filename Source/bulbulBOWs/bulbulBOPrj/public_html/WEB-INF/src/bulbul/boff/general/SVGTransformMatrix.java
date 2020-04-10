package bulbul.boff.general;

import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


/**
 * @author Sudheer V. Pujar
 * @version 1.0
 * @Date of creation: dd-Aug-2005
 * @Last Modfied by : Saurabh gupta
 * @Last Modfied Date: 04-Oct-2006
 */
public final class SVGTransformMatrix  {
  private AffineTransform transform=null;
  private DecimalFormat format;

  /**
   * 
   * @description 
   */
  public SVGTransformMatrix(double a, double b, double c, double d, double e, double f) {
    transform=new AffineTransform(a, b, c, d, e, f);
    
    //sets the format object that will be used to convert a double value into a string
    DecimalFormatSymbols symbols=new DecimalFormatSymbols();
    symbols.setDecimalSeparator('.');
    format=new DecimalFormat("############.################", symbols);
  }

  /**
   * 
   * @description 
   * @return 
   */
  public double getA(){
    double[] matrix=new double[6];
    transform.getMatrix(matrix);
    return matrix[0];
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public double getB(){
    double[] matrix=new double[6];
    transform.getMatrix(matrix);
    return matrix[1];
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public double getC(){
    double[] matrix=new double[6];
    transform.getMatrix(matrix);
    return matrix[2];
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public double getD(){
    double[] matrix=new double[6];
    transform.getMatrix(matrix);
    return matrix[3];
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public double getE(){
    double[] matrix=new double[6];
    transform.getMatrix(matrix);
    return matrix[4];
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public double getF(){
    double[] matrix=new double[6];
    transform.getMatrix(matrix);
    return matrix[5];
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public AffineTransform getTransform(){
    return transform;
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public String getMatrixRepresentation(){
    double[] matrix=new double[6];
    transform.getMatrix(matrix);

    String matrixRepresentation="matrix(";
    matrixRepresentation=matrixRepresentation.concat(" "+format.format(matrix[0])+",");
    matrixRepresentation=matrixRepresentation.concat(" "+format.format(matrix[1])+",");			
    matrixRepresentation=matrixRepresentation.concat(" "+format.format(matrix[2])+",");
    matrixRepresentation=matrixRepresentation.concat(" "+format.format(matrix[3])+",");			
    matrixRepresentation=matrixRepresentation.concat(" "+format.format(matrix[4])+",");		
    matrixRepresentation=matrixRepresentation.concat(" "+format.format(matrix[5]));			
    matrixRepresentation=matrixRepresentation.concat(")");
    
    return matrixRepresentation;
  }
  
  /**
   * 
   * @description 
   * @param matrix
   */
  public void concatenateMatrix(SVGTransformMatrix matrix){
    AffineTransform newTransform=new AffineTransform(	matrix.getA(),
                                                      matrix.getB(), 
                                                      matrix.getC(), 
                                                      matrix.getD(), 
                                                      matrix.getE(), 
                                                      matrix.getF());
		transform.preConcatenate(newTransform);
  }
  
  /**
   * 
   * @description 
   * @param af
   */
  public void concatenateTransform(AffineTransform af){
    if(af!=null){
      transform.preConcatenate(af);
		}
  }
  
  /**
   * 
   * @description 
   * @param f
   * @param e
   */
  public void concatenateTranslate(double e, double f){
    transform.preConcatenate(AffineTransform.getTranslateInstance(e, f));      
  }
  
  /**
   * 
   * @description 
   * @param angle
   */
  public void concatenateRotate(double angle){
    transform.preConcatenate(AffineTransform.getRotateInstance(angle));
  }
  
  /**
   * 
   * @description 
   * @param d
   * @param a
   */
  public void concatenateScale(double a, double d){
    transform.preConcatenate(AffineTransform.getScaleInstance(a, d));
  }
  
  /**
   * 
   * @description 
   */
  public void concatenateHorizontalFlip(){
    transform.preConcatenate(AffineTransform.getScaleInstance(-1, 1));
  }
  
  /**
   * 
   * @description 
   */
  public void concatenateVerticalFlip(){
    transform.preConcatenate(AffineTransform.getScaleInstance(1, -1));
  }
  
  /**
   * 
   * @description 
   * @param angle
   */
  public void concatenateSkewX(double angle){
    transform.preConcatenate(AffineTransform.getShearInstance(Math.tan(angle), 0));
  }
  
  /**
   * 
   * @description 
   * @param angle
   */
  public void concatenateSkewY(double angle){
    transform.preConcatenate(AffineTransform.getShearInstance(0, Math.tan(angle)));
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public boolean isMatrixCorrect(){
    boolean isNotCorrect=false;
    isNotCorrect=isNotCorrect || Double.isNaN(getA()) || Double.isInfinite(getA());
    isNotCorrect=isNotCorrect || Double.isNaN(getB()) || Double.isInfinite(getB());		    
    isNotCorrect=isNotCorrect || Double.isNaN(getC()) || Double.isInfinite(getC());
    isNotCorrect=isNotCorrect || Double.isNaN(getD()) || Double.isInfinite(getD());
    isNotCorrect=isNotCorrect || Double.isNaN(getE()) || Double.isInfinite(getE());
    isNotCorrect=isNotCorrect || Double.isNaN(getF()) || Double.isInfinite(getF());
		return ! isNotCorrect;
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public SVGTransformMatrix cloneMatrix(){
    double[] matrix=new double[6];
    transform.getMatrix(matrix);
    return new SVGTransformMatrix(matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5]);
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public boolean isIdentity(){
    return transform.isIdentity();
  }
  
  /**
   * 
   * @description 
   * @return 
   */
  public String toString(){
    double[] matrix=new double[6];
    transform.getMatrix(matrix);
    String display="";
    for(int i=0;i<6;i++) display=display.concat(matrix[i]+" ");
    return display;

  }
}

