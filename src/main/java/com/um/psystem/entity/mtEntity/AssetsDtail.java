package com.um.psystem.entity.mtEntity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zhenjin.zheng
 * @Description: 物资明细
 * @Date: 2020/5/28
 */
@TableName(value="ws_eng_assets_type_dtl")
@Data
@NoArgsConstructor
public class AssetsDtail {
     @TableId(value="type_dtl_id", type= IdType.AUTO)
     private Integer typeDtlId;

     /**类别明细 */
     @TableField(value = "type_dtl_name")
     private String typeDtlName;

     /**物资编号*/
     @TableField(value="type_dtl_no")
     private String typeDtlNo;

     /**型号*/
     private String model;

     /**规格*/
     private String sizes;

     /**类别ID*/
     @TableField(value = "type_main_id")
     private Integer typeMainId;

     /**类别名称*/
     @TableField(value="type_main_name")
     private String typeMainName;

     private String remark;


}
