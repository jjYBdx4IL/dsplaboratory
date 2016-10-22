
/**
 * Write a description of class FiltruFisierRaw here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package dsplaboratory.auraw;
import javax.swing.filechooser.FileFilter;
import java.io.*;
public class FiltruFisierRaw extends FileFilter
{
public FiltruFisierRaw()
{
}
public  boolean accept(File pathname)
{ 
 return pathname.getAbsolutePath().endsWith(".raw");
}
public String getDescription()
{
  return "Fisiere raw (*.raw)";
}
}
