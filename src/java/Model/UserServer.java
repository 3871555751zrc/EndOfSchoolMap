/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import  java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author zhengrucong
 */
public class UserServer extends  Server{

    public UserServer() {
       super();
      
    }
    public void run(){
        try {
            if(this._reqJsonObj.get("reqType").equals("REGISTER")){
                this.register();
            }
            if(this._reqJsonObj.get("reqType").equals("LOGIN")){
                this.login();//登录
            }
            else if(this._reqJsonObj.get("reqType").equals("FORGETPASSWORD")){
                this.forgetPassword();//忘记密码
            }
            else if(this._reqJsonObj.get("reqType").equals("CHANGEPASSWORD")){
                this.changePassword();//改变密码
            }
            else if(this._reqJsonObj.get("reqType").equals("PUBLISHMARKINF")){
                this.publishMarkInf();
            }
            else if(this._reqJsonObj.get("reqType").equals("GETMYPUBLISHINF")){
                this.getMyPublishInf();
            }
            else if(this._reqJsonObj.get("reqType").equals("QUERYINFBYID")){
                this.queryInfById();
            }
            else if(this._reqJsonObj.get("reqType").equals("COMMENT")){
                this.commentMarkInf();
            }
            else if(this._reqJsonObj.get("reqType").equals("LOADCOMMENT")){
                 this.loadComment();
            }
            else if(this._reqJsonObj.get("reqType").equals("SPATIALQUE")){
                this.spatialQuery();//空间查询
            }
            
        } catch (JSONException | SQLException ex) {
            Logger.getLogger(UserServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void register() throws JSONException, SQLException {
       //To change body of generated methods, choose Tools | Templates.
       
       
       String username = this._reqJsonObj.getString("username");
       String password = this._reqJsonObj.getString("password");
       String findPassQueAnw = this._reqJsonObj.getString("findPassQuAnw");//找回密码的部分
       String sql1 = "insert into sm_user(username,password) values('"+username+"','"+password+"');";
       String sql2 = "insert into sm_userfoundpassword values('"+username+"','"+findPassQueAnw+"');";
       Statement st1 = this._connection.createStatement();
       Statement st2 = this._connection.createStatement();
       int rs1=0,rs2=0;
       rs1 = st1.executeUpdate(sql1);
       rs2 = st2.executeUpdate(sql2);
       JSONObject jsonObj = new JSONObject();
       if(rs1!=-1 && rs2!=-1){
        jsonObj.put("success",true);
        jsonObj.put("message", "注册");
       }
       else{
        jsonObj.put("success",false);
        jsonObj.put("message", "注册失败"); 
       }
       this._responseJsonObj = jsonObj;

        
    }

    private void publishMarkInf() throws SQLException, JSONException {
        int itemtypeid = this._reqJsonObj.getInt("itemtypeid");
        String title = this._reqJsonObj.getString("title");
        String content = this._reqJsonObj.getString("content");
        String imagefilename = this._reqJsonObj.getString("imagefilename");
        String positionStr = this._reqJsonObj.getString("positionStr");
        java.sql.Timestamp publishtime = new java.sql.Timestamp(new java.util.Date().getTime());
        int userid = (int) this._session.getAttribute("userid");
       String sql = "insert into sm_markinf(itemtypeid,title,content,imagefilename,publishtime,userid,positionstr) values(?,?,?,?,?,?,?)";
        PreparedStatement pst = this._connection.prepareStatement(sql);
        pst.setInt(1,itemtypeid);
        pst.setString(2,title);
        pst.setString(3, content);
        pst.setString(4, imagefilename);
        pst.setTimestamp(5,publishtime);
        pst.setInt(6, userid);
        pst.setString(7,positionStr);
       int result =  pst.executeUpdate();
       if(result !=-1){
           this._responseJsonObj.put("success", true);
           this._responseJsonObj.put("message","ok");
       }
       else {
            this._responseJsonObj.put("success", false);
           this._responseJsonObj.put("message","failure");
       }
    }

    private void getMyPublishInf() throws JSONException, SQLException {
      //To change body of generated methods, choose Tools | Templates.
      String sql = "select * from sm_markinf where userid = "+this._session.getAttribute("userid");
//      String sql = "select * from sm_markinf where userid = "+this._reqJsonObj.getInt("userid");
      Statement st = this._connection.createStatement();
      ResultSet rs = st.executeQuery(sql);
      ArrayList responseDataArr = new ArrayList();
//                                         数据表 "public.sm_markinf"
//     栏位      |            类型             |                      修饰词
//---------------+-----------------------------+--------------------------------------------------
// id            | integer                     | 非空 默认 nextval('sm_markinf_id_seq'::regclass)
// itemtypeid    | integer                     |
// title         | text                        |
// content       | text                        |
// imagefilename | text                        |
// publishtime   | timestamp without time zone |
// userid        | integer                     |
// positionstr   | text                        |
      boolean success = false;
      while(rs.next()){
         JSONObject myPublishInfData = new JSONObject();
         myPublishInfData.put("id",rs.getInt(1));
         myPublishInfData.put("itemtypeid",rs.getInt(2));
         myPublishInfData.put("title",rs.getString(3));
         myPublishInfData.put("content", rs.getString(4));
         myPublishInfData.put("imagefilename", rs.getString(5));
         myPublishInfData.put("pulishtime", rs.getTimestamp(6));
         myPublishInfData.put("userid",rs.getInt(7));
         myPublishInfData.put("positionstr",rs.getString(8));
         responseDataArr.add(myPublishInfData);//添加数据到数组里面
         success = true;
      }
      
      this._responseJsonObj.put("success", success);
      this._responseJsonObj.put("responseDataArr", responseDataArr);
    }

    private void queryInfById() throws JSONException, SQLException {
         //To change body of generated methods, choose Tools | Templates.
          String sql = "select * from sm_markinf where id = "+this._reqJsonObj.getInt("infId");
          Statement st = this._connection.createStatement();
          ResultSet rs = st.executeQuery(sql);
          JSONObject queryInfData = new JSONObject();
           boolean success = false;
          while(rs.next()){
         queryInfData.put("id",rs.getInt(1));
         queryInfData.put("itemtypeid",rs.getInt(2));
         queryInfData.put("title",rs.getString(3));
         queryInfData.put("content", rs.getString(4));
         queryInfData.put("imagefilename", rs.getString(5));
         queryInfData.put("pulishtime", rs.getTimestamp(6));
         queryInfData.put("userid",rs.getInt(7));
         queryInfData.put("positionstr",rs.getString(8));
         success = true;
      }
      
      this._responseJsonObj.put("success", success);
      this._responseJsonObj.put("queryInfData", queryInfData);
    }

    private void commentMarkInf() throws SQLException, JSONException {
       
        
//                                 数据表 "public.sm_markinf_comment"
//     栏位     |            类型             |                          修饰词
//--------------+-----------------------------+----------------------------------------------------------
// id           | integer                     | 非空 默认 nextval('sm_markinf_comment_id_seq'::regclass)
// markinfid    | integer                     |
// commenttext  | text                        |
// commentterid | integer                     |
// commenttime  | timestamp without time zone |
         
         int markinfid = this._reqJsonObj.getInt("infId");
         String commentText = this._reqJsonObj.getString("commentText");
         int currUserid = (int) this._session.getAttribute("userid");
         java.sql.Timestamp commetTimestamp = new java.sql.Timestamp(new java.util.Date().getTime());
         String sql = "insert into sm_markinf_comment(markinfid,commenttext,commentterid,commenttime) values(?,?,?,?)";
         PreparedStatement pst = this._connection.prepareStatement(sql);
         pst.setInt(1, markinfid);
         pst.setString(2, commentText);
         pst.setInt(3,currUserid);
         pst.setTimestamp(4, commetTimestamp);
        int result =  pst.executeUpdate();
        if(result!=-1){
            this._responseJsonObj.put("success", true);
            this._responseJsonObj.put("message", "评论成功！");
        }
        else {
             this._responseJsonObj.put("success", false);
            this._responseJsonObj.put("message", "评论失败！");
        }
        
    }

    private void spatialQuery() throws JSONException, SQLException {
                  //To change body of generated methods, choose Tools | Templates.
      String sql = "select * from sm_markinf";
      Statement st = this._connection.createStatement();
      ResultSet rs = st.executeQuery(sql);
      ArrayList responseDataArr = new ArrayList();
//                                         数据表 "public.sm_markinf"
//     栏位      |            类型             |                      修饰词
//---------------+-----------------------------+--------------------------------------------------
// id            | integer                     | 非空 默认 nextval('sm_markinf_id_seq'::regclass)
// itemtypeid    | integer                     |
// title         | text                        |
// content       | text                        |
// imagefilename | text                        |
// publishtime   | timestamp without time zone |
// userid        | integer                     |
// positionstr   | text                        |
      boolean success = false;
      while(rs.next()){
         JSONObject myPublishInfData = new JSONObject();
         myPublishInfData.put("id",rs.getInt(1));
         myPublishInfData.put("itemtypeid",rs.getInt(2));
         myPublishInfData.put("title",rs.getString(3));
         myPublishInfData.put("content", rs.getString(4));
         myPublishInfData.put("imagefilename", rs.getString(5));
         myPublishInfData.put("pulishtime", rs.getTimestamp(6));
         myPublishInfData.put("userid",rs.getInt(7));
         myPublishInfData.put("positionstr",rs.getString(8));
         responseDataArr.add(myPublishInfData);//添加数据到数组里面
         success = true;
      }
      
      this._responseJsonObj.put("success", success);
      this._responseJsonObj.put("responseDataArr", responseDataArr);
    }

    private void loadComment() {
        //To change body of generated methods, choose Tools | Templates.
        
    }

    private void forgetPassword() throws JSONException, SQLException {
         //To change body of generated methods, choose Tools | Templates.
         
         String username = this._reqJsonObj.getString("username");//获取用户名
         String anwser = this._reqJsonObj.getString("anwser");
         String foundAnwserSql = "select anwser from sm_userfoundpassword where username ='"+username+"'; ";
         Statement st = this._connection.createStatement();
         ResultSet rs =  st.executeQuery(foundAnwserSql);
         while(rs.next()){
             if(anwser.equals(rs.getString(1))){
                 this._responseJsonObj.put("success", true);
                 this._responseJsonObj.put("message", "ok");
             }
             else {
                 this._responseJsonObj.put("success", false);
                 this._responseJsonObj.put("message", "invalid anwser");
             }
         }
         
         
    }

    private void login() throws JSONException, SQLException {
        String username  = this._reqJsonObj.getString("username");//获取用户名
        String password = this._reqJsonObj.getString("password");
        String sql = "select password,userid from sm_user where username = '"+username+"';";
        Statement st = this._connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        boolean hasRegister = false;
        JSONObject jsonObj = new JSONObject();
        while(rs.next()){
            hasRegister = true;
            if(password.equals(rs.getString(1))){
               jsonObj.put("success", true);//登录成功！
                 this._session.setAttribute("userid",rs.getInt(2));//设置用户的id,此时已经登录了！
            }
            else{
                jsonObj.put("success", false);
                jsonObj.put("message", "你的用户名或者密码输入错误，请重新输入");
            }
        }
        if(!hasRegister){
            jsonObj.put("success",false);
            jsonObj.put("messsge", "你还没有注册！请先去注册");
        }
        
        this._responseJsonObj = jsonObj;
        
    }

    private void changePassword() throws JSONException, SQLException {
       String username = this._reqJsonObj.getString("username");
       String newPassword = this._reqJsonObj.getString("newPassword");
       String sql  = "update sm_user set password = '"+newPassword+"' where username = '"+username+"';";
       Statement st = this._connection.createStatement();
       int result = st.executeUpdate(sql);
       if(result !=-1){
           this._responseJsonObj.put("success", true);
           this._responseJsonObj.put("message", "密码修改成功！");
       }
       else {
           this._responseJsonObj.put("success", false);
           this._responseJsonObj.put("message","密码更新失败！");
       }
       
    }
 
}
