package com.zhpt;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

@Mojo(name="replaceStrPlugin")
public class ReplaceStrPlugin extends AbstractMojo {
    @Parameter
    private String changeFiles;
    @Parameter
    private String sourceStrings;
    @Parameter
    private String targetStrings;
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        this.getLog().info("ReplaceStrPlugin plugin starting...");
        this.getLog().info("changeFiles:"+changeFiles);
        this.getLog().info("sourceStrings:"+sourceStrings);
        this.getLog().info("targetStrings:"+targetStrings);
        //check parameter
        if(StringUtils.isEmpty(changeFiles)){
            this.getLog().error("changeFiles is empty");
        }
        if(StringUtils.isEmpty(sourceStrings)){
            this.getLog().error("sourceStrings is empty");
        }
        if(StringUtils.isEmpty(sourceStrings)){
            this.getLog().error("targetStrings is empty");
        }
        if(StringUtils.isEmpty(changeFiles)||StringUtils.isEmpty(sourceStrings)||StringUtils.isEmpty(targetStrings)){
            return;
        }
        String[] fileArray=changeFiles.split(",");
        String[] sourceStrArray=sourceStrings.split(",");
        String[] targetStrArray=targetStrings.split(",");
        if(sourceStrArray.length!=targetStrArray.length){
            this.getLog().error("替换源字符串与目标字符串的个数不一致！");
            return;
        }
        for(String file:fileArray){
            autoReplace(file,sourceStrArray,targetStrArray);
        }

    }

    /**
     * 替换文件中的字符，并覆盖源文件
     * @param filePath 文件全路径
     * @param sourceStrArray 替换源字符数组
     * @param targetStrArray 替换目标字符数组
     */
    public void autoReplace(String filePath,String[] sourceStrArray,String[] targetStrArray) {
        this.getLog().info("开始执行替换操作文件："+filePath);
        FileInputStream in =null;
        PrintWriter out =null;
        try{
            in = new FileInputStream(filePath);
            File file = new File(filePath);
            if(!file.exists()){
                this.getLog().error("文件不存在："+filePath);
                return ;
            }
            Long fileLength = file.length();
            byte[] fileContext = new byte[fileLength.intValue()];
            in.read(fileContext);
            String str = new String(fileContext);
            for(int i=0;i<sourceStrArray.length;i++){
                this.getLog().info("开始替换 "+sourceStrArray[i]+" 为 "+targetStrArray[i]);
                str = str.replaceAll(sourceStrArray[i], targetStrArray[i]);
            }
            out = new PrintWriter(filePath);
            out.write(str.toCharArray());
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out!=null){
                out.close();
            }
        }
    }
}
