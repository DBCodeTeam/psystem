package com.um.psystem.entity.sysEntity;

import com.baomidou.mybatisplus.annotations.TableField;

import java.util.Date;

public class BaseEntity extends PK {

    /**
     * 创建时间
     */
    @TableField("create_time")
    protected Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    protected Date updateTime;

    /**
     * 是否已删除 1=已删除 0=未删除
     */
    @TableField("is_deleted")
    private Integer isDeleted;

    public BaseEntity() {
        super();
    }

    public BaseEntity(Long id) {
        super(id);
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
