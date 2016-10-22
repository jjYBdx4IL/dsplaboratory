
/**
 * Write a description of class FiltruFisierAu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package dsplaboratory.auraw;
import javax.swing.filechooser.FileFilter;
import java.io.*;
public class FiltruFisierAu extends FileFilter
{
public FiltruFisierAu()
{
}
public  boolean accept(File pathname)
{ 
 return pathname.getAbsolutePath().endsWith(".au");
}
public String getDescription()
{
  return "Fisiere au (*.au)";
}
}
