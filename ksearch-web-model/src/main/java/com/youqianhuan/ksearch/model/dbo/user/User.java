package com.youqianhuan.ksearch.model.dbo.user;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author chenghuanhuan
 * @since 2017-06-20
 */
@TableName("user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="user_id",type = IdType.INPUT)
	private String userId;

	/**
	 * 角色
	 */
	@TableField(exist = false)
	private Role role;
    /**
     * 密码
     */
	@TableField("password")
	private String password;

    /**
     * 名字
     */
	@TableField("username")
	private String username;

    /**
     * 角色id
     */
	@TableField(value = "role_id")
	private Integer roleId;

    /**
     * 状态(1：启用  2：冻结  3：删除）
     */
    @TableField(value = "status")
	private Integer status;
    /**
     * 创建时间
     */
	@TableField(value = "create_time")
	private Date createTime;

	@TableField(value = "update_time")
	private Date updateTime;

	@TableField(value = "create_by")
	private String createBy;

	@TableField(value = "update_by")
	private String updateBy;


	public User() {
	}




	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	protected Serializable pkVal() {
		return this.userId;
	}

}
