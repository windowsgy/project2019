package inOut;

import param.Param;
import base.FileUtils;
import base.Log;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class Input {

    public static boolean inputStatus = false;//输入状态 判断输入是否成功

    /**
     * 输入 是否参数
     *
     * @param prompt 提示信息
     */
    public static void inputParam(String prompt) {
        inputStatus = false;//执行状态重置
        Log.linel4();
        System.out.println("Input " + prompt + " : (0,yes ; 1,no ;2, return ; 3,exit)");
        System.out.println();
        System.out.print(":");
        Scanner input = new Scanner(System.in);
        int inputInt = input.nextInt();
        if (inputInt == 0) {
            inputStatus = true;
        } else if (1 == inputInt) {
            inputStatus = false;
        } else if (2 == inputInt) {
            inputStatus = false;
        } else if (3 == inputInt) {
            System.out.println("Exit");
            inputStatus = false;
            Param.exitOnOff = true;
        } else {
            System.out.println("input error");
            inputParam(prompt);
        }

    }

    /**
     * 输入参数
     *
     * @param param 全局静态变量，根据选择内容设置这个全局静态变量
     * @param set   选择的集合，根据集合中的内容进行选择
     */
    public static void inputParam(String param, Set<String> set) {
        inputStatus = false;//执行状态重置
        Log.linel4();
        System.out.println("Input " + param + "");
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
                inputStatus = false;
                Param.exitOnOff = true;
            } else if (inputInt == (list.size() - 2)) {
                inputStatus = false;
            } else if (inputInt >= 0 && inputInt <= list.size()) {
                //get the input index
                String value = list.get(inputInt);
                field = param.Param.class.getField(param);
                field.set(param.Param.class, value);
                //打印变量 和变量值
                System.out.println(param + " :" + field.get(Param.class));
                inputStatus = true;
            } else {
                System.out.println("input error");
                Input.inputParam(param, set);
            }
        } catch (NoSuchFieldException e) {
            Log.error("Input : NoSuchFieldException : " + e.getMessage());
            inputStatus = false;
        } catch (Exception e) {
            Log.error("Input  : " + e.getMessage());
            inputStatus = false;
        }
    }

    /**
     * 添加基本路径
     *
     * @param paramName 输入路径
     * @param basePath  基本路径 ，输入路径的前缀
     */

    public static void inputPath(String paramName, String basePath) {
        inputStatus = false;//执行状态重置
        FileUtils fileUtils = new FileUtils();
        Log.linel4();
        System.out.println("Input " + paramName + ":  Path ;0,Return ; 1,Exit");
        System.out.print(":");
        Field field;
        Scanner input = new Scanner(System.in);
        try {
            String str = input.next().trim();
            boolean boolDir = fileUtils.isDir(basePath + str);
            if ("1".equals(str)) {
                System.out.println("Exit");
                inputStatus = false;
                Param.exitOnOff = true;
            } else if ("0".equals(str)) {
                inputStatus = false;
            } else if (boolDir) {
                field = param.Param.class.getField(paramName);
                field.set(param.Param.class, str);
                //打印变量 和变量值
                System.out.println(paramName + " :" + field.get(Param.class));
                inputStatus = true;
            } else {
                System.out.println("input path not exist");
                inputPath(paramName, basePath);
            }
        } catch (NoSuchFieldException e) {
            Log.error("Input : NoSuchFieldException : " + e.getMessage());
            inputStatus = false;
        } catch (IllegalAccessException e) {
            Log.error("Input : IllegalAccessException : " + e.getMessage());
            inputStatus = false;
        }
    }

}
