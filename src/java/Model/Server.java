/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import org.json.JSONObject;

/**
 *
 * @author zhengrucong
 */
public class Server {
    public JSONObject _reqJsonObj = null;
    public JSONObject _responseJsonObj = null;
    public HttpSession    _session = null ;
    public Connection _connection = null;
    
    public Server(){
        this._responseJsonObj = new JSONObject();
    }
    public void setRequest(JSONObject _reqJsonObj){
        this._reqJsonObj = _reqJsonObj;
    }
    public JSONObject getResponse(){
        return this._responseJsonObj;
    }
    public void openDB() throws ClassNotFoundException, SQLException{
            Class.forName("org.postgresql.Driver"); 
            String url = "jdbc:postgresql://127.0.0.1:5432/schoolmapdb"; 
            this._connection = DriverManager.getConnection(url, "postgres", "123456"); 
    }
    public void closeDB() throws SQLException{
        this._connection.close();
    }

    public void setRequest(String requestObj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void run(){
        
    }
    public boolean  isHasLogin(){
        Object obj = this._session.getAttribute("userid");
        if(obj == null){
            return false;
        }
        else {
            return true;
        }
    }
    
            
}
