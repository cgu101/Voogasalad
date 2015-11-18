package DESIGN.datafiles;

import java.io.FileOutputStream;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import DESIGN.authoring.IActor;

/*
 * http://stackoverflow.com/questions/13063815/save-xml-file-with-xstream
 */

public abstract class XMLSaver {
	private XStream mySerializer;
	
	public XMLSaver () {
		mySerializer = new XStream(new DomDriver());
	}
	
	public void save (IActor s) {
		String serializedActor = mySerializer.toXML(s);
        //System.out.println(serializedActor);
	}
	
	public void saveGame (GameData game, String filePath) {
    	FileOutputStream fos = null;
		try{            
			String xml = mySerializer.toXML(game);
			fos = new FileOutputStream(filePath);
			fos.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
			byte[] bytes = xml.getBytes("UTF-8");
			fos.write(bytes);
		}catch (Exception e){
			System.err.println("Error in XML Write: " + e.getMessage());
		}
		finally{
			if(fos != null){
				try{
					fos.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
}
