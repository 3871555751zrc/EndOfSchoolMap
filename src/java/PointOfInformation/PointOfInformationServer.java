/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PointOfInformation;

import Model.Server;
import Model.UserServer;
import java.sql.DriverManager;
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
public class PointOfInformationServer extends  Server{
    public PointOfInformationServer(){
        super();
    }
    
    @Override  //重写了connection，让 他们能够连接dely数据库
    public void openDB() throws ClassNotFoundException, SQLException{
            Class.forName("org.postgresql.Driver"); 
            String url = "jdbc:postgresql://127.0.0.1:5432/dely"; 
            this._connection = DriverManager.getConnection(url, "postgres", "123456"); 
    }
    public void run(){
//  1 | 甜点咖啡       |     98 | 2017-09-13 00:53:42.325636
//  2 | 韩日料理及西餐 |    132 | 2017-09-13 00:54:11.4133
//  3 | 湘菜           |    196 | 2017-09-13 00:54:54.396759
//  4 | 小吃快餐       |     50 | 2017-09-13 00:55:08.774581
//  5 | 火锅           |    101 | 2017-09-13 00:55:26.549598
//  6 | 烧烤           |     52 | 2017-09-13 00:55:42.830529
//  7 | 海鲜           |     41 | 2017-09-13 00:56:11.52617
//  8 | 家常菜         |      5 | 2017-09-13 00:56:29.063173
//  9 | 其他           |     57 | 2017-09-13 00:56:45.95814
    try {
            if(this._reqJsonObj.get("reqType").equals("TIANDIANCAFEI")){
                this.loadTianDianCaFei();
            }
            if(this._reqJsonObj.get("reqType").equals("HANSHILIAOLIJIXICAN")){
                this.loadHanShiLiaoLiJiXiCan();//韩式料理及西餐
            }
            else if(this._reqJsonObj.get("reqType").equals("XIANGCAI")){
                this.loadXiangCai();//湘菜
            }
            else if(this._reqJsonObj.get("reqType").equals("XIAOCHIKUAICAN")){
                this.loadXiaoChiKuaiCan();
            }
            else if(this._reqJsonObj.get("reqType").equals("HUOGUO")){
                this.loadHuoGuo();
            }
            else if(this._reqJsonObj.get("reqType").equals("SHAOKAO")){
                this.loadShaoKao();
            }
            else if(this._reqJsonObj.get("reqType").equals("HAIXIAN")){
                this.LoadHaiXian();
            }   
            
        } catch (JSONException ex) {
            Logger.getLogger(UserServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PointOfInformationServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   

    private void loadHanShiLiaoLiJiXiCan() throws SQLException, JSONException {
       String sql = "select A.* ,B.lat,B.lng from foodinformation A,location B where A.typeoffood='韩日料理及西餐' limit 500; "; 
//        select A.* ,B.lat,B.lng from foodinformation A,location B where A.typeoffood='湘菜' limit 2;
// id |       title        | typeoffood |              location               | pertakes | audientideas | scoreoftest | scoreofevrio | scoreofwaiter |        registertime        |       lat       |       lng
//----+--------------------+------------+-------------------------------------+----------+--------------+-------------+--------------+---------------+----------------------------+-----------------+------------------
//  2 | 岳阳小家庭         | 湘菜       | 黄兴南路45号                        |       86 | 33           |         9.1 |          9.1 |             9 | 2017-09-12 15:26:21.920047 | 28.179262067797 | 112.992411522218
//  3 | 悦来湘精品湘菜餐厅 | 湘菜       | 芙蓉中路88号天健芙蓉盛世2-4号栋二楼 |       59 | 24           |         7.7 |          7.7 |           8.1 | 2017-09-12 15:26:21.96405  | 28.179262067797 | 112.992411522218
//        
        ArrayList responseDataArr = new ArrayList();//集合框架
        Statement st = this._connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        boolean success = false;
        while(rs.next()){
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("id", rs.getInt(1));
            jsonObj.put("title",rs.getString(2));
            jsonObj.put("typeoffood", rs.getString(3));
            jsonObj.put("location", rs.getString(4));
            jsonObj.put("pertakes", rs.getDouble(5));
            jsonObj.put("audientideas", rs.getDouble(6));
            jsonObj.put("scoreoftest", rs.getDouble(7));
            jsonObj.put("scoreofevrio", rs.getDouble(8));
            jsonObj.put("scoreofwaiter", rs.getDouble(9));
            jsonObj.put("lat", rs.getDouble(11));
            jsonObj.put("lng", rs.getDouble(12));
            responseDataArr.add(jsonObj);
            success = true;
        }
        this._responseJsonObj.put("success", success);
        this._responseJsonObj.put("responseDataArr", responseDataArr);
        
    }

    private void loadXiangCai() throws SQLException, JSONException {
       String sql = "select A.* ,B.lat,B.lng from foodinformation A,location B where A.typeoffood='湘菜' limit 500;";
       //        select A.* ,B.lat,B.lng from foodinformation A,location B where A.typeoffood='湘菜' limit 2;
// id |       title        | typeoffood |              location               | pertakes | audientideas | scoreoftest | scoreofevrio | scoreofwaiter |        registertime        |       lat       |       lng
//----+--------------------+------------+-------------------------------------+----------+--------------+-------------+--------------+---------------+----------------------------+-----------------+------------------
//  2 | 岳阳小家庭         | 湘菜       | 黄兴南路45号                        |       86 | 33           |         9.1 |          9.1 |             9 | 2017-09-12 15:26:21.920047 | 28.179262067797 | 112.992411522218
//  3 | 悦来湘精品湘菜餐厅 | 湘菜       | 芙蓉中路88号天健芙蓉盛世2-4号栋二楼 |       59 | 24           |         7.7 |          7.7 |           8.1 | 2017-09-12 15:26:21.96405  | 28.179262067797 | 112.992411522218
//        
        ArrayList responseDataArr = new ArrayList();//集合框架
        Statement st = this._connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        boolean success = false;
        while(rs.next()){
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("title",rs.getString(2));
            jsonObj.put("typeoffood", rs.getString(3));
            jsonObj.put("location", rs.getString(4));
            jsonObj.put("pertakes", rs.getDouble(5));
            jsonObj.put("audientideas", rs.getDouble(6));
            jsonObj.put("scoreoftest", rs.getDouble(7));
            jsonObj.put("scoreofevrio", rs.getDouble(8));
            jsonObj.put("scoreofwaiter", rs.getDouble(9));
            jsonObj.put("lat", rs.getDouble(11));
            jsonObj.put("lng", rs.getDouble(12));
            responseDataArr.add(jsonObj);
            success = true;
        }
        this._responseJsonObj.put("success", success);
        this._responseJsonObj.put("responseDataArr", responseDataArr);
    }

    private void loadXiaoChiKuaiCan() throws SQLException, JSONException {
        String sql = "select A.* ,B.lat,B.lng from foodinformation A,location B where A.typeoffood='小吃快餐' limit 500;";
        //        select A.* ,B.lat,B.lng from foodinformation A,location B where A.typeoffood='湘菜' limit 2;
// id |       title        | typeoffood |              location               | pertakes | audientideas | scoreoftest | scoreofevrio | scoreofwaiter |        registertime        |       lat       |       lng
//----+--------------------+------------+-------------------------------------+----------+--------------+-------------+--------------+---------------+----------------------------+-----------------+------------------
//  2 | 岳阳小家庭         | 湘菜       | 黄兴南路45号                        |       86 | 33           |         9.1 |          9.1 |             9 | 2017-09-12 15:26:21.920047 | 28.179262067797 | 112.992411522218
//  3 | 悦来湘精品湘菜餐厅 | 湘菜       | 芙蓉中路88号天健芙蓉盛世2-4号栋二楼 |       59 | 24           |         7.7 |          7.7 |           8.1 | 2017-09-12 15:26:21.96405  | 28.179262067797 | 112.992411522218
//        
        ArrayList responseDataArr = new ArrayList();//集合框架
        Statement st = this._connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        boolean success = false;
        while(rs.next()){
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("title",rs.getString(2));
            jsonObj.put("typeoffood", rs.getString(3));
            jsonObj.put("location", rs.getString(4));
            jsonObj.put("pertakes", rs.getDouble(5));
            jsonObj.put("audientideas", rs.getDouble(6));
            jsonObj.put("scoreoftest", rs.getDouble(7));
            jsonObj.put("scoreofevrio", rs.getDouble(8));
            jsonObj.put("scoreofwaiter", rs.getDouble(9));
            jsonObj.put("lat", rs.getDouble(11));
            jsonObj.put("lng", rs.getDouble(12));
            responseDataArr.add(jsonObj);
            success = true;
        }
        this._responseJsonObj.put("success", success);
        this._responseJsonObj.put("responseDataArr", responseDataArr);
    }

    private void loadHuoGuo() throws SQLException, JSONException {
       String sql = "select A.* ,B.lat,B.lng from foodinformation A,location B where A.typeoffood='火锅' limit 500;";
       //        select A.* ,B.lat,B.lng from foodinformation A,location B where A.typeoffood='湘菜' limit 2;
// id |       title        | typeoffood |              location               | pertakes | audientideas | scoreoftest | scoreofevrio | scoreofwaiter |        registertime        |       lat       |       lng
//----+--------------------+------------+-------------------------------------+----------+--------------+-------------+--------------+---------------+----------------------------+-----------------+------------------
//  2 | 岳阳小家庭         | 湘菜       | 黄兴南路45号                        |       86 | 33           |         9.1 |          9.1 |             9 | 2017-09-12 15:26:21.920047 | 28.179262067797 | 112.992411522218
//  3 | 悦来湘精品湘菜餐厅 | 湘菜       | 芙蓉中路88号天健芙蓉盛世2-4号栋二楼 |       59 | 24           |         7.7 |          7.7 |           8.1 | 2017-09-12 15:26:21.96405  | 28.179262067797 | 112.992411522218
//        
        ArrayList responseDataArr = new ArrayList();//集合框架
        Statement st = this._connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        boolean success = false;
        while(rs.next()){
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("title",rs.getString(2));
            jsonObj.put("typeoffood", rs.getString(3));
            jsonObj.put("location", rs.getString(4));
            jsonObj.put("pertakes", rs.getDouble(5));
            jsonObj.put("audientideas", rs.getDouble(6));
            jsonObj.put("scoreoftest", rs.getDouble(7));
            jsonObj.put("scoreofevrio", rs.getDouble(8));
            jsonObj.put("scoreofwaiter", rs.getDouble(9));
            jsonObj.put("lat", rs.getDouble(11));
            jsonObj.put("lng", rs.getDouble(12));
            responseDataArr.add(jsonObj);
            success = true;
        }
        this._responseJsonObj.put("success", success);
        this._responseJsonObj.put("responseDataArr", responseDataArr);
    }

    private void loadShaoKao() throws SQLException, JSONException {
      String sql = "select A.* ,B.lat,B.lng from foodinformation A,location B where A.typeoffood='烧烤' limit 500;";
      //        select A.* ,B.lat,B.lng from foodinformation A,location B where A.typeoffood='湘菜' limit 2;
// id |       title        | typeoffood |              location               | pertakes | audientideas | scoreoftest | scoreofevrio | scoreofwaiter |        registertime        |       lat       |       lng
//----+--------------------+------------+-------------------------------------+----------+--------------+-------------+--------------+---------------+----------------------------+-----------------+------------------
//  2 | 岳阳小家庭         | 湘菜       | 黄兴南路45号                        |       86 | 33           |         9.1 |          9.1 |             9 | 2017-09-12 15:26:21.920047 | 28.179262067797 | 112.992411522218
//  3 | 悦来湘精品湘菜餐厅 | 湘菜       | 芙蓉中路88号天健芙蓉盛世2-4号栋二楼 |       59 | 24           |         7.7 |          7.7 |           8.1 | 2017-09-12 15:26:21.96405  | 28.179262067797 | 112.992411522218
//        
        ArrayList responseDataArr = new ArrayList();//集合框架
        Statement st = this._connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        boolean success = false;
        while(rs.next()){
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("title",rs.getString(2));
            jsonObj.put("typeoffood", rs.getString(3));
            jsonObj.put("location", rs.getString(4));
            jsonObj.put("pertakes", rs.getDouble(5));
            jsonObj.put("audientideas", rs.getDouble(6));
            jsonObj.put("scoreoftest", rs.getDouble(7));
            jsonObj.put("scoreofevrio", rs.getDouble(8));
            jsonObj.put("scoreofwaiter", rs.getDouble(9));
            jsonObj.put("lat", rs.getDouble(11));
            jsonObj.put("lng", rs.getDouble(12));
            responseDataArr.add(jsonObj);
            success = true;
        }
        this._responseJsonObj.put("success", success);
        this._responseJsonObj.put("responseDataArr", responseDataArr);
    }

    private void LoadHaiXian() throws SQLException, JSONException {
       String sql = "select A.* ,B.lat,B.lng from foodinformation A,location B where A.typeoffood='海鲜' limit 500;";
       //        select A.* ,B.lat,B.lng from foodinformation A,location B where A.typeoffood='湘菜' limit 2;
// id |       title        | typeoffood |              location               | pertakes | audientideas | scoreoftest | scoreofevrio | scoreofwaiter |        registertime        |       lat       |       lng
//----+--------------------+------------+-------------------------------------+----------+--------------+-------------+--------------+---------------+----------------------------+-----------------+------------------
//  2 | 岳阳小家庭         | 湘菜       | 黄兴南路45号                        |       86 | 33           |         9.1 |          9.1 |             9 | 2017-09-12 15:26:21.920047 | 28.179262067797 | 112.992411522218
//  3 | 悦来湘精品湘菜餐厅 | 湘菜       | 芙蓉中路88号天健芙蓉盛世2-4号栋二楼 |       59 | 24           |         7.7 |          7.7 |           8.1 | 2017-09-12 15:26:21.96405  | 28.179262067797 | 112.992411522218
//        
        ArrayList responseDataArr = new ArrayList();//集合框架
        Statement st = this._connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        boolean success = false;
        while(rs.next()){
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("title",rs.getString(2));
            jsonObj.put("typeoffood", rs.getString(3));
            jsonObj.put("location", rs.getString(4));
            jsonObj.put("pertakes", rs.getDouble(5));
            jsonObj.put("audientideas", rs.getDouble(6));
            jsonObj.put("scoreoftest", rs.getDouble(7));
            jsonObj.put("scoreofevrio", rs.getDouble(8));
            jsonObj.put("scoreofwaiter", rs.getDouble(9));
            jsonObj.put("lat", rs.getDouble(11));
            jsonObj.put("lng", rs.getDouble(12));
            responseDataArr.add(jsonObj);
            success = true;
        }
        this._responseJsonObj.put("success", success);
        this._responseJsonObj.put("responseDataArr", responseDataArr);
    }

    private void loadTianDianCaFei() throws SQLException, JSONException {
       String sql = "select A.* ,B.lat,B.lng from foodinformation A,location B where A.typeoffood='甜点咖啡' limit 500; ";
       //        select A.* ,B.lat,B.lng from foodinformation A,location B where A.typeoffood='湘菜' limit 2;
// id |       title        | typeoffood |              location               | pertakes | audientideas | scoreoftest | scoreofevrio | scoreofwaiter |        registertime        |       lat       |       lng
//----+--------------------+------------+-------------------------------------+----------+--------------+-------------+--------------+---------------+----------------------------+-----------------+------------------
//  2 | 岳阳小家庭         | 湘菜       | 黄兴南路45号                        |       86 | 33           |         9.1 |          9.1 |             9 | 2017-09-12 15:26:21.920047 | 28.179262067797 | 112.992411522218
//  3 | 悦来湘精品湘菜餐厅 | 湘菜       | 芙蓉中路88号天健芙蓉盛世2-4号栋二楼 |       59 | 24           |         7.7 |          7.7 |           8.1 | 2017-09-12 15:26:21.96405  | 28.179262067797 | 112.992411522218
//        
        ArrayList responseDataArr = new ArrayList();//集合框架
        Statement st = this._connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        boolean success = false;
        while(rs.next()){
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("title",rs.getString(2));
            jsonObj.put("typeoffood", rs.getString(3));
            jsonObj.put("location", rs.getString(4));
            jsonObj.put("pertakes", rs.getDouble(5));
            jsonObj.put("audientideas", rs.getDouble(6));
            jsonObj.put("scoreoftest", rs.getDouble(7));
            jsonObj.put("scoreofevrio", rs.getDouble(8));
            jsonObj.put("scoreofwaiter", rs.getDouble(9));
            jsonObj.put("lat", rs.getDouble(11));
            jsonObj.put("lng", rs.getDouble(12));
            responseDataArr.add(jsonObj);
            success = true;
        }
        this._responseJsonObj.put("success", success);
        this._responseJsonObj.put("responseDataArr", responseDataArr);
    }
}
