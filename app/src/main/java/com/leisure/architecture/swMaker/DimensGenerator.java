package com.leisure.architecture.swMaker;

import java.io.File;


public class DimensGenerator {

    /**
     * 设计稿宽度
     */
    private static final int DESIGN_WIDTH = 1080;

    /**
     * 设计稿的高度
     */
    private static final int DESIGN_HEIGHT = 1920;


    /**
     * 生成适配dimens
     */
    public static void main(String[] args) {
        int smallest = DESIGN_WIDTH > DESIGN_HEIGHT ? DESIGN_HEIGHT : DESIGN_WIDTH;  //     求得最小宽度
        DimensTypes[] values = DimensTypes.values();
        for (DimensTypes value : values) {
            File file = new File("");
            MakeUtils.makeAll(smallest, value, file.getAbsolutePath());
        }

    }

}
