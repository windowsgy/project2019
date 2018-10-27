package inOut;

import param.Param;
import utils.FileUtils;
import utils.LogInfo;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class Input {


     public static void inputParam(String paramName) {
        Param.inputStatus = false;//执行状态重置
        LogInfo.linel4();
        System.out.println("Input " + paramName + " : (0,yes ; 1,no ;2, return ; 3,exit)");
        System.out.println();
        System.out.print(":");
        Scanner input = new Scanner(System.in);
        int inputInt = input.nextInt();
        if (inputInt == 0) {
            Param.inputStatus = true;
        } else if (1== inputInt) {
            Param.inputStatus = false;
        } else if (2 == inputInt) {
            Param.inputStatus = false;
        } else if (3== inputInt) {
            System.out.println("Exit");
            Param.inputStatus = false;
            Param.exitOnOff = true;
        } else {
            System.out.println("input error");
            inputParam(paramName);
        }

    }
    public static void inputParam(String paramName, Set<String> set) {
        Param.inputStatus = false;//执行状态重置
        LogInfo.linel4();
        System.out.println("Input " + paramName + "");
        Field field;
        List<String> list = new ArrayList<>(set);
        list.add("Return");//
        list.add("Exit");//添加退出选项
        for (int i = 0; i < list.size(); i++) {
            System.out.print(i + ":" + list.get(i) + " ;  ");
        }
        System.out.println();
        System.out.print(":");
        Scanner input = new Scanner(System.in);
        try {
            int inputInt = input.nextInt();
            if (inputInt == list.size() - 1) {
                System.out.println("Exit");
                Param.inputStatus = false;
                Param.exitOnOff = true;
            } else if (inputInt == (list.size()-2)) {
                Param.inputStatus = false;
            } else if (inputInt >=0 && inputInt <= list.size() ) {
                //get the input index
                String value = list.get(inputInt);
                field = param.Param.class.getField(paramName);
                field.set(param.Param.class, value);
                //打印变量 和变量值
                System.out.println(paramName + " :" + field.get(Param.class));
                Param.inputStatus = true;
            } else {
                System.out.println("input error");
                Input.inputParam(paramName, set);
            }
        } catch (NoSuchFieldException e) {
            LogInfo.error("Input : NoSuchFieldException : " + e.getMessage());
            Param.inputStatus = false;
        } catch (Exception e){
            LogInfo.error("Input  : " + e.getMessage());
            Param.inputStatus = false;
        }
    }


    private static void inputPath(String paramName, boolean isDir) {
        Param.inputStatus = false;//执行状态重置
        LogInfo.linel4();
        System.out.println("Input " + paramName + ":  Path ;0,Return ; 1,Exit");
        System.out.print(":");
        Field field;
        Scanner input = new Scanner(System.in);
        String str = input.next().trim();
        try {
            if ("1".equals(str)) {
                System.out.println("Exit");
                Param.inputStatus = false;
                Param.exitOnOff = true;
            } else if ("0".equals(str)) {
                Param.inputStatus = false;
            } else if (isPath(str,isDir)) {
                field = param.Param.class.getField(paramName);
                field.set(param.Param.class, str);
                //打印变量 和变量值
                System.out.println(paramName + " :" + field.get(Param.class));
                Param.inputStatus = true;
            } else {
                System.out.println("input path not exist");
                inputPath(paramName,isDir);
            }
        } catch (NoSuchFieldException e) {
            LogInfo.error("Input : NoSuchFieldException : " + e.getMessage());
            Param.inputStatus = false;
        } catch (IllegalAccessException e) {
            LogInfo.error("Input : IllegalAccessException : " + e.getMessage());
            Param.inputStatus = false;
        }
    }

    /**
     * 添加基本路径
     * @param paramName 输入路径
     * @param basePath 基本路径
     * @param isDir 是否是目录
     */

    public static void inputPath(String paramName,String basePath,boolean isDir) {
        Param.inputStatus = false;//执行状态重置
        LogInfo.linel4();
        System.out.println("Input " + paramName + ":  Path ;0,Return ; 1,Exit");
        System.out.print(":");
        Field field;
        Scanner input = new Scanner(System.in);
        try {
            String str = input.next().trim();
            if ("1".equals(str)) {
                System.out.println("Exit");
                Param.inputStatus = false;
                Param.exitOnOff = true;
            } else if ("0".equals(str)) {
                Param.inputStatus = false;
            } else if (isPath(basePath+str,isDir)) {
                field = param.Param.class.getField(paramName);
                field.set(param.Param.class, str);
                //打印变量 和变量值
                System.out.println(paramName + " :" + field.get(Param.class));
                Param.inputStatus = true;
            } else {
                System.out.println("input path not exist");
                inputPath(paramName,isDir);
            }
        } catch (NoSuchFieldException e) {
            LogInfo.error("Input : NoSuchFieldException : " + e.getMessage());
            Param.inputStatus = false;
        } catch (IllegalAccessException e) {
            LogInfo.error("Input : IllegalAccessException : " + e.getMessage());
            Param.inputStatus = false;
        }
    }


    private static boolean isPath(String path, boolean isDir){
        FileUtils fileUtils =  new FileUtils();
         if(isDir){
             return fileUtils.isDir(path);
         }else {
             return fileUtils.isFile(path);
         }
    }






}
