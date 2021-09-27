package GeneralFiles;


import BaseFiles.TestBase;
import com.mongodb.DBCursor;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;

import org.bson.Document;
import org.testng.Assert;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class MongoDbConnection {

    int port_no = 27017;
    String auth_user=EncryptUtils.getRealData("UserName"),
            auth_pwd =EncryptUtils.getRealData("Password"),
            host_name = "Host",
            db_name = "nap_poc";

    private MongoClient getConnection(){
        MongoClient client =null;
        try {
            auth_pwd=URLEncoder.encode(auth_pwd, StandardCharsets.UTF_8.name());
            client = MongoClients.create("mongodb://"+auth_user+":"+auth_pwd+"@"+host_name+":"+port_no+"");
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return client;
    }

    public void deleteDataInTheColumn(String columnName, String fieldName,String value){
        try {
            MongoDatabase db = getConnection().getDatabase(db_name);
            MongoCollection<Document> collection = db.getCollection(columnName);
            collection.deleteMany(Filters.eq(fieldName, value));
            System.out.println("Document deleted successfully...");
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }

    public  Document getDocument(String columnName, String fieldName,String value){
        Document myDoc=null;
        try {
            MongoDatabase db = getConnection().getDatabase(db_name);
            MongoCollection<Document> collection = db.getCollection(columnName);
            myDoc =collection.find(Filters.eq(fieldName, value)).first();
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return myDoc;
    }

    public boolean findDataInTheColumn(String columnName, String fieldName,String value){
        boolean dataExist=false;
        try {
            Document myDoc =getDocument(columnName,fieldName,value);
            if (myDoc==null){
                dataExist=false;
            }else {
                dataExist=true;
            }
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return dataExist;
    }

    public String getTheFieldsValue(String columnName, String referansFieldName,String referansValue,String fieldName){
        String data="";
        try {
            Document doc=getDocument(columnName,referansFieldName,referansValue);
            data=doc.get(fieldName).toString();

        }catch (Exception e){
            e.getStackTrace();
        }
        return data;
    }




}
