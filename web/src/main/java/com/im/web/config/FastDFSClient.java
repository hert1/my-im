package com.im.web.config;

import org.apache.tomcat.util.codec.binary.Base64;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerServer;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author viruser
 * @create 2019/1/12
 * @since 1.0.0
 */
@Component
public class FastDFSClient {
    /**
     * 路径分隔符
     */
    public static final String SEPARATOR = "/";
    /**
     * Point
     */
    public static final String POINT = ".";
    /**
     * 文件类型
     */
    public static final Map<String, String> EXT_MAPS = new HashMap<>();

    /**
     * 文件名称
     */
    private static final String FILENAME = "filename";
    /**
     * 文件最大的大小
     */
    private int maxFileSize = 500 * 1024 * 1024;

    public FastDFSClient(){
        initExt();
    }

    private void initExt(){
        //图片
        EXT_MAPS.put("png", "image/png");
        EXT_MAPS.put("gif", "image/gif");
        EXT_MAPS.put("bmp", "image/bmp");
        EXT_MAPS.put("ico", "image/x-ico");
        EXT_MAPS.put("jpeg", "image/jpeg");
        EXT_MAPS.put("jpg", "image/jpeg");
        //压缩文件
        EXT_MAPS.put("zip", "application/zip");
        EXT_MAPS.put("rar", "application/x-rar");
        //word,excel,ppt
        EXT_MAPS.put("pdf", "application/pdf");
        EXT_MAPS.put("ppt", "application/vnd.ms-powerpoint");
        EXT_MAPS.put("xls", "application/vnd.ms-excel");
        EXT_MAPS.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        EXT_MAPS.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        EXT_MAPS.put("doc", "application/msword");
        EXT_MAPS.put("doc", "application/wps-office.doc");
        EXT_MAPS.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        EXT_MAPS.put("txt", "text/plain");
        //视频、音频
        EXT_MAPS.put("mp4", "video/mp4");
        EXT_MAPS.put("flv", "video/x-flv");
    }

    /***
     * 获取文件最大值
     * @return
     */
    public int getMaxFileSize(){
        return maxFileSize ;
    }

    /***
     * 设置文件的最大值
     * @param value
     */
    public void setMaxFileSize(int value){
        this.maxFileSize = value ;
    }

    /***
     * 获取FastDFS文件的名称，如：M00/00/00/rBBNW1sJCvCAHgH7AAVGh5jPYok707.jpg
     * @param fileid fileId 包含组名和文件名,如：group1/M00/00/00/rBBNW1sJCvCAHgH7AAVGh5jPYok707.jpg
     * @return
     */
    public static String getFilename(String fileid){

        String[] results = new String[2];
        StorageClient1.split_file_id(fileid,results);
        return results[1] ;
    }

    /***
     * 获取文件描述信息
     * @param filePath
     * @return
     * @throws Exception
     */
    public Map<String,Object> getFileDescriptions(String filePath)throws  Exception{
        //获取trackerserver
        TrackerServer trackerServer = TrackerServerPool.borrowObject();
        StorageClient1 storageClient1 = new StorageClient1(trackerServer,null) ;
        NameValuePair[] pairs = null ;
        //获取文件元数据
        pairs = storageClient1.get_metadata1(filePath) ;
        //回收trackerServer
        TrackerServerPool.recycleObject(trackerServer);

        Map<String, Object> infoMap = null;
        if (pairs != null && pairs.length > 0) {
            infoMap = new HashMap<>(pairs.length);

            for (NameValuePair pair : pairs) {
                infoMap.put(pair.getName(), pair.getValue());
            }
        }

        return infoMap;
    }

    /***
     * 获取源文件的文件名称
     * @param filePath
     * @return
     * @throws Exception
     */
    public String getOriginalFileName(String filePath) throws Exception{
        //获取文件的描述信息
        Map<String,Object> descriptions = getFileDescriptions(filePath);
        //获取文件名称
        if (descriptions.get(FILENAME)!=null){
            return (String) descriptions.get(FILENAME);
        }
        return null ;
    }

    /***
     * 获取文件名称的后缀
     * @param fileName
     * @return
     */
    public static String getFIleNameSuffix(String fileName){
        String suffix = null ;
        if (fileName!=null&&!"".equals(fileName)){
            if (fileName.contains(SEPARATOR)){
                fileName = fileName.substring(fileName.lastIndexOf(SEPARATOR) + 1);
            }
            if (fileName.contains(POINT)){
                suffix = fileName.substring(fileName.lastIndexOf(POINT) + 1);
            } else {

            }
        }

        return suffix ;
    }

    /***
     * 获取文件信息
     * @param filePath
     * @return
     * @throws Exception
     */
    public Map<String,Object> getFileInfo(String filePath) throws Exception{
        //获取trackerserver
        TrackerServer trackerServer = TrackerServerPool.borrowObject();
        StorageClient1 storageClient1 = new StorageClient1(trackerServer,null) ;
        FileInfo fileInfo = null ;
        //获取文件数据
        fileInfo = storageClient1.get_file_info1(filePath);

        // 返还对象
        TrackerServerPool.recycleObject(trackerServer);

        Map<String, Object> infoMap = new HashMap<>(4);
        //源IP
        infoMap.put("SourceIpAddr", fileInfo.getSourceIpAddr());
        //文件大小
        infoMap.put("FileSize", fileInfo.getFileSize());
        //创建时间
        infoMap.put("CreateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fileInfo.getCreateTimestamp()));
        //签名
        infoMap.put("CRC32", fileInfo.getCrc32());

        return infoMap;
    }

    /***
     * 转换路径中的 '\' 为 '/' ,并把文件后缀转为小写
     * @param path
     * @return
     */
    public static String toLocal(String path) {
        if (path!=null&&!"".equals(path)) {
            path = path.replaceAll("\\\\", SEPARATOR);

            if (path.contains(POINT)) {
                String pre = path.substring(0, path.lastIndexOf(POINT) + 1);
                String suffix = path.substring(path.lastIndexOf(POINT) + 1).toLowerCase();
                path = pre + suffix;
            }
        }
        return path;
    }

    /**
     * MultipartFile 上传文件
     *
     * @param file
     * @return 返回文件路径
     */
    public String uploadFileWithMultipart(MultipartFile file) throws Exception {
        return upload(file, null);
    }

    /**
     * MultipartFile 上传文件
     *
     * @param file
     * @param descriptions
     * @return 返回文件路径
     */
    public String uploadFileWithMultipart(MultipartFile file, Map<String, String> descriptions) throws Exception {
        return upload(file, descriptions);
    }

    /**
     * 根据指定的路径上传文件
     *
     * @param filepath
     * @return 返回文件路径
     */
    public String uploadFileWithFilepath(String filepath) throws Exception {
        return upload(filepath, null);
    }

    /**
     * 根据指定的路径上传文件
     *
     * @param filepath
     * @param descriptions
     * @return 返回文件路径
     */
    public String uploadFileWithFilepath(String filepath, Map<String, String> descriptions) throws Exception {
        return upload(filepath, descriptions);
    }

    /**
     * 上传base64文件
     *
     * @param base64
     * @return 返回文件路径
     */
    public String uploadFileWithBase64(String base64) throws Exception {
        return upload(base64, null, null);
    }

    /**
     * 上传base64文件
     *
     * @param base64
     * @param filename
     * @return 返回文件路径
     */
    public String uploadFileWithBase64(String base64, String filename) throws Exception {
        return upload(base64, filename, null);
    }

    /**
     * 上传base64文件
     *
     * @param base64
     * @param filename
     * @param descriptions
     * @return 返回文件路径
     */
    public String uploadFileWithBase64(String base64, String filename, Map<String, String> descriptions) throws Exception {
        return upload(base64, filename, descriptions);
    }

    /***
     * 上传通用方法
     * @param is 文件流
     * @param fileName 文件名称
     * @param descriptions
     * @return 组名+文件路径
     * @throws Exception
     */
    public String upload(InputStream is, String fileName, Map<String, String> descriptions) throws Exception {
        if(is == null){
            throw new Exception("文件为空！");
        }

        if(is.available() > maxFileSize){
            throw new Exception("文件太大！");
        }

        fileName = toLocal(fileName);
        // 返回路径
        String path = null;
        // 文件描述
        NameValuePair[] nvps = null;
        List<NameValuePair> nvpsList = new ArrayList<>();
        // 文件名后缀
        String suffix = getFIleNameSuffix(fileName);

        // 文件名
        if (fileName!=null&&!"".equals(fileName)) {
            nvpsList.add(new NameValuePair(FILENAME, fileName));
        }
        // 描述信息
        if (descriptions != null && descriptions.size() > 0) {
            descriptions.forEach((key, value) -> {
                nvpsList.add(new NameValuePair(key, value));
            });
        }
        if (nvpsList.size() > 0) {
            nvps = new NameValuePair[nvpsList.size()];
            nvpsList.toArray(nvps);
        }

        TrackerServer trackerServer = TrackerServerPool.borrowObject();
        StorageClient1 storageClient = new StorageClient1(trackerServer, null);
        try {
            // 读取流
            byte[] fileBuff = new byte[is.available()];
            is.read(fileBuff, 0, fileBuff.length);

            // 上传
            path = storageClient.upload_file1(fileBuff, suffix, nvps);

            if(path==null||"".equals(path)) {
                throw new Exception("上传失败！");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 回收对象
        TrackerServerPool.recycleObject(trackerServer);

        return path;
    }

    /****
     * 根据指定的路径上传
     * @param filePath
     * @param descriptions
     * @return
     * @throws Exception
     */
    public String upload(String filePath ,Map<String,String> descriptions) throws Exception{
        if (filePath==null||"".equals(filePath)){
            throw  new Exception("文件路径为空！") ;
        }
        File file = new File(filePath);
        String path = null ;
        //获取文件流
        InputStream is = new FileInputStream(file);
        // 获取文件名
        filePath = toLocal(filePath);
        String filename = filePath.substring(filePath.lastIndexOf("/") + 1);
        //上传
        path = upload(is, filename, descriptions);

        return path ;
    }

    /***
     * 使用 MultipartFile 上传
     * @param file
     * @param descriptions
     * @return 文件路径
     * @throws Exception
     */
    public String upload(MultipartFile file, Map<String, String> descriptions) throws Exception {
        if(file == null || file.isEmpty()){
            throw new Exception("文件为空！");
        }
        String path = null;
        try {
            path = upload(file.getInputStream(), file.getOriginalFilename(), descriptions);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    /***
     * 上传base64文件
     * @param base64
     * @param filename
     * @param descriptions
     * @return
     * @throws Exception
     */
    public String upload(String base64, String filename, Map<String, String> descriptions) throws Exception {
        if(base64==null||"".equals(base64)){
            throw new Exception("文件为空！");
        }
        return upload(new ByteArrayInputStream(Base64.decodeBase64(base64)), filename, descriptions);
    }

    /***
     * 获取访问服务器的token
     * @param filePath group1/M00/00/00/rBBNW1sJCvCAHgH7AAVGh5jPYok707.jpg
     * @param secret 密钥
     * @return 返回token
     */
    public static String getToken(String filePath,String secret) throws Exception{
        //获取当前时间毫秒数
        int ts = (int) Instant.now().getEpochSecond();
        String token ="" ;
        try {
            token = ProtoCommon.getToken(getFilename(filePath),ts,secret) ;
        }catch (Exception e){
            e.printStackTrace();
        }

        return token ;
    }

    /**
     * 以附件形式下载文件
     *
     * @param filepath
     * @param response
     */
    public void downloadFile(String filepath, HttpServletResponse response) throws Exception {
        download(filepath, null, null, response);
    }
    /**
     * 下载文件 输出文件
     *
     * @param filepath
     * @param os 输出流
     */
    public void downloadFile(String filepath, OutputStream os) throws Exception {
        download(filepath, null, os, null);
    }
    /**
     * 以附件形式下载文件 可以指定文件名称.
     *
     * @param filepath
     * @param filename
     * @param response
     */
    public void downloadFile(String filepath, String filename, HttpServletResponse response) throws Exception {
        download(filepath, filename, null, response);
    }
    /**
     * 下载文件
     *
     * @param filepath
     * @param fileName
     * @param os 输出流
     * @param response
     */
    public void download(String filepath, String fileName, OutputStream os, HttpServletResponse response) throws Exception {
        if(fileName!=null&&!"".equals(fileName)){
            throw new Exception("文件路径为空！");
        }
        filepath = toLocal(filepath);
        // 文件名
        if (fileName==null||"".equals(fileName)) {
            fileName = getOriginalFileName(filepath);
        }
        //获取文件类型
        String contentType = EXT_MAPS.get(getFIleNameSuffix(fileName));
        //获取trackerServer
        TrackerServer trackerServer = TrackerServerPool.borrowObject();
        StorageClient1 storageClient = new StorageClient1(trackerServer, null);
        InputStream is = null;
        try {
            // 下载
            byte[] fileByte = storageClient.download_file1(filepath);

            if(fileByte == null){
                throw new Exception("下载失败！");
            }
            if (response != null) {
                os = response.getOutputStream();

                // 设置响应头
                if (contentType!=null||!"".equals(contentType)) {
                    // 文件编码
                    String encoderName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20").replace("%2B", "+");
                    response.setHeader("Content-Disposition", "attachment;filename=\"" + encoderName + "\"");
                    response.setContentType(contentType + ";charset=UTF-8");
                    response.setHeader("Accept-Ranges", "bytes");
                }
            }

            is = new ByteArrayInputStream(fileByte);
            byte[] buffer = new byte[1024 * 5];
            int len = 0;
            while ((len = is.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("下载失败！");
        } finally {
            // 关闭流
            try {
                if(is != null){
                    is.close();
                }
                if(os != null){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 回收对象
        TrackerServerPool.recycleObject(trackerServer);
    }
    /**
     * 删除文件
     *
     * @param filepath
     * @return 删除成功返回 0, 失败返回其它
     */
    public int deleteFile(String filepath) throws Exception {
        if(filepath==null||"".equals(filepath)){
            throw new Exception("文件路径为空！");
        }

        TrackerServer trackerServer = TrackerServerPool.borrowObject();
        StorageClient1 storageClient = new StorageClient1(trackerServer, null);
        int success = 0;
        try {
            success = storageClient.delete_file1(filepath);
            if(success != 0){
                throw new Exception("删除失败！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 回收对象
        TrackerServerPool.recycleObject(trackerServer);
        return success;
    }

}
