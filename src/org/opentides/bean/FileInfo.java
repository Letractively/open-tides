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

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This bean is holder of uploaded file information. 
 * Used by BaseUploadEntity to store uploaded files.
 * 
 * @author allantan
 *
 */
@Entity
@Table(name="FILE_INFO")
public class FileInfo extends BaseEntity implements Auditable {

	private static final long serialVersionUID = 3526479205130157604L;

	@Column(name="FILENAME", nullable=false)
	private String filename;

	@Column(name="FULL_PATH", length=2000)
	private String fullPath;

	@Column(name="FILE_SIZE", nullable=false)
	private Long fileSize;

	@Column(name="ORIGINAL_FILENAME")
	private String originalFileName;

	@Column(name="AUTHOR_ID")
	private Long authorId;


	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Deprecated
	public final String getFileName() {
		return filename;
	}

	@Deprecated
	public final void setFileName(String fileName) {
		filename = fileName;
	}

	/**
	 * @return the fullPath
	 */
	public final String getFullPath() {
		return fullPath;
	}

	/**
	 * @param fullPath the fullPath to set
	 */
	public final void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	/**
	 * @return the fileSize
	 */
	public final Long getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public final void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	/**
	 * @return the fileSize in kilobytes
	 */
	public Long getFileSizeInKB() {
		return fileSize / 1024;
	}

	/**
	 * 
	 * @return  id of the author who uploaded the attachment
	 */
	public Long getAuthorId() {
		return authorId;
	}

	/**
	 * Sets the author who uploaded the attachment
	 * @param authorId
	 */
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opentides.bean.BaseEntity#getAuditableFields()
	 */
	@Override
	public List<AuditableField> getAuditableFields() {
		List<AuditableField> auditableFields = super.getAuditableFields();
		auditableFields.add(new AuditableField("authorId", "Author ID"));
		auditableFields.add(new AuditableField("originalFileName",
				"Original File Name"));
		auditableFields.add(new AuditableField("fileSize", "File Size"));
		auditableFields.add(new AuditableField("fullPath", "Full Path"));

		return auditableFields;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opentides.bean.Auditable#getPrimaryField()
	 */
	@Override
	public AuditableField getPrimaryField() {
		return new AuditableField("fileName", "File Name");
	}

}