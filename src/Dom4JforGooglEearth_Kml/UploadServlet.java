package Dom4JforGooglEearth_Kml;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    //上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "upload";
    //private static String uploadFilePath;

    //上传配置
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;   // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;     // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;  // 50MB
    private static ArrayList flist = new ArrayList();
    int flag=0;
    /**
     * 上传数据及保存文件
     */
    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException {
        //检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            //如果不是则停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须包含 enctype = multipart/form-data");
            writer.flush();
            return;
        }

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值（包含文件和表单数据）
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 中文处理
        upload.setHeaderEncoding("UTF-8");

        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
        String uploadPath = getServletContext().getRealPath("/") + UPLOAD_DIRECTORY;
        System.out.println(uploadPath);
        System.out.println(getServletContext().getRealPath("/"));

        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }


        try {
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // 控制台输出文件的上传路径

                        //uploadFilePath = filePath;
                        //flist= new  java.util.ArrayList();
                        if(flist.isEmpty()) {
                            System.out.println("1=="+filePath);
                            flist.add(filePath);
                        }else{
                            System.out.println("2=="+filePath);
                            flist.clear();
                            flist.add(filePath);
                        }
                        System.out.println("333"+flist.size()+flist.get(0));
                        //getUploadFilePath(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        request.setAttribute("message" , "文件上传成功！");  //需要修改，做判断，是否上传成功
                    }
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message" , "错误信息：" + ex.getMessage());
        }
        // 跳转到 message.jsp
        //getServletContext().getRequestDispatcher("/webapp/jsp/message.jsp").forward(request , response);
        // 跳转到kml文件解析页面
        response.sendRedirect("/hello");
        flag++;
    }
    protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException {
        //doPost(request , response);
        request.getRequestDispatcher("/webapp/jsp/upload.jsp").forward(request , response);
    }

    public static java.util.ArrayList getUploadFilePath() {
        //stem.out.println("333"+flist.size()+flist.get(0));
        return flist;
    }
}
