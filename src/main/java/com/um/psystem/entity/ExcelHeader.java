package com.um.psystem.entity;

/**
 * @Author: zhenjin.zheng
 * @Description:
 * @Date: 2020/6/3
 */
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 表格头类.
 */
@Data
@NoArgsConstructor
public class ExcelHeader implements Serializable {
    //数字
    public final static int NUMBER = 1;
    //日期
    public final static int DATE = 2;
    //字符串
    public final static int STRING = 3;
    //布尔型
    public final static int BOOLEN = 4;


    /**
     * general (normal) horizontal alignment
     */

    public final static short ALIGN_GENERAL = 0x0;

    /**
     * left-justified horizontal alignment
     */

    public final static short ALIGN_LEFT = 0x1;

    /**
     * center horizontal alignment
     */

    public final static short ALIGN_CENTER = 0x2;

    /**
     * right-justified horizontal alignment
     */

    public final static short ALIGN_RIGHT = 0x3;

    /**
     * fill? horizontal alignment
     */

    public final static short ALIGN_FILL = 0x4;

    /**
     * justified horizontal alignment
     */

    public final static short ALIGN_JUSTIFY = 0x5;

    /**
     * center-selection? horizontal alignment
     */

    public final static short ALIGN_CENTER_SELECTION = 0x6;

    /**
     * top-aligned vertical alignment
     */

    public final static short VERTICAL_TOP = 0x0;

    /**
     * center-aligned vertical alignment
     */

    public final static short VERTICAL_CENTER = 0x1;

    /**
     * bottom-aligned vertical alignment
     */

    public final static short VERTICAL_BOTTOM = 0x2;

    /**
     * vertically justified vertical alignment
     */
    //表格头对应字段名
    private String  headerField;
    //表格头名
    private String headerName;
    //表格头占的宽度(单位:单元格)
    private  int headerWidth;
    //此列数据类型支持的有布尔值、数字、字符串、日期
    private int dataType;
    //在单元格位置
    private  short headerAlign;
    //显示格式
    private String format;
    //是否计算总和
    private  boolean isCountTotal;
    //是否分组组织数据
    private boolean isGroupData;


    public ExcelHeader(String headerField, String headerName, int headerWidth, short headerAlign) {
        this.headerField = headerField;
        this.headerName = headerName;
        this.headerWidth = headerWidth;
        this.headerAlign = headerAlign;
        this.isCountTotal = false;
        this.isGroupData = false;
    }

    public ExcelHeader(String headerField, String headerName, int dataType, short headerAlign, boolean isCountTotal) {
        this.headerField = headerField;
        this.headerName = headerName;
        this.dataType = dataType;
        this.headerAlign = headerAlign;
        this.isCountTotal = isCountTotal;
    }

    public ExcelHeader(String headerField, String headerName, int headerWidth, int dataType, short headerAlign, String format, boolean isCountTotal, boolean isGroupData) {
        this.headerField = headerField;
        this.headerName = headerName;
        this.headerWidth = headerWidth;
        this.dataType = dataType;
        this.headerAlign = headerAlign;
        this.format = format;
        this.isCountTotal = isCountTotal;
        this.isGroupData = isGroupData;
    }

    public ExcelHeader(String headerField, String headerName, int headerWidth) {
        this(headerField,headerName,headerWidth,ALIGN_CENTER);
    }

    public ExcelHeader(String headerField, String headerName) {
        this(headerField,headerName,1);
    }




}

