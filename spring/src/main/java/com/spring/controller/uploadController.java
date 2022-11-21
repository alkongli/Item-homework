package com.spring.controller;

import com.spring.common.R;
import com.spring.entity.SWork;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@RestController
public class uploadController {

//    在yml配置的路径
    @Value("${file.path}")
    private String filePath;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file,String workId, String userName){
//        log.info("提交信息:file1={}",file.getSize());
//        log.info("workId={},  userName={}",workId,userName);

        if(!file.isEmpty()){
            String name= workId+"_"+userName;
//        截取原始文件名的后缀
            String filename=file.getOriginalFilename();
            String substring = filename.substring(filename.lastIndexOf("."));

            try {
                file.transferTo(new File(filePath+name+substring));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return R.success("上传成功");
    }

    /**
     *     * 文件下载
     * @param fileName
     * @param pathName
     * @throws IOException
     */
    @GetMapping("/download")
    public void download(String fileName,String pathName,HttpServletResponse response) throws IOException {


//        读取文件
        File file=new File(filePath,pathName);

//        获取文件输入流
        FileInputStream is=new FileInputStream(file);
//        attachment 附件下载   inline 在线打开
        response.setHeader("Content-Disposition","attachment;fileName="+ java.net.URLEncoder.encode(fileName, "UTF-8"));
//        获取响应输出流
        ServletOutputStream os=response.getOutputStream();
//        文件拷贝
        int len=0;
        byte[] b=new byte[1024];
        while (true){
            len=is.read(b);
            if(len==-1) break;
            os.write(b,0,len);
        }

        is.close();
        os.close();

    }

}
