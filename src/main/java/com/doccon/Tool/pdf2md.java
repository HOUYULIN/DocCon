package com.doccon.Tool;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.util.Iterator;
import java.util.regex.Pattern;


/**
 * pdf文件转md
 */
public class pdf2md {



    public static void main(String[] args) throws IOException {
        //输入要转换的pdf文件
        String pdfFileName = "D:\\pdf\\day1.pdf";

        String filename = "";
        String[] files = pdfFileName.split("\\\\");
        for (int z=0;z<files.length-1;z++) {
            filename= filename+files[z]+"\\";
        }
        filename=filename+"img";

        File srcFolder = new File(filename);
        if(!srcFolder.exists()){//如果文件夹不存在
            srcFolder.mkdir();//创建文件夹
        }
        //转换后的md文件
        String docFileName = pdfFileName.substring(0, pdfFileName.lastIndexOf(".")) + ".md";

        //判断是否为空
        File file = new File(docFileName);
        if (!file.exists()) {
            file.createNewFile();
        }

        //创建输出流对象,true文档末尾写入，防止覆盖
        FileOutputStream fos = new FileOutputStream(docFileName, true);

        //通过PDDocument将pdf文件解析
        PDDocument pdf = PDDocument.load(new File(pdfFileName));

        //获取pdf文件的页数
        int pageNumber = pdf.getNumberOfPages();

        //提取每一页的图片和文字，添加到 word 中
        for (int i = 0; i < pageNumber; i++) {
            //图片处理
            PDPage page = pdf.getPage(i);
            PDResources resources = page.getResources();

            Iterable<COSName> names = resources.getXObjectNames();

            //迭代器遍历
            Iterator<COSName> iterator = names.iterator();
            while (iterator.hasNext()) {
                COSName cosName = iterator.next();




                if (resources.isImageXObject(cosName)) {
                    PDImageXObject imageXObject = (PDImageXObject) resources.getXObject(cosName);

                    //图片路径
                    String timeimg = "\\"+System.currentTimeMillis() + "";
                    String img = filename + timeimg + ".jpg";
                    //创建img文件
                    File outImgFile = new File(img);
                    //图片写入img文件
                    Thumbnails.of(imageXObject.getImage()).scale(0.9).rotate(0).toFile(outImgFile);

                    //缩放图片
                    /*BufferedImage bufferedImage = ImageIO.read(outImgFile);
                    int width = bufferedImage.getWidth();
                    int height = bufferedImage.getHeight();
                    if (width > 600) {
                        double ratio = Math.round((double) width / 550.0);
                        System.out.println("缩放比ratio：" + ratio);
                        width = (int) (width / ratio);
                        height = (int) (height / ratio);

                    }else{

                    }
                     */


                    //图片写入输入流
                    FileInputStream in = new FileInputStream(outImgFile);
                    byte[] ba = new byte[in.available()];
                    in.read(ba);
                    ByteArrayInputStream byteInputStream = new ByteArrayInputStream(ba);

                    //图片路径
                    String url = "![img](img/" + timeimg + ".jpg)";
                    byte[] urlby = url.getBytes();
                    fos.write("\r\n".getBytes());
                    fos.write(urlby);
                }
            }


            //pdf文本处理

            //创建pdf文档处理对象
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            stripper.setStartPage(i);
            stripper.setEndPage(i);
            //当前页中的文字
            String text = stripper.getText(pdf);
            System.out.println(text);

            //将每一行分成string字段
            String[] split = text.split("\\r\\n");
            String dl = "";
            int status = 1;
            //正则判断每一行是否首页字段
            for (int x = 0; x < split.length; x++) {
                if (Pattern.matches("^[0-9].[0-9].[0-9].[0-9][\\s\\S]*", split[x])) {
                    String[] spl = split[x].split("\\.");
                    dl = "#### " + spl[spl.length-2]+"\\."+ spl[spl.length-1];
                } else if (Pattern.matches("^[0-9].[0-9].[0-9][\\s\\S]*", split[x])) {
                    String[] spl = split[x].split("\\.");
                    dl = "### " + spl[spl.length-1];
                } else if (Pattern.matches("^[0-9].[0-9].[\\s\\S]*", split[x])) {
                    dl = "## " + split[x];
                } else if (Pattern.matches("^[0-9] [\\s\\S]*", split[x])) {
                    dl = "# " + split[x];
                } else {

                    //以：（开头，或者以）结尾，且汉字长度不超过10的行：
                    if(Pattern.matches("[\\s\\S]*[：]$|[\\s\\S]*[：] $|^（[\\s\\S]*|[\\s\\S]*[）]$|[\\s\\S]*[）] $",split[x])&&split[x].length()<=30){
                        split[x]= "\n"+"**"+split[x]+"**"+"\n";
                    }else if(Pattern.matches("[\\s\\S]*[：；]$|[\\s\\S]*[：；] $",split[x])){
                        split[x]= split[x]+"\n\n";
                    }
                    dl = split[x];
                    status = 2;
                }

                //写入输出流
                byte[] bytes = dl.getBytes();


                //判断是否换行
                //status:2非标题， 正则表达式最后非以：。结尾
                if (status == 2 && !Pattern.matches("[\\s\\S]*[。：]$", dl)) {
                    fos.write(bytes);
                } else {
                    //以[#]开头[:,: ]结尾必须换行^（[\s\S]*|[\s\S]*[）]$
                    if (Pattern.matches("^#[\\s\\S]*", dl) ) {
                        fos.write("\r\n".getBytes());
                        fos.write(bytes);
                        fos.write("\r\n".getBytes());
                    } else {
                        fos.write(bytes);
                        //换行
                        fos.write("\r\n".getBytes());
                        fos.write("\n".getBytes());
                    }


                }
                //重置参数
                status = 1;

            }
        }
        fos.close();
        pdf.close();
        System.out.println("pdf转换解析结束！！----");
    }


}
