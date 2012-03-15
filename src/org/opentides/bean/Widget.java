/*
   Licensed to the Apache Software Foundation (ASF) under one
   or more contributor license agreements.  See the NOTICE file
   distributed with this work for additional information
   regarding copyright ownership.  The ASF licenses this file
   to you under the Apache License, Version 2.0 (the
   "License"); you may not use this file except in compliance
   with the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing,
   software distributed under the License is distributed on an
   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
   KIND, either express or implied.  See the License for the
   specific language governing permissions and limitations
   under the License.    
 */

package org.opentides.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hightides.annotations.HiddenField;
import org.hightides.annotations.TextArea;
import org.hightides.annotations.TextField;
import org.opentides.util.StringUtil;

/**
 * The Widget entity is responsible for holding the dashboard widgets and and its cache.
 * To make dashboard load efficient, a cache is maintained to reduce large database queries.
 * User must be careful in using cache settings to avoid stale data.
 * 
 * @author ideyatech
 */

@Entity
@Table(name = "WIDGET")
public class Widget extends BaseEntity implements Searchable, Auditable, Uploadable, Sortable {

	private static final long serialVersionUID = -1621927178131406825L;
	
	public static final String TYPE_HTML = "html";
	public static final String TYPE_IMAGE = "image";
	
	@Column(name = "NAME", unique = true)
	@TextField(titleField=true, requiredField=true, searchable=true, listed=true)
	private String name;
	
	@Column(name = "TITLE")
	@TextField(requiredField=true, searchable=true, listed=true)
	private String title;
	
	@Column(name = "IS_SHOWN")
	private Boolean isShown;
	
	// this is the property that will hold the screenshot
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="SCREENSHOT_ID")
	private List<FileInfo> screenshot;
	
	@Column(name = "DESCRIPTION")
	@TextArea
	private String description;

	@Column(name = "URL", unique = true)
	@TextField(requiredField=true, searchable=true, listed=true)
	private String url;
	
	@Column(name = "ACCESS_CODE")
	@TextField(requiredField=true, searchable=true, listed=true)
	private String accessCode;
	
	@Column(name = "CACHE_DURATION")
	@TextField(listed=true,defaultValue="3600")
	private long cacheDuration;

	@Column(name = "LAST_CACHE_UPDATE")
	@Temporal(TemporalType.TIMESTAMP)
	@TextField(dateFormat="MMM dd, yyyy hh:mm:ss", listed=true)
	private Date lastCacheUpdate;

	@Lob
	@Column(name = "CACHE", columnDefinition = "LONGBLOB")
	private byte[] cache;

	@Column(name = "CACHE_TYPE")
	private String cacheType;
	
	@Column(name = "IS_USER_DEFINED")
	@HiddenField(defaultValue="true")
	private Boolean isUserDefined;
	
	@SuppressWarnings("unused")
	@OneToMany(cascade=CascadeType.ALL, mappedBy="widget", fetch = FetchType.LAZY)
    private List<UserWidgets> userWidget;
	
	@Transient
	private transient boolean isInstalled = false;
	
	public Widget() {
		super();
	}
	
	public Widget(String url, Widget parent) {
		this.setCacheDuration(parent.getCacheDuration());
		this.setAccessCode(parent.getAccessCode());
		this.setUrl(url);
		this.setLastCacheUpdate(parent.getLastCacheUpdate());
	}

	/**
	 * @return the name
	 */
	public String getName() {
		if(!StringUtil.isEmpty(name))
			return name.trim();
		else
			return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		if(!StringUtil.isEmpty(name))
			this.name = name.trim();
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the cacheDuration
	 */
	public long getCacheDuration() {
		return cacheDuration;
	}

	/**
	 * @param cacheDuration the cacheDuration to set
	 */
	public void setCacheDuration(long cacheDuration) {
		this.cacheDuration = cacheDuration;
	}

	/**
	 * @return the cache
	 */
	public byte[] getCache() {
		return cache;
	}

	/**
	 * @param cache the cache to set
	 */
	public void setCache(byte[] cache) {
		this.cache = cache;
	}

	
	/**
	 * @return the cacheType
	 */
	public String getCacheType() {
		return cacheType;
	}

	/**
	 * @param cacheType the cacheType to set
	 */
	public void setCacheType(String cacheType) {
		this.cacheType = cacheType;
	}

	/**
	 * @return the lastCacheUpdate
	 */
	public Date getLastCacheUpdate() {
		return lastCacheUpdate;
	}

	/**
	 * @param lastCacheUpdate the lastCacheUpdate to set
	 */
	public void setLastCacheUpdate(Date lastCacheUpdate) {
		this.lastCacheUpdate = lastCacheUpdate;
	}

	/**
	 * @return the accessCode
	 */
	public String getAccessCode() {
		if(!StringUtil.isEmpty(accessCode))
			return accessCode.trim();
		else
			return accessCode;
	}

	/**
	 * @param accessCode the accessCode to set
	 */
	public void setAccessCode(String accessCode) {
		if(!StringUtil.isEmpty(accessCode))
			this.accessCode = accessCode.trim();
	}
	
	/**
	 * @return the screenshot
	 */
	public List<FileInfo> getScreenshot() {
		return screenshot;
	}

	/**
	 * @param screenshot the screenshot to set
	 */
	public void setScreenshot(List<FileInfo> screenshot) {
		this.screenshot = screenshot;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the isUserDefined
	 */
	public Boolean getIsUserDefined() {
		return isUserDefined;
	}

	public Boolean getIsShown() {
		return isShown;
	}

	public void setIsShown(Boolean isShown) {
		this.isShown = isShown;
	}

	/**
	 * @param isUserDefined the isUserDefined to set
	 */
	public void setIsUserDefined(Boolean isUserDefined) {
		this.isUserDefined = isUserDefined;
	}

	/**
	 * @return the isInstalled
	 */
	public boolean isInstalled() {
		return isInstalled;
	}

	/**
	 * @param isInstalled the isInstalled to set
	 */
	public void setInstalled(boolean isInstalled) {
		this.isInstalled = isInstalled;
	}

	public List<FileInfo> getFiles() {
		return screenshot;
	}

	public void setFiles(List<FileInfo> files) {
		this.screenshot = files;		
	}

	public String getAuditMessage() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.opentides.bean.BaseEntity#getSearchProperties()
	 */
	@Override
	public List<String> getSearchProperties() {
		List<String> props = new ArrayList<String>();
		props.add("name");
		props.add("url");
		props.add("accessCode");
		props.add("isUserDefined");
		props.add("title");
		return props;
	}

	public List<AuditableField> getAuditableFields() {
		List<AuditableField> props = new ArrayList<AuditableField>();
		props.add(new AuditableField("name","Name"));
		props.add(new AuditableField("url","Url"));
		props.add(new AuditableField("accessCode","Access Code"));
		props.add(new AuditableField("cacheDuration","Cache Duration"));
		return props;
	}
	
	public AuditableField getPrimaryField() {
		return new AuditableField("name","Name");
	}
	
	public String getReference() { 
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((accessCode == null) ? 0 : accessCode.hashCode());
		result = prime * result
				+ ((cacheType == null) ? 0 : cacheType.hashCode());
		result = prime * result
				+ ((isUserDefined == null) ? 0 : isUserDefined.hashCode());
		result = prime * result
				+ ((lastCacheUpdate == null) ? 0 : lastCacheUpdate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Widget other = (Widget) obj;
		if (accessCode == null) {
			if (other.accessCode != null)
				return false;
		} else if (!accessCode.equals(other.accessCode))
			return false;
		if (cacheType == null) {
			if (other.cacheType != null)
				return false;
		} else if (!cacheType.equals(other.cacheType))
			return false;
		if (isUserDefined == null) {
			if (other.isUserDefined != null)
				return false;
		} else if (!isUserDefined.equals(other.isUserDefined))
			return false;
		if (lastCacheUpdate == null) {
			if (other.lastCacheUpdate != null)
				return false;
		} else if (!lastCacheUpdate.equals(other.lastCacheUpdate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public String getTitle() {
		if(!StringUtil.isEmpty(title))
			return title.trim();
		else
			return title;
	}

	public void setTitle(String title) {
		if(!StringUtil.isEmpty(title))
			this.title = title.trim();
	}
}
