# DocumentConversion

# 1.介绍

java文档转换工具。 

目前已实现： pdf转md(pdf转markdown：能够将pdf格式的文件转换成markdown格式的文件，支持同步同时转换图片和文本。)；

## 1.1实现功能：pdf转换md

## 1.2存在问题：
    1.图片和文本错位

    2.无法处理代码块和表格等。

## 1.3转换D:\pdf\day1.pdf文件的效果：

### 1.3.1转换后的目录结构：

![输入图片说明](https://images.gitee.com/uploads/images/2021/0115/093958_d361d6d2_1012259.png "屏幕截图.png")

### 1.3.2转换后的md文档效果：

![输入图片说明](https://images.gitee.com/uploads/images/2021/0115/094142_46370529_1012259.png "屏幕截图.png")


# 2.软件架构

pom.xml文件

引入pdfbox,thumbnailator文档处理jar包


# 3.使用说明

## 3.1引入项目
![输入图片说明](https://images.gitee.com/uploads/images/2021/0115/093309_d80acfc5_1012259.png "屏幕截图.png")
## 3.2刷新maven
![输入图片说明](https://images.gitee.com/uploads/images/2021/0115/093239_62a5ddb9_1012259.png "屏幕截图.png")
## 3.3pdf2md.java文件输入要转换pdf文件路径
![输入图片说明](https://images.gitee.com/uploads/images/2021/0115/093438_ef46b033_1012259.png "屏幕截图.png")
## 3.4右键运行





#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
