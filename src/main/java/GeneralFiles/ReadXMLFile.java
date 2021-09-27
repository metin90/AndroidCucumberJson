package GeneralFiles;

import org.w3c.dom.NodeList;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


public class ReadXMLFile {

    private  NodeList getList(String xmlPath,String tagName){
        NodeList nodeList=null;
        try {
            //creating a constructor of file class and parsing an XML file
            File file = new File(xmlPath);
//an instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//an instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            nodeList = doc.getElementsByTagName(tagName);

        }catch (Exception e){
            e.getStackTrace();
        }
        return nodeList;
    }

    public  String getDataInPropertiesTag(String elementNameInTag){
        String data="";
        try {
            NodeList nodeList=getList(Data.pomXMLPath,"properties");
            for (int itr = 0; itr < nodeList.getLength(); itr++)
            {
                Node node = nodeList.item(itr);
                System.out.println("\nNode Name :" + node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) node;
                    data=eElement.getElementsByTagName(elementNameInTag).item(0).getTextContent();
                    System.out.println("Element in The Tag: "+data);
                }
            }
        }catch (Exception e){
            e.getStackTrace();
        }
        return data;
    }

}
