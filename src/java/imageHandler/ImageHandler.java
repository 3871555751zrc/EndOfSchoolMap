/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageHandler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author zhengrucong
 */
@WebServlet(name = "ImageHandler", urlPatterns = {"/ImageHandler"})
public class ImageHandler extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
  boolean isMultipart = ServletFileUpload.isMultipartContent(request);
  isMultipart = true;
  if(isMultipart==true){
      try{
          
        String uploadPath = "C:\\Users\\zhengrucong\\Documents\\NetBeansProjects\\SchoolMapBackEnd\\web\\images";//生成文件的物理保存地址 
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = upload.parseRequest(request);//得到所有的文件
       Iterator<FileItem> itr = items.iterator();
       String filenameStr = "";
        while(itr.hasNext()){//依次处理每个文件
         FileItem item=(FileItem)itr.next();
          String fileName=item.getName();//获得文件名，包括路径
         if(fileName!=null){
             File fullFile=new File(item.getName());
             File savedFile=new File(uploadPath,fullFile.getName());
             item.write(savedFile);
             filenameStr = fullFile.getName();
         }
        }
        
        out.print("http://176547or77.51mypc.cn/"+"SchoolMapBackEnd\\images\\"+filenameStr);
      }
      catch(Exception e){
         e.printStackTrace();
      }
  }
  else{
      out.println("the enctype must be multipart/form-data");
  }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
