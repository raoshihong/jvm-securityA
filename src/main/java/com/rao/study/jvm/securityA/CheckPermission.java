package com.rao.study.jvm.securityA;

public class CheckPermission {

    public static void main(String[] args){
        //打开系统安全权限检查开关(默认关闭的)
        System.setSecurityManager(new SecurityManager());
        SecurityManager securityManager = System.getSecurityManager();
        if(securityManager!=null){//表示开启了安全权限检测
            securityManager.checkPermission(new RuntimePermission("enableContextClassLoaderOverride"));//检测是否有某个权限,实际上也是通过AccessController来检测的,整个security就是通过AccessController操作
        }
    }

}
