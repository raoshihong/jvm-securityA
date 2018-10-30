package com.rao.study.jvm.securityA;

import com.rao.study.jvm.security.FileUtils;

import java.io.File;
import java.io.IOException;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * 以下实现了项目内和项目外的文件安全检测
 */

public class DemoDoPrivilege {
    public static void main(String[] args){
        System.out.println("***************************************");
        System.out.println("I will show AccessControl functionality...");

        System.out.println("Preparation step : turn on system permission check...");
        // 打开系统安全权限检查开关,开启了的话,如果没有指定授权,则无法使用特权进行访问,所以需要进行授权
        System.setSecurityManager(new SecurityManager());
        System.out.println();

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Create a new file named temp1.txt via privileged action ...");
        // 用特权访问方式在工程 A 执行文件路径中创建 temp1.txt 文件
        doPrivilegedAction("temp1.txt");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();

        System.out.println("/////////////////////////////////////////");
        System.out.println("Create a new file named temp2.txt via File ...");
        try {
            // 用普通文件操作方式在工程 A 执行文件路径中创建 temp2.txt 文件
            File fs = new File(
                    "D:\\aaa\\temp2.txt");
            fs.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AccessControlException e1) {
            e1.printStackTrace();
        }
        System.out.println("/////////////////////////////////////////");
        System.out.println();

        System.out.println("-----------------------------------------");
        System.out.println("create a new file named temp3.txt via FileUtil ...");
        // 直接调用普通接口方式在工程 A 执行文件路径中创建 temp3.txt 文件
        makeFile("temp3.txt");
        System.out.println("-----------------------------------------");
        System.out.println();

        System.out.println("***************************************");

        System.out.println("-----------------------------------------");
        System.out.println("create a new file named temp4.txt via FileUtil ...");
        // 直接调用普通接口方式在工程 A 执行文件路径中创建 temp3.txt 文件
        FileUtils.makeFile("temp4.txt");
        System.out.println("-----------------------------------------");
        System.out.println();

        System.out.println("***************************************");

        System.out.println("-----------------------------------------");
        System.out.println("create a new file named temp5.txt via FileUtil ...");
        // 直接调用普通接口方式在工程 A 执行文件路径中创建 temp3.txt 文件
        FileUtils.doPrivilegedAction("temp5.txt");
        System.out.println("-----------------------------------------");
        System.out.println();

        System.out.println("***************************************");
    }

    public static void doPrivilegedAction(final String fileName) {
        // 用特权访问方式创建文件
        AccessController.doPrivileged(new PrivilegedAction<String>() {
            public String run() {
                makeFile(fileName);
                return null;
            }
        });
    }

    private final static String FOLDER_PATH = "D:\\aaa";

    public static void makeFile(String fileName) {
        try {
            // 尝试在工程 A 执行文件的路径中创建一个新文件
            File fs = new File(FOLDER_PATH + "\\" + fileName);
            fs.createNewFile();
        } catch (AccessControlException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
