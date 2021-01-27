package com.doccon.Tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SQLTool {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = "";
        List<String> result = new ArrayList<>();
        System.out.println();
        result.add("CREATE TABLE `author` (");
        while (!"end".equals(line = sc.nextLine())) {
            String s = "";
            String[] split = line.split("\t");
            for (int i =0;i<split.length;i++){
                if(i==0){
                    s+="`"+split[i]+"` ";
                }else if(i==1){
                    if(split[0].equals("create_by_id")){
                        s+="bigint(20) unsigned"+split[i];
                    }else {
                        s+=split[i];
                    }

                }else if(i==2){
                    s+=" COMMENT '"+split[i]+"',";
                }

            }
            result.add(s);
        }
        result.add(") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='作者表';");
        for(String str:result){
            System.out.println(str);
        }
    }
}
