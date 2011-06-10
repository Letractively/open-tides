/**
 * 
 */
package org.opentides.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hightides.annotations.TextArea;
import org.hightides.annotations.TextField;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author allantan
 *
 */
@Entity
@Table(name = "DYNAMIC_REPORT")
public class DynamicReport extends BaseEntity implements Searchable, Auditable, Uploadable, Sortable {

	private static final long serialVersionUID = -8372709433123880931L;

	public static final String REPORT_FORMAT = "reportFormat";
	public static final String REPORT_FILE   = "reportFile";

	public static final String FORMAT_IMAGE  ="image";
	public static final String FORMAT_HTML   ="html";
	public static final String FORMAT_PDF    ="pdf";
	public static final String FORMAT_EXCEL  ="xls";
	
	@Column(name = "NAME", unique = true)
	@TextField(titleField=true, requiredField=true, searchable=true, listed=true)
	private String name;
	
	// this is the property that will hold the screenshot
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="REPORT_SCREENSHOT_ID")
	private List<FileInfo> screenshot;
	
	@Column(name = "DESCRIPTION")
	@TextArea
	private String description;
	
	@Column(name = "ACCESS_CODE")
	@TextField(requiredField=true, searchable=true, listed=true)
	private String accessCode;
		
	@Column(name = "REPORT_FILE")
	private String reportFile;
	
	@Column(name = "REPORT_PATH")
	private String reportPath;
	
	@Transient
	private transient CommonsMultipartFile jasperFile;

	@Transient
	private transient CommonsMultipartFile jrxmlFile;	
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FileInfo> getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(List<FileInfo> screenshot) {
		this.screenshot = screenshot;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public String getReportFile() {
		return reportFile;
	}

	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}
	
	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	public CommonsMultipartFile getJasperFile() {
		return jasperFile;
	}

	public void setJasperFile(CommonsMultipartFile jasperFile) {
		this.jasperFile = jasperFile;
	}

	public CommonsMultipartFile getJrxmlFile() {
		return jrxmlFile;
	}

	public void setJrxmlFile(CommonsMultipartFile jrxmlFile) {
		this.jrxmlFile = jrxmlFile;
	}

	public List<AuditableField> getAuditableFields() {
		List<AuditableField> props = new ArrayList<AuditableField>();
		props.add(new AuditableField("name","Name"));
		props.add(new AuditableField("reportFile","Report File"));
		props.add(new AuditableField("accessCode","Access Code"));		
		return props;
	}

	
	@Override
	public List<String> getSearchProperties() {
		List<String> props = new ArrayList<String>();
		props.add("name");
		props.add("reportFile");
		props.add("accessCode");
		return props;
	}

	public AuditableField getPrimaryField() {
		return new AuditableField("name","Name");
	}

	public String getReference() {
		return null;
	}

	public Boolean isSkipAudit() {
		return false;
	}

	public List<FileInfo> getFiles() {
		return screenshot;
	}

	public void setFiles(List<FileInfo> files) {
		if (!files.isEmpty()) {
			String filename = files.get(0).getFilename();
			if (filename.endsWith(".png") ||
				filename.endsWith(".gif") ||
				filename.endsWith(".jpg")) {
				this.screenshot = files;
			}
		}
	}
}