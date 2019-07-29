/*
 * GetChannelAuthenticationCapabilitiesResponseData.java 
 * Created on 2011-07-21
 *
 * Copyright (c) Verax Systems 2011.
 * All rights reserved.
 *
 * This software is furnished under a license. Use, duplication,
 * disclosure and all other uses are restricted to the rights
 * specified in the written license agreement.
 */
package com.veraxsystems.vxipmi.coding.commands.session;

import java.util.Collection;

import com.veraxsystems.vxipmi.coding.commands.ResponseData;
import com.veraxsystems.vxipmi.coding.protocol.AuthenticationType;

/**
 * A wrapper for Get Channel Authentication Capabilities command response
 * 用于获取通道身份验证功能的包装器命令响应
 * @see GetChannelAuthenticationCapabilities
 */
public class GetChannelAuthenticationCapabilitiesResponseData implements ResponseData {
	
    @Override
	public String toString() {
		return "GetChannelAuthenticationCapabilitiesResponseData [channelNumber=" + channelNumber + ", ipmiv20Support="
				+ ipmiv20Support + ", authenticationTypes=" + authenticationTypes + ", kgEnabled=" + kgEnabled
				+ ", perMessageAuthenticationEnabled=" + perMessageAuthenticationEnabled
				+ ", userLevelAuthenticationEnabled=" + userLevelAuthenticationEnabled + ", nonNullUsernamesEnabled="
				+ nonNullUsernamesEnabled + ", nullUsernamesEnabled=" + nullUsernamesEnabled + ", anonymusLoginEnabled="
				+ anonymusLoginEnabled + ", oemId=" + oemId + ", oemData=" + oemData + "]";
	}

	/**
     * Channel number that the Authentication Capabilities is being returned for.
     * 返回身份验证功能的通道号。
     */
    private byte channelNumber;

    /**
     * IPMI v2.0 support.
     */
    private boolean ipmiv20Support;

    /**
     * Authentication Types supported for requested privilege level.
     * 所请求权限级别支持的身份验证类型。
     */
    private Collection<AuthenticationType> authenticationTypes;

    /**
     * BMC key used for authentication. If false, then BMC uses user key. 
     * 用于身份验证的BMC密钥。如果为false，则BMC使用用户密钥。
     */
    private boolean kgEnabled;

    /**
     * If Mer-message Authentication is enabled, packets to the BMC must be authenticated per Authentication Type used
     * to activate the session, and User Level Authentication setting, following. Otherwise, Authentication Type 'None'
     * accepted for packets to the BMC after the session has been activated.
     * 如果启用了Mer-message身份验证，则必须根据用于激活会话的身份验证类型和下面
     * 的用户级身份验证设置对BMC的数据包进行身份验证。否则，在会话激活后
     * ，身份验证类型“None”接受发送到BMC的数据包。
     */
    private boolean perMessageAuthenticationEnabled;

    /**
     * If User Level Authentication is enabled, User Level commands must be authenticated per Authentication Type used
     * to activate the session. Otherwise, Authentication Type 'none' accepted for User Level commands to the BMC.
     * 如果启用了用户级身份验证，则必须根据用于激活会话的身
     * 份验证类型对用户级命令进行身份验证。否则，身份验证类型“none”接受BMC的用户级命令。
     */
    private boolean userLevelAuthenticationEnabled;

    /**
     * One or more users are enabled that have non-null usernames
     * 启用一个或多个具有非空用户名的用户
     */
    private boolean nonNullUsernamesEnabled;

    /**
     * One or more users that have a null username, but non-null password, are presently enabled
     * 当前启用一个或多个用户，这些用户的用户名为空，但密码为非空
     */
    private boolean nullUsernamesEnabled;

    /**
     * A user that has a null username and null password is presently enabled
     * 当前启用用户名和密码为空的用户
     */
    private boolean anonymusLoginEnabled;

    /**
     * IANA Enterprise Number for OEM/Organization that specified the particular OEM Authentication Type for RMCP.
     * 为RMCP指定特定OEM身份验证类型的OEM/组织的IANA企业编号。
     */
    private int oemId;

    /**
     * Additional OEM-specific information for the OEM Authentication Type for RMCP.
     * RMCP OEM身份验证类型的其他特定于OEM的信息。
     */
    private byte oemData;

    public void setChannelNumber(byte channelNumber) {
        this.channelNumber = channelNumber;
    }

    public byte getChannelNumber() {
        return channelNumber;
    }

    public void setIpmiv20Support(boolean ipmiv20Support) {
        this.ipmiv20Support = ipmiv20Support;
    }

    public boolean isIpmiv20Support() {
        return ipmiv20Support;
    }

    public void setAuthenticationTypes(Collection<AuthenticationType> authenticationTypes) {
        this.authenticationTypes = authenticationTypes;
    }

    public Collection<AuthenticationType> getAuthenticationTypes() {
        return authenticationTypes;
    }

    public void setKgEnabled(boolean kgEnabled) {
        this.kgEnabled = kgEnabled;
    }

    public boolean isKgEnabled() {
        return kgEnabled;
    }

    public void setPerMessageAuthenticationEnabled(boolean perMessageAuthenticationEnabled) {
        this.perMessageAuthenticationEnabled = perMessageAuthenticationEnabled;
    }

    public boolean isPerMessageAuthenticationEnabled() {
        return perMessageAuthenticationEnabled;
    }

    public void setUserLevelAuthenticationEnabled(boolean userLevelAuthenticationEnabled) {
        this.userLevelAuthenticationEnabled = userLevelAuthenticationEnabled;
    }

    public boolean isUserLevelAuthenticationEnabled() {
        return userLevelAuthenticationEnabled;
    }

    public void setNonNullUsernamesEnabled(boolean nonNullUsernamesEnabled) {
        this.nonNullUsernamesEnabled = nonNullUsernamesEnabled;
    }

    public boolean isNonNullUsernamesEnabled() {
        return nonNullUsernamesEnabled;
    }

    public void setNullUsernamesEnabled(boolean nullUsernamesEnabled) {
        this.nullUsernamesEnabled = nullUsernamesEnabled;
    }

    public boolean isNullUsernamesEnabled() {
        return nullUsernamesEnabled;
    }

    public void setAnonymusLoginEnabled(boolean anonymusLoginEnabled) {
        this.anonymusLoginEnabled = anonymusLoginEnabled;
    }

    public boolean isAnonymusLoginEnabled() {
        return anonymusLoginEnabled;
    }

    public void setOemId(int oemId) {
        this.oemId = oemId;
    }

    public int getOemId() {
        return oemId;
    }

    public void setOemData(byte oemData) {
        this.oemData = oemData;
    }

    public byte getOemData() {
        return oemData;
    }
}
