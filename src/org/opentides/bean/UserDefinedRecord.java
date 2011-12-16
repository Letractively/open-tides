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
public class UserDefinedRecord extends BaseEntity {

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
		for (UserDefinedField field: meta) {
			if (field.getSearchable())
				searchProperties.add("udf."+field.getUserField());
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
		for (UserDefinedField field: meta) {
			auditable.add(new AuditableField("udf."+field.getUserField(), field.getLabel()));
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

}
