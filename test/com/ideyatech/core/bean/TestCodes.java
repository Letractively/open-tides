package com.ideyatech.core.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hightides.annotations.CheckBox;
import org.hightides.annotations.Controller;
import org.hightides.annotations.Dao;
import org.hightides.annotations.DropDown;
import org.hightides.annotations.Page;
import org.hightides.annotations.RadioButton;
import org.hightides.annotations.Service;
import org.hightides.annotations.TextField;
import org.opentides.bean.Auditable;
import org.opentides.bean.AuditableField;
import org.opentides.bean.BaseCriteria;
import org.opentides.bean.BaseEntity;
import org.opentides.bean.SystemCodes;

@Entity
@Service
@Dao
@Controller
@Page
@Table(name="TEST_CODES")
/**
 * The system codes class is a lookup table used for drop-downs.
 * Developer must ensure category is consistently used in the application.
 */
public class TestCodes extends BaseEntity implements Serializable, BaseCriteria, Auditable {
	
	private static final long serialVersionUID = -4142599915292096152L;
	private static final int CATEGORY_LIST_LENGTH = 52;
	
	@Column(name = "KEY_", unique=true)
	@TextField
	private String key;

	@Column(name = "VALUE_", nullable=true)
	@TextField
	private String value;

	@Column(name = "NUMBER_VALUE", nullable=true)
	@TextField
	private Long numberValue; 

	@Column(name = "CATEGORY_")
	@DropDown(category="CATEGORY", titleField=true, searchable=true)
	private String category;
	
	@Column(name = "status")
	@DropDown(category="STATUS", searchable=true)
	private SystemCodes status;

	@Column(name = "TYPE_")
	@DropDown(options={"Low", "Medium", "High"}, searchable=true)
	private String[] type;
	
	@Column(name = "LEVEL_")
	@CheckBox(category="LEVEL", titleField=true, searchable=true)
	private String[] level;
	
	@Column(name = "GENDER_")
	@RadioButton(options={"Male", "Female"}, searchable=true)
	private String[] gender;
	
	@Transient
	private transient String auditMessage = "";
	
	@Transient
	private transient Boolean skipAudit = false;
	
	public TestCodes() {
	}
	
	public TestCodes(String category, String key, String value) {
		super();
		setCategory(category);
		this.key = key;
		this.value = value;
	}	
	
	public TestCodes(String key) {
		super();
		this.key = key;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category2 the category to set
	 */
	public void setCategory(String category2) {
		this.category = category2;
	}

	/**
	 * @return the numberValue
	 */
	public final Long getNumberValue() {
		return numberValue;
	}

	/**
	 * @param numberValue the numberValue to set
	 */
	public final void setNumberValue(Long numberValue) {
		this.numberValue = numberValue;
	}
	
	public final void incrementNumberValue() {
		if (numberValue == null)
			numberValue = Long.valueOf(1);
		else
			numberValue++;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TestCodes other = (TestCodes) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
	
   public String getTruncatedValue() {
       if (value.length() > CATEGORY_LIST_LENGTH) {
           return value.substring(0, CATEGORY_LIST_LENGTH - 4) + "...";
       }
       return value;
   }

	public List<String> getSearchProperties() {
		List<String> props = new ArrayList<String>();
		props.add("key");
		props.add("value");
		props.add("category");
		return props;
	}

	public List<AuditableField> getAuditableFields() {
		List<AuditableField> props = new ArrayList<AuditableField>();
		props.add(new AuditableField("key","Key"));
		props.add(new AuditableField("value","Value"));
		props.add(new AuditableField("category","Category"));
		props.add(new AuditableField("status","Status"));
		return props;
	}

	public AuditableField getPrimaryField() {
		return new AuditableField("key","Key");
	}

	/**
	 * @param auditMessage the auditMessage to set
	 */
	public final void setAuditMessage(String auditMessage) {
		this.auditMessage = auditMessage;
	}

	public String getAuditMessage() {
		return this.auditMessage;
	}

	/**
	 * @param skipAudit the skipAudit to set
	 */
	public final void setSkipAudit(Boolean skipAudit) {
		this.skipAudit = skipAudit;
	}

	public Boolean skipAudit() {
		return skipAudit;
	}
	
	public String getReference() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the type
	 */
	public String[] getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String[] type) {
		this.type = type;
	}

	/**
	 * @return the level
	 */
	public String[] getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(String[] level) {
		this.level = level;
	}

	/**
	 * @return the gender
	 */
	public String[] getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String[] gender) {
		this.gender = gender;
	}

	/**
	 * @return the status
	 */
	public SystemCodes getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(SystemCodes status) {
		this.status = status;
	}
	
}