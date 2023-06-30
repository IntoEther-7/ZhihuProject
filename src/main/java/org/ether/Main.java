package org.ether;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String s = "D:\\webmagic\\www.zhihu.com\\Q476472545A2987077254.md";
        File file = new File(s);
        System.out.println(file.getParent());
    }
}