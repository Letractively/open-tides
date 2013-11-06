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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author allantan
 *
 */
@Entity
@Table(name="USER_DEFINED_RECORD")
public class UserDefinedRecord extends BaseEntity implements Auditable, Searchable {

	private static final long serialVersionUID = 276235925368271733L;

    /**
     * Primary key of object being tracked.
     */
    @Column(name = "ENTITY_ID")
    private Long entityId;
    
    /**
     * Class type of object being tracked.
     */
    @SuppressWarnings({ "rawtypes" })
    @Column(name = "ENTITY_CLASS")
    private Class entityClass;
	
	@Column(name = "STRING_0")
	private String string0;
	@Column(name = "STRING_1")
	private String string1;
	@Column(name = "STRING_2")
	private String string2;
	@Column(name = "STRING_3")
	private String string3;
	@Column(name = "STRING_4")
	private String string4;
	@Column(name = "STRING_5")
	private String string5;
	@Column(name = "STRING_6")
	private String string6;
	@Column(name = "STRING_7")
	private String string7;
	@Column(name = "STRING_8")
	private String string8;
	@Column(name = "STRING_9")
	private String string9;
	
	@Column(name = "DATE_0")
	private Date date0;
	@Column(name = "DATE_1")
	private Date date1;
	@Column(name = "DATE_2")
	private Date date2;
	@Column(name = "DATE_3")
	private Date date3;
	@Column(name = "DATE_4")
	private Date date4;
	@Column(name = "DATE_5")
	private Date date5;
	@Column(name = "DATE_6")
	private Date date6;
	@Column(name = "DATE_7")
	private Date date7;
	@Column(name = "DATE_8")
	private Date date8;
	@Column(name = "DATE_9")
	private Date date9;

	@Transient
	private transient Date date0From;
	@Transient
	private transient Date date1From;
	@Transient
	private transient Date date2From;
	@Transient
	private transient Date date3From;
	@Transient
	private transient Date date4From;
	@Transient
	private transient Date date5From;
	@Transient
	private transient Date date6From;
	@Transient
	private transient Date date7From;
	@Transient
	private transient Date date8From;
	@Transient
	private transient Date date9From;
	
	@Transient
	private transient Date date0To;
	@Transient
	private transient Date date1To;
	@Transient
	private transient Date date2To;
	@Transient
	private transient Date date3To;
	@Transient
	private transient Date date4To;
	@Transient
	private transient Date date5To;
	@Transient
	private transient Date date6To;
	@Transient
	private transient Date date7To;
	@Transient
	private transient Date date8To;
	@Transient
	private transient Date date9To;

	@Column(name = "NUMBER_0")
	private Double number0;
	@Column(name = "NUMBER_1")
	private Double number1;
	@Column(name = "NUMBER_2")
	private Double number2;
	@Column(name = "NUMBER_3")
	private Double number3;
	@Column(name = "NUMBER_4")
	private Double number4;
	@Column(name = "NUMBER_5")
	private Double number5;
	@Column(name = "NUMBER_6")
	private Double number6;
	@Column(name = "NUMBER_7")
	private Double number7;
	@Column(name = "NUMBER_8")
	private Double number8;
	@Column(name = "NUMBER_9")
	private Double number9;
	
	@Column(name = "BOOLEAN_0")
	private Boolean boolean0;
	@Column(name = "BOOLEAN_1")
	private Boolean boolean1;
	@Column(name = "BOOLEAN_2")
	private Boolean boolean2;
	@Column(name = "BOOLEAN_3")
	private Boolean boolean3;
	@Column(name = "BOOLEAN_4")
	private Boolean boolean4;
	@Column(name = "BOOLEAN_5")
	private Boolean boolean5;
	@Column(name = "BOOLEAN_6")
	private Boolean boolean6;
	@Column(name = "BOOLEAN_7")
	private Boolean boolean7;
	@Column(name = "BOOLEAN_8")
	private Boolean boolean8;
	@Column(name = "BOOLEAN_9")
	private Boolean boolean9;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "DROPDOWN_0")
	private SystemCodes dropdown0;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "DROPDOWN_1")
	private SystemCodes dropdown1;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "DROPDOWN_2")
	private SystemCodes dropdown2;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "DROPDOWN_3")
	private SystemCodes dropdown3;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "DROPDOWN_4")
	private SystemCodes dropdown4;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "DROPDOWN_5")
	private SystemCodes dropdown5;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "DROPDOWN_6")
	private SystemCodes dropdown6;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "DROPDOWN_7")
	private SystemCodes dropdown7;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "DROPDOWN_8")
	private SystemCodes dropdown8;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "DROPDOWN_9")
	private SystemCodes dropdown9;
	
	@Transient
	private transient String reference;
	
	@Transient
	@SuppressWarnings("rawtypes")
	private static transient Map<Class, List<UserDefinedField>> udfMap = 
			Collections.synchronizedMap(new HashMap<Class, List<UserDefinedField>>());
	
	/**
	 * Getter method for entityId.
	 *
	 * @return the entityId
	 */
	public final Long getEntityId() {
		return entityId;
	}
	/**
	 * Setter method for entityId.
	 *
	 * @param entityId the entityId to set
	 */
	public final void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	/**
	 * Getter method for entityClass.
	 *
	 * @return the entityClass
	 */
	@SuppressWarnings("rawtypes")	
	public final Class getEntityClass() {
		return entityClass;
	}
	/**
	 * Setter method for entityClass.
	 *
	 * @param entityClass the entityClass to set
	 */
	@SuppressWarnings("rawtypes")	
	public final void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}
	
	/* (non-Javadoc)
	 * @see org.opentides.bean.BaseEntity#getSearchProperties()
	 */
	@Override
	public List<String> getSearchProperties() {
		List<String> searchProperties = new ArrayList<String>();
		List<UserDefinedField> meta = udfMap.get(this.entityClass);
		if (meta!=null) {
			for (UserDefinedField field: meta) {
				if (field.getSearchable())
					searchProperties.add(field.getUserField());
			}
		}
		return searchProperties;
	}
	
	/* (non-Javadoc)
	 * @see org.opentides.bean.BaseEntity#getAuditableFields()
	 */
	@Override
	public List<AuditableField> getAuditableFields() {
		List<AuditableField> auditable = new ArrayList<AuditableField>();
		List<UserDefinedField> meta = udfMap.get(this.entityClass);
		if (meta!=null) {
			for (UserDefinedField field: meta) {
				String fieldName = field.getUserField();
				// for dropdown values (i.e. SystemCodes, lets use the value field for audit log)
				if (fieldName.startsWith("dropdown")) 
					fieldName+=".value";
				auditable.add(new AuditableField(fieldName, field.getLabel()));
			}
		}
		return auditable;
	}
	
	/* (non-Javadoc)
	 * @see org.opentides.bean.Auditable#getPrimaryField()
	 */
	public AuditableField getPrimaryField() {
		return new AuditableField("","");
	}
	
	/**
	 * Standard support method for searching
	 * @param prefix - prefix to prepend in the field. 
	 * 				   This is usually the variable name of the udf in the class. (e.g. "udf.")
	 * @return
	 */
	public List<String> getSearchProperties(String prefix) {
		List<String> searchProperties = new ArrayList<String>();
		for (String prop:this.getSearchProperties()) {
			searchProperties.add(prefix+prop);
		}
		return searchProperties;
	}
	
	/**
	 * Standard support method for audit logging.
	 * @param prefix - prefix to prepend in the field. 
	 * 				   This is usually the variable name of the udf in the class. (e.g. "udf.")
	 * @return
	 */
	public List<AuditableField> getAuditableFields(String prefix) {
		List<AuditableField> auditable = new ArrayList<AuditableField>();
		for (AuditableField audit:this.getAuditableFields()) {
			auditable.add(new AuditableField(prefix+audit.getFieldName(), audit.getTitle()));
		}
		return auditable;		
	}

	/**
	 * Getter method for string0.
	 *
	 * @return the string0
	 */
	public final String getString0() {
		return string0;
	}
	/**
	 * Setter method for string0.
	 *
	 * @param string0 the string0 to set
	 */
	public final void setString0(String string0) {
		this.string0 = string0;
	}
	/**
	 * Getter method for string1.
	 *
	 * @return the string1
	 */
	public final String getString1() {
		return string1;
	}
	/**
	 * Setter method for string1.
	 *
	 * @param string1 the string1 to set
	 */
	public final void setString1(String string1) {
		this.string1 = string1;
	}
	/**
	 * Getter method for string2.
	 *
	 * @return the string2
	 */
	public final String getString2() {
		return string2;
	}
	/**
	 * Setter method for string2.
	 *
	 * @param string2 the string2 to set
	 */
	public final void setString2(String string2) {
		this.string2 = string2;
	}
	/**
	 * Getter method for string3.
	 *
	 * @return the string3
	 */
	public final String getString3() {
		return string3;
	}
	/**
	 * Setter method for string3.
	 *
	 * @param string3 the string3 to set
	 */
	public final void setString3(String string3) {
		this.string3 = string3;
	}
	/**
	 * Getter method for string4.
	 *
	 * @return the string4
	 */
	public final String getString4() {
		return string4;
	}
	/**
	 * Setter method for string4.
	 *
	 * @param string4 the string4 to set
	 */
	public final void setString4(String string4) {
		this.string4 = string4;
	}
	/**
	 * Getter method for string5.
	 *
	 * @return the string5
	 */
	public final String getString5() {
		return string5;
	}
	/**
	 * Setter method for string5.
	 *
	 * @param string5 the string5 to set
	 */
	public final void setString5(String string5) {
		this.string5 = string5;
	}
	/**
	 * Getter method for string6.
	 *
	 * @return the string6
	 */
	public final String getString6() {
		return string6;
	}
	/**
	 * Setter method for string6.
	 *
	 * @param string6 the string6 to set
	 */
	public final void setString6(String string6) {
		this.string6 = string6;
	}
	/**
	 * Getter method for string7.
	 *
	 * @return the string7
	 */
	public final String getString7() {
		return string7;
	}
	/**
	 * Setter method for string7.
	 *
	 * @param string7 the string7 to set
	 */
	public final void setString7(String string7) {
		this.string7 = string7;
	}
	/**
	 * Getter method for string8.
	 *
	 * @return the string8
	 */
	public final String getString8() {
		return string8;
	}
	/**
	 * Setter method for string8.
	 *
	 * @param string8 the string8 to set
	 */
	public final void setString8(String string8) {
		this.string8 = string8;
	}
	/**
	 * Getter method for string9.
	 *
	 * @return the string9
	 */
	public final String getString9() {
		return string9;
	}
	/**
	 * Setter method for string9.
	 *
	 * @param string9 the string9 to set
	 */
	public final void setString9(String string9) {
		this.string9 = string9;
	}
	/**
	 * Getter method for date0.
	 *
	 * @return the date0
	 */
	public final Date getDate0() {
		return date0;
	}
	/**
	 * Setter method for date0.
	 *
	 * @param date0 the date0 to set
	 */
	public final void setDate0(Date date0) {
		this.date0 = date0;
	}
	/**
	 * Getter method for date1.
	 *
	 * @return the date1
	 */
	public final Date getDate1() {
		return date1;
	}
	/**
	 * Setter method for date1.
	 *
	 * @param date1 the date1 to set
	 */
	public final void setDate1(Date date1) {
		this.date1 = date1;
	}
	/**
	 * Getter method for date2.
	 *
	 * @return the date2
	 */
	public final Date getDate2() {
		return date2;
	}
	/**
	 * Setter method for date2.
	 *
	 * @param date2 the date2 to set
	 */
	public final void setDate2(Date date2) {
		this.date2 = date2;
	}
	/**
	 * Getter method for date3.
	 *
	 * @return the date3
	 */
	public final Date getDate3() {
		return date3;
	}
	/**
	 * Setter method for date3.
	 *
	 * @param date3 the date3 to set
	 */
	public final void setDate3(Date date3) {
		this.date3 = date3;
	}
	/**
	 * Getter method for date4.
	 *
	 * @return the date4
	 */
	public final Date getDate4() {
		return date4;
	}
	/**
	 * Setter method for date4.
	 *
	 * @param date4 the date4 to set
	 */
	public final void setDate4(Date date4) {
		this.date4 = date4;
	}
	/**
	 * Getter method for date5.
	 *
	 * @return the date5
	 */
	public final Date getDate5() {
		return date5;
	}
	/**
	 * Setter method for date5.
	 *
	 * @param date5 the date5 to set
	 */
	public final void setDate5(Date date5) {
		this.date5 = date5;
	}
	/**
	 * Getter method for date6.
	 *
	 * @return the date6
	 */
	public final Date getDate6() {
		return date6;
	}
	/**
	 * Setter method for date6.
	 *
	 * @param date6 the date6 to set
	 */
	public final void setDate6(Date date6) {
		this.date6 = date6;
	}
	/**
	 * Getter method for date7.
	 *
	 * @return the date7
	 */
	public final Date getDate7() {
		return date7;
	}
	/**
	 * Setter method for date7.
	 *
	 * @param date7 the date7 to set
	 */
	public final void setDate7(Date date7) {
		this.date7 = date7;
	}
	/**
	 * Getter method for date8.
	 *
	 * @return the date8
	 */
	public final Date getDate8() {
		return date8;
	}
	/**
	 * Setter method for date8.
	 *
	 * @param date8 the date8 to set
	 */
	public final void setDate8(Date date8) {
		this.date8 = date8;
	}
	/**
	 * Getter method for date9.
	 *
	 * @return the date9
	 */
	public final Date getDate9() {
		return date9;
	}
	/**
	 * Setter method for date9.
	 *
	 * @param date9 the date9 to set
	 */
	public final void setDate9(Date date9) {
		this.date9 = date9;
	}
	public Date getDate0From() {
		return date0From;
	}
	public void setDate0From(Date date0From) {
		this.date0From = date0From;
	}
	public Date getDate1From() {
		return date1From;
	}
	public void setDate1From(Date date1From) {
		this.date1From = date1From;
	}
	public Date getDate2From() {
		return date2From;
	}
	public void setDate2From(Date date2From) {
		this.date2From = date2From;
	}
	public Date getDate3From() {
		return date3From;
	}
	public void setDate3From(Date date3From) {
		this.date3From = date3From;
	}
	public Date getDate4From() {
		return date4From;
	}
	public void setDate4From(Date date4From) {
		this.date4From = date4From;
	}
	public Date getDate5From() {
		return date5From;
	}
	public void setDate5From(Date date5From) {
		this.date5From = date5From;
	}
	public Date getDate6From() {
		return date6From;
	}
	public void setDate6From(Date date6From) {
		this.date6From = date6From;
	}
	public Date getDate7From() {
		return date7From;
	}
	public void setDate7From(Date date7From) {
		this.date7From = date7From;
	}
	public Date getDate8From() {
		return date8From;
	}
	public void setDate8From(Date date8From) {
		this.date8From = date8From;
	}
	public Date getDate9From() {
		return date9From;
	}
	public void setDate9From(Date date9From) {
		this.date9From = date9From;
	}
	public Date getDate0To() {
		return date0To;
	}
	public void setDate0To(Date date0To) {
		this.date0To = date0To;
	}
	public Date getDate1To() {
		return date1To;
	}
	public void setDate1To(Date date1To) {
		this.date1To = date1To;
	}
	public Date getDate2To() {
		return date2To;
	}
	public void setDate2To(Date date2To) {
		this.date2To = date2To;
	}
	public Date getDate3To() {
		return date3To;
	}
	public void setDate3To(Date date3To) {
		this.date3To = date3To;
	}
	public Date getDate4To() {
		return date4To;
	}
	public void setDate4To(Date date4To) {
		this.date4To = date4To;
	}
	public Date getDate5To() {
		return date5To;
	}
	public void setDate5To(Date date5To) {
		this.date5To = date5To;
	}
	public Date getDate6To() {
		return date6To;
	}
	public void setDate6To(Date date6To) {
		this.date6To = date6To;
	}
	public Date getDate7To() {
		return date7To;
	}
	public void setDate7To(Date date7To) {
		this.date7To = date7To;
	}
	public Date getDate8To() {
		return date8To;
	}
	public void setDate8To(Date date8To) {
		this.date8To = date8To;
	}
	public Date getDate9To() {
		return date9To;
	}
	public void setDate9To(Date date9To) {
		this.date9To = date9To;
	}
	/**
	 * Getter method for number0.
	 *
	 * @return the number0
	 */
	public final Double getNumber0() {
		return number0;
	}
	/**
	 * Setter method for number0.
	 *
	 * @param number0 the number0 to set
	 */
	public final void setNumber0(Double number0) {
		this.number0 = number0;
	}
	/**
	 * Getter method for number1.
	 *
	 * @return the number1
	 */
	public final Double getNumber1() {
		return number1;
	}
	/**
	 * Setter method for number1.
	 *
	 * @param number1 the number1 to set
	 */
	public final void setNumber1(Double number1) {
		this.number1 = number1;
	}
	/**
	 * Getter method for number2.
	 *
	 * @return the number2
	 */
	public final Double getNumber2() {
		return number2;
	}
	/**
	 * Setter method for number2.
	 *
	 * @param number2 the number2 to set
	 */
	public final void setNumber2(Double number2) {
		this.number2 = number2;
	}
	/**
	 * Getter method for number3.
	 *
	 * @return the number3
	 */
	public final Double getNumber3() {
		return number3;
	}
	/**
	 * Setter method for number3.
	 *
	 * @param number3 the number3 to set
	 */
	public final void setNumber3(Double number3) {
		this.number3 = number3;
	}
	/**
	 * Getter method for number4.
	 *
	 * @return the number4
	 */
	public final Double getNumber4() {
		return number4;
	}
	/**
	 * Setter method for number4.
	 *
	 * @param number4 the number4 to set
	 */
	public final void setNumber4(Double number4) {
		this.number4 = number4;
	}
	/**
	 * Getter method for number5.
	 *
	 * @return the number5
	 */
	public final Double getNumber5() {
		return number5;
	}
	/**
	 * Setter method for number5.
	 *
	 * @param number5 the number5 to set
	 */
	public final void setNumber5(Double number5) {
		this.number5 = number5;
	}
	/**
	 * Getter method for number6.
	 *
	 * @return the number6
	 */
	public final Double getNumber6() {
		return number6;
	}
	/**
	 * Setter method for number6.
	 *
	 * @param number6 the number6 to set
	 */
	public final void setNumber6(Double number6) {
		this.number6 = number6;
	}
	/**
	 * Getter method for number7.
	 *
	 * @return the number7
	 */
	public final Double getNumber7() {
		return number7;
	}
	/**
	 * Setter method for number7.
	 *
	 * @param number7 the number7 to set
	 */
	public final void setNumber7(Double number7) {
		this.number7 = number7;
	}
	/**
	 * Getter method for number8.
	 *
	 * @return the number8
	 */
	public final Double getNumber8() {
		return number8;
	}
	/**
	 * Setter method for number8.
	 *
	 * @param number8 the number8 to set
	 */
	public final void setNumber8(Double number8) {
		this.number8 = number8;
	}
	/**
	 * Getter method for number9.
	 *
	 * @return the number9
	 */
	public final Double getNumber9() {
		return number9;
	}
	/**
	 * Setter method for number9.
	 *
	 * @param number9 the number9 to set
	 */
	public final void setNumber9(Double number9) {
		this.number9 = number9;
	}
	/**
	 * Getter method for boolean0.
	 *
	 * @return the boolean0
	 */
	public final Boolean getBoolean0() {
		return boolean0;
	}
	/**
	 * Setter method for boolean0.
	 *
	 * @param boolean0 the boolean0 to set
	 */
	public final void setBoolean0(Boolean boolean0) {
		this.boolean0 = boolean0;
	}
	/**
	 * Getter method for boolean1.
	 *
	 * @return the boolean1
	 */
	public final Boolean getBoolean1() {
		return boolean1;
	}
	/**
	 * Setter method for boolean1.
	 *
	 * @param boolean1 the boolean1 to set
	 */
	public final void setBoolean1(Boolean boolean1) {
		this.boolean1 = boolean1;
	}
	/**
	 * Getter method for boolean2.
	 *
	 * @return the boolean2
	 */
	public final Boolean getBoolean2() {
		return boolean2;
	}
	/**
	 * Setter method for boolean2.
	 *
	 * @param boolean2 the boolean2 to set
	 */
	public final void setBoolean2(Boolean boolean2) {
		this.boolean2 = boolean2;
	}
	/**
	 * Getter method for boolean3.
	 *
	 * @return the boolean3
	 */
	public final Boolean getBoolean3() {
		return boolean3;
	}
	/**
	 * Setter method for boolean3.
	 *
	 * @param boolean3 the boolean3 to set
	 */
	public final void setBoolean3(Boolean boolean3) {
		this.boolean3 = boolean3;
	}
	/**
	 * Getter method for boolean4.
	 *
	 * @return the boolean4
	 */
	public final Boolean getBoolean4() {
		return boolean4;
	}
	/**
	 * Setter method for boolean4.
	 *
	 * @param boolean4 the boolean4 to set
	 */
	public final void setBoolean4(Boolean boolean4) {
		this.boolean4 = boolean4;
	}
	/**
	 * Getter method for boolean5.
	 *
	 * @return the boolean5
	 */
	public final Boolean getBoolean5() {
		return boolean5;
	}
	/**
	 * Setter method for boolean5.
	 *
	 * @param boolean5 the boolean5 to set
	 */
	public final void setBoolean5(Boolean boolean5) {
		this.boolean5 = boolean5;
	}
	/**
	 * Getter method for boolean6.
	 *
	 * @return the boolean6
	 */
	public final Boolean getBoolean6() {
		return boolean6;
	}
	/**
	 * Setter method for boolean6.
	 *
	 * @param boolean6 the boolean6 to set
	 */
	public final void setBoolean6(Boolean boolean6) {
		this.boolean6 = boolean6;
	}
	/**
	 * Getter method for boolean7.
	 *
	 * @return the boolean7
	 */
	public final Boolean getBoolean7() {
		return boolean7;
	}
	/**
	 * Setter method for boolean7.
	 *
	 * @param boolean7 the boolean7 to set
	 */
	public final void setBoolean7(Boolean boolean7) {
		this.boolean7 = boolean7;
	}
	/**
	 * Getter method for boolean8.
	 *
	 * @return the boolean8
	 */
	public final Boolean getBoolean8() {
		return boolean8;
	}
	/**
	 * Setter method for boolean8.
	 *
	 * @param boolean8 the boolean8 to set
	 */
	public final void setBoolean8(Boolean boolean8) {
		this.boolean8 = boolean8;
	}
	/**
	 * Getter method for boolean9.
	 *
	 * @return the boolean9
	 */
	public final Boolean getBoolean9() {
		return boolean9;
	}
	/**
	 * Setter method for boolean9.
	 *
	 * @param boolean9 the boolean9 to set
	 */
	public final void setBoolean9(Boolean boolean9) {
		this.boolean9 = boolean9;
	}
	/**
	 * Getter method for dropdown0.
	 *
	 * @return the dropdown0
	 */
	public final SystemCodes getDropdown0() {
		return dropdown0;
	}
	/**
	 * Setter method for dropdown0.
	 *
	 * @param dropdown0 the dropdown0 to set
	 */
	public final void setDropdown0(SystemCodes dropdown0) {
		this.dropdown0 = dropdown0;
	}
	/**
	 * Getter method for dropdown1.
	 *
	 * @return the dropdown1
	 */
	public final SystemCodes getDropdown1() {
		return dropdown1;
	}
	/**
	 * Setter method for dropdown1.
	 *
	 * @param dropdown1 the dropdown1 to set
	 */
	public final void setDropdown1(SystemCodes dropdown1) {
		this.dropdown1 = dropdown1;
	}
	/**
	 * Getter method for dropdown2.
	 *
	 * @return the dropdown2
	 */
	public final SystemCodes getDropdown2() {
		return dropdown2;
	}
	/**
	 * Setter method for dropdown2.
	 *
	 * @param dropdown2 the dropdown2 to set
	 */
	public final void setDropdown2(SystemCodes dropdown2) {
		this.dropdown2 = dropdown2;
	}
	/**
	 * Getter method for dropdown3.
	 *
	 * @return the dropdown3
	 */
	public final SystemCodes getDropdown3() {
		return dropdown3;
	}
	/**
	 * Setter method for dropdown3.
	 *
	 * @param dropdown3 the dropdown3 to set
	 */
	public final void setDropdown3(SystemCodes dropdown3) {
		this.dropdown3 = dropdown3;
	}
	/**
	 * Getter method for dropdown4.
	 *
	 * @return the dropdown4
	 */
	public final SystemCodes getDropdown4() {
		return dropdown4;
	}
	/**
	 * Setter method for dropdown4.
	 *
	 * @param dropdown4 the dropdown4 to set
	 */
	public final void setDropdown4(SystemCodes dropdown4) {
		this.dropdown4 = dropdown4;
	}
	/**
	 * Getter method for dropdown5.
	 *
	 * @return the dropdown5
	 */
	public final SystemCodes getDropdown5() {
		return dropdown5;
	}
	/**
	 * Setter method for dropdown5.
	 *
	 * @param dropdown5 the dropdown5 to set
	 */
	public final void setDropdown5(SystemCodes dropdown5) {
		this.dropdown5 = dropdown5;
	}
	/**
	 * Getter method for dropdown6.
	 *
	 * @return the dropdown6
	 */
	public final SystemCodes getDropdown6() {
		return dropdown6;
	}
	/**
	 * Setter method for dropdown6.
	 *
	 * @param dropdown6 the dropdown6 to set
	 */
	public final void setDropdown6(SystemCodes dropdown6) {
		this.dropdown6 = dropdown6;
	}
	/**
	 * Getter method for dropdown7.
	 *
	 * @return the dropdown7
	 */
	public final SystemCodes getDropdown7() {
		return dropdown7;
	}
	/**
	 * Setter method for dropdown7.
	 *
	 * @param dropdown7 the dropdown7 to set
	 */
	public final void setDropdown7(SystemCodes dropdown7) {
		this.dropdown7 = dropdown7;
	}
	/**
	 * Getter method for dropdown8.
	 *
	 * @return the dropdown8
	 */
	public final SystemCodes getDropdown8() {
		return dropdown8;
	}
	/**
	 * Setter method for dropdown8.
	 *
	 * @param dropdown8 the dropdown8 to set
	 */
	public final void setDropdown8(SystemCodes dropdown8) {
		this.dropdown8 = dropdown8;
	}
	/**
	 * Getter method for dropdown9.
	 *
	 * @return the dropdown9
	 */
	public final SystemCodes getDropdown9() {
		return dropdown9;
	}
	/**
	 * Setter method for dropdown9.
	 *
	 * @param dropdown9 the dropdown9 to set
	 */
	public final void setDropdown9(SystemCodes dropdown9) {
		this.dropdown9 = dropdown9;
	}
	/**
	 * Getter method for udf.
	 *
	 * @return the udf
	 */
	@SuppressWarnings("rawtypes")
	public static final List<UserDefinedField> getUdf(Class clazz) {
		return udfMap.get(clazz);
	}	
	/**
	 * Setter method for udf.
	 *
	 * @param udf the udf to set
	 */
	@SuppressWarnings("rawtypes")
	public static final void setUdf(Class clazz, List<UserDefinedField> udf) {
		udfMap.put(clazz, udf);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((boolean0 == null) ? 0 : boolean0.hashCode());
		result = prime * result
				+ ((boolean1 == null) ? 0 : boolean1.hashCode());
		result = prime * result
				+ ((boolean2 == null) ? 0 : boolean2.hashCode());
		result = prime * result
				+ ((boolean3 == null) ? 0 : boolean3.hashCode());
		result = prime * result
				+ ((boolean4 == null) ? 0 : boolean4.hashCode());
		result = prime * result
				+ ((boolean5 == null) ? 0 : boolean5.hashCode());
		result = prime * result
				+ ((boolean6 == null) ? 0 : boolean6.hashCode());
		result = prime * result
				+ ((boolean7 == null) ? 0 : boolean7.hashCode());
		result = prime * result
				+ ((boolean8 == null) ? 0 : boolean8.hashCode());
		result = prime * result
				+ ((boolean9 == null) ? 0 : boolean9.hashCode());
		result = prime * result + ((date0 == null) ? 0 : date0.hashCode());
		result = prime * result + ((date1 == null) ? 0 : date1.hashCode());
		result = prime * result + ((date2 == null) ? 0 : date2.hashCode());
		result = prime * result + ((date3 == null) ? 0 : date3.hashCode());
		result = prime * result + ((date4 == null) ? 0 : date4.hashCode());
		result = prime * result + ((date5 == null) ? 0 : date5.hashCode());
		result = prime * result + ((date6 == null) ? 0 : date6.hashCode());
		result = prime * result + ((date7 == null) ? 0 : date7.hashCode());
		result = prime * result + ((date8 == null) ? 0 : date8.hashCode());
		result = prime * result + ((date9 == null) ? 0 : date9.hashCode());
		result = prime * result
				+ ((dropdown0 == null) ? 0 : dropdown0.hashCode());
		result = prime * result
				+ ((dropdown1 == null) ? 0 : dropdown1.hashCode());
		result = prime * result
				+ ((dropdown2 == null) ? 0 : dropdown2.hashCode());
		result = prime * result
				+ ((dropdown3 == null) ? 0 : dropdown3.hashCode());
		result = prime * result
				+ ((dropdown4 == null) ? 0 : dropdown4.hashCode());
		result = prime * result
				+ ((dropdown5 == null) ? 0 : dropdown5.hashCode());
		result = prime * result
				+ ((dropdown6 == null) ? 0 : dropdown6.hashCode());
		result = prime * result
				+ ((dropdown7 == null) ? 0 : dropdown7.hashCode());
		result = prime * result
				+ ((dropdown8 == null) ? 0 : dropdown8.hashCode());
		result = prime * result
				+ ((dropdown9 == null) ? 0 : dropdown9.hashCode());
		result = prime * result
				+ ((entityClass == null) ? 0 : entityClass.hashCode());
		result = prime * result
				+ ((entityId == null) ? 0 : entityId.hashCode());
		result = prime * result + ((number0 == null) ? 0 : number0.hashCode());
		result = prime * result + ((number1 == null) ? 0 : number1.hashCode());
		result = prime * result + ((number2 == null) ? 0 : number2.hashCode());
		result = prime * result + ((number3 == null) ? 0 : number3.hashCode());
		result = prime * result + ((number4 == null) ? 0 : number4.hashCode());
		result = prime * result + ((number5 == null) ? 0 : number5.hashCode());
		result = prime * result + ((number6 == null) ? 0 : number6.hashCode());
		result = prime * result + ((number7 == null) ? 0 : number7.hashCode());
		result = prime * result + ((number8 == null) ? 0 : number8.hashCode());
		result = prime * result + ((number9 == null) ? 0 : number9.hashCode());
		result = prime * result + ((string0 == null) ? 0 : string0.hashCode());
		result = prime * result + ((string1 == null) ? 0 : string1.hashCode());
		result = prime * result + ((string2 == null) ? 0 : string2.hashCode());
		result = prime * result + ((string3 == null) ? 0 : string3.hashCode());
		result = prime * result + ((string4 == null) ? 0 : string4.hashCode());
		result = prime * result + ((string5 == null) ? 0 : string5.hashCode());
		result = prime * result + ((string6 == null) ? 0 : string6.hashCode());
		result = prime * result + ((string7 == null) ? 0 : string7.hashCode());
		result = prime * result + ((string8 == null) ? 0 : string8.hashCode());
		result = prime * result + ((string9 == null) ? 0 : string9.hashCode());
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
		UserDefinedRecord other = (UserDefinedRecord) obj;
		if (boolean0 == null) {
			if (other.boolean0 != null)
				return false;
		} else if (!boolean0.equals(other.boolean0))
			return false;
		if (boolean1 == null) {
			if (other.boolean1 != null)
				return false;
		} else if (!boolean1.equals(other.boolean1))
			return false;
		if (boolean2 == null) {
			if (other.boolean2 != null)
				return false;
		} else if (!boolean2.equals(other.boolean2))
			return false;
		if (boolean3 == null) {
			if (other.boolean3 != null)
				return false;
		} else if (!boolean3.equals(other.boolean3))
			return false;
		if (boolean4 == null) {
			if (other.boolean4 != null)
				return false;
		} else if (!boolean4.equals(other.boolean4))
			return false;
		if (boolean5 == null) {
			if (other.boolean5 != null)
				return false;
		} else if (!boolean5.equals(other.boolean5))
			return false;
		if (boolean6 == null) {
			if (other.boolean6 != null)
				return false;
		} else if (!boolean6.equals(other.boolean6))
			return false;
		if (boolean7 == null) {
			if (other.boolean7 != null)
				return false;
		} else if (!boolean7.equals(other.boolean7))
			return false;
		if (boolean8 == null) {
			if (other.boolean8 != null)
				return false;
		} else if (!boolean8.equals(other.boolean8))
			return false;
		if (boolean9 == null) {
			if (other.boolean9 != null)
				return false;
		} else if (!boolean9.equals(other.boolean9))
			return false;
		if (date0 == null) {
			if (other.date0 != null)
				return false;
		} else if (!date0.equals(other.date0))
			return false;
		if (date1 == null) {
			if (other.date1 != null)
				return false;
		} else if (!date1.equals(other.date1))
			return false;
		if (date2 == null) {
			if (other.date2 != null)
				return false;
		} else if (!date2.equals(other.date2))
			return false;
		if (date3 == null) {
			if (other.date3 != null)
				return false;
		} else if (!date3.equals(other.date3))
			return false;
		if (date4 == null) {
			if (other.date4 != null)
				return false;
		} else if (!date4.equals(other.date4))
			return false;
		if (date5 == null) {
			if (other.date5 != null)
				return false;
		} else if (!date5.equals(other.date5))
			return false;
		if (date6 == null) {
			if (other.date6 != null)
				return false;
		} else if (!date6.equals(other.date6))
			return false;
		if (date7 == null) {
			if (other.date7 != null)
				return false;
		} else if (!date7.equals(other.date7))
			return false;
		if (date8 == null) {
			if (other.date8 != null)
				return false;
		} else if (!date8.equals(other.date8))
			return false;
		if (date9 == null) {
			if (other.date9 != null)
				return false;
		} else if (!date9.equals(other.date9))
			return false;
		if (dropdown0 == null) {
			if (other.dropdown0 != null)
				return false;
		} else if (!dropdown0.equals(other.dropdown0))
			return false;
		if (dropdown1 == null) {
			if (other.dropdown1 != null)
				return false;
		} else if (!dropdown1.equals(other.dropdown1))
			return false;
		if (dropdown2 == null) {
			if (other.dropdown2 != null)
				return false;
		} else if (!dropdown2.equals(other.dropdown2))
			return false;
		if (dropdown3 == null) {
			if (other.dropdown3 != null)
				return false;
		} else if (!dropdown3.equals(other.dropdown3))
			return false;
		if (dropdown4 == null) {
			if (other.dropdown4 != null)
				return false;
		} else if (!dropdown4.equals(other.dropdown4))
			return false;
		if (dropdown5 == null) {
			if (other.dropdown5 != null)
				return false;
		} else if (!dropdown5.equals(other.dropdown5))
			return false;
		if (dropdown6 == null) {
			if (other.dropdown6 != null)
				return false;
		} else if (!dropdown6.equals(other.dropdown6))
			return false;
		if (dropdown7 == null) {
			if (other.dropdown7 != null)
				return false;
		} else if (!dropdown7.equals(other.dropdown7))
			return false;
		if (dropdown8 == null) {
			if (other.dropdown8 != null)
				return false;
		} else if (!dropdown8.equals(other.dropdown8))
			return false;
		if (dropdown9 == null) {
			if (other.dropdown9 != null)
				return false;
		} else if (!dropdown9.equals(other.dropdown9))
			return false;
		if (entityClass == null) {
			if (other.entityClass != null)
				return false;
		} else if (!entityClass.equals(other.entityClass))
			return false;
		if (entityId == null) {
			if (other.entityId != null)
				return false;
		} else if (!entityId.equals(other.entityId))
			return false;
		if (number0 == null) {
			if (other.number0 != null)
				return false;
		} else if (!number0.equals(other.number0))
			return false;
		if (number1 == null) {
			if (other.number1 != null)
				return false;
		} else if (!number1.equals(other.number1))
			return false;
		if (number2 == null) {
			if (other.number2 != null)
				return false;
		} else if (!number2.equals(other.number2))
			return false;
		if (number3 == null) {
			if (other.number3 != null)
				return false;
		} else if (!number3.equals(other.number3))
			return false;
		if (number4 == null) {
			if (other.number4 != null)
				return false;
		} else if (!number4.equals(other.number4))
			return false;
		if (number5 == null) {
			if (other.number5 != null)
				return false;
		} else if (!number5.equals(other.number5))
			return false;
		if (number6 == null) {
			if (other.number6 != null)
				return false;
		} else if (!number6.equals(other.number6))
			return false;
		if (number7 == null) {
			if (other.number7 != null)
				return false;
		} else if (!number7.equals(other.number7))
			return false;
		if (number8 == null) {
			if (other.number8 != null)
				return false;
		} else if (!number8.equals(other.number8))
			return false;
		if (number9 == null) {
			if (other.number9 != null)
				return false;
		} else if (!number9.equals(other.number9))
			return false;
		if (string0 == null) {
			if (other.string0 != null)
				return false;
		} else if (!string0.equals(other.string0))
			return false;
		if (string1 == null) {
			if (other.string1 != null)
				return false;
		} else if (!string1.equals(other.string1))
			return false;
		if (string2 == null) {
			if (other.string2 != null)
				return false;
		} else if (!string2.equals(other.string2))
			return false;
		if (string3 == null) {
			if (other.string3 != null)
				return false;
		} else if (!string3.equals(other.string3))
			return false;
		if (string4 == null) {
			if (other.string4 != null)
				return false;
		} else if (!string4.equals(other.string4))
			return false;
		if (string5 == null) {
			if (other.string5 != null)
				return false;
		} else if (!string5.equals(other.string5))
			return false;
		if (string6 == null) {
			if (other.string6 != null)
				return false;
		} else if (!string6.equals(other.string6))
			return false;
		if (string7 == null) {
			if (other.string7 != null)
				return false;
		} else if (!string7.equals(other.string7))
			return false;
		if (string8 == null) {
			if (other.string8 != null)
				return false;
		} else if (!string8.equals(other.string8))
			return false;
		if (string9 == null) {
			if (other.string9 != null)
				return false;
		} else if (!string9.equals(other.string9))
			return false;
		return true;
	}
	
	/**
	 * Setter method for reference.
	 *
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
		this.setUserId();
	}
	
	/* (non-Javadoc)
	 * @see org.opentides.bean.BaseEntity#getReference()
	 */
	@Override
	public String getReference() {
		return reference;
	}
		
}
