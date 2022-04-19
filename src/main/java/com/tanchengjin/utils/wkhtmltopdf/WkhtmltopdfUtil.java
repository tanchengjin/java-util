package com.tanchengjin.utils.wkhtmltopdf;

import java.io.*;
import java.util.UUID;

/**
 * @author TanChengjin
 * @version v1.0.0
 * @email 18865477815@163.com
 */
public class WkhtmltopdfUtil {
    //可执行文件路径
    private String exec;
    /**
     * 字符串格式的html
     */
    private String htmlString;
    /**
     * 单例模式
     */
    private static WkhtmltopdfUtil wkhtmltopdfContainer;
    /**
     * 存储文件夹
     */
    private String storageDir;

    private String headerHtmlFile;

    private String footerHtmlFile;

    /**
     * 页脚和内容的距离
     */
    private int footerSpacing = 5;
    /**
     * 是否显示页眉的线
     */
    private boolean headerLine = false;
    /**
     * 是否显示页尾的线
     */
    private boolean footerLine = false;
    /**
     * 是否显示页码
     */
    private boolean footerPage = true;

    public static final String fileStoragePath = "/wk/tmp";

    private WkhtmltopdfUtil() {
    }

    /**
     * 配置相关参数
     *
     * @param exec       可执行文件
     * @param storageDir 存储文件夹
     */
    public WkhtmltopdfUtil config(String exec, String storageDir) {
        this.exec = exec;
        this.storageDir = storageDir;
        return wkhtmltopdfContainer;
    }

    public static WkhtmltopdfUtil getInstance() {
        if (wkhtmltopdfContainer == null) {
            wkhtmltopdfContainer = new WkhtmltopdfUtil();
        }
        return wkhtmltopdfContainer;
    }

    /**
     * 设置页眉
     */
    public WkhtmltopdfUtil setHeader(String html) {
        this.headerHtmlFile = html;
        return wkhtmltopdfContainer;
    }

    /**
     * 设置页尾
     */
    public WkhtmltopdfUtil setFooter(String html) {
        this.footerHtmlFile = html;
        return wkhtmltopdfContainer;
    }

    public WkhtmltopdfUtil html(String htmlStr) {
        htmlString = htmlStr;
        return wkhtmltopdfContainer;
    }

    /**
     * 将HTML转换为PDF，并返回存储后的文件名
     */
    public String toPDF() {

        String uuid = UUID.randomUUID().toString();
        String fileName = storageDir + File.separator + fileStoragePath + File.separator + uuid;
        String htmlFile = fileName + ".html";
        //获取html文件路径
        String htmlStr = null;
        try {
            htmlStr = strToHtmlFile(htmlString, htmlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String pdfFile = fileName + ".pdf";
        //将html文件转换成pdf文件
        boolean result = convert(htmlStr, pdfFile, exec);
        if (result) {
            return uuid + ".pdf";
        } else {
            return null;
        }
    }


    /**
     * html转pdf
     * 核心方法
     *
     * @param srcPath  html路径，可以是硬盘上的路径，也可以是网络路径
     * @param destPath pdf保存路径
     * @param execFile 执行文件
     * @return 转换成功返回true
     */
    public boolean convert(String srcPath, String destPath, String execFile) {
        File file = new File(destPath);
        File parent = file.getParentFile();
        // 如果pdf保存路径不存在，则创建路径
        if (!parent.exists()) {
            parent.mkdirs();
        }
        StringBuilder cmd = new StringBuilder();
//        if (System.getProperty("os.name").indexOf("Windows") == -1) {
//            // 非windows 系统
//            //toPdfTool = FileUtil.convertSystemFilePath("/home/ubuntu/wkhtmltox/bin/wkhtmltopdf");
//        }
        cmd.append(execFile);
        cmd.append(" ");
//        cmd.append(" --header-center");//页眉中间内容
        if (headerLine) {
            cmd.append("  --header-line");// 页眉下面的线
        }
//        cmd.append("  --margin-top 3cm ");// 设置页面上边距 (default 10mm)
        if (!isEmpty(this.headerHtmlFile)) {
            cmd.append(" --header-html ").append(this.headerHtmlFile);
        }
//        cmd.append(" --header-spacing 0 ");// (设置页眉和内容的距离,默认0)
        if (footerPage) {
            //设置在中心位置的页脚内容
            cmd.append(" --footer-center 第[page]页／共[topage]页");
        }

        if (!isEmpty(this.footerHtmlFile)) {
            cmd.append(" --footer-html ").append(this.footerHtmlFile);
        }
//        cmd.append(" --footer-center 眼健康在线服务平台提供支持");
        if (footerLine) {
            cmd.append(" --footer-line");// * 显示一条线在页脚内容上)
        }

        cmd.append("--page-size A4");

        cmd.append(" --footer-spacing ").append(footerSpacing).append(" ");// (设置页脚和内容的距离)
        cmd.append(srcPath);
        cmd.append(" ");
        cmd.append(destPath);

        boolean result = true;
        try {
            Process proc = Runtime.getRuntime().exec(cmd.toString());
            HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());
            HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());
            error.start();
            output.start();
            proc.waitFor();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 将html字符串转换为HTML文件
     *
     * @param htmlStr html字符串
     * @param path    html文件保存地址
     * @return
     */
    public static String strToHtmlFile(String htmlStr, String path) throws Exception {
        if (htmlStr == null) {
            throw new Exception("html字符串不允许为空");
        }
        File file = new File(path);
        try {
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(htmlStr.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return path;
    }

    private boolean isEmpty(CharSequence character) {
        return character == null || character.length() == 0;
    }


    private class HtmlToPdfInterceptor extends Thread {
        private InputStream is;

        public HtmlToPdfInterceptor(InputStream is) {
            this.is = is;
        }

        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is, "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.out.println(line.toString()); //输出内容
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public WkhtmltopdfUtil setFooterPage(boolean footerPage) {
        this.footerPage = footerPage;
        return wkhtmltopdfContainer;
    }
}
