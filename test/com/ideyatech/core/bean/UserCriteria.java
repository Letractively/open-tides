package com.ideyatech.core.bean;

import java.util.ArrayList;
import java.util.List;

import org.opentides.bean.AuditableField;
import org.opentides.bean.SystemCodes;
import org.opentides.bean.user.BaseUser;


/**
 * This is a test class used to check if CrudUtil.retrieveObjectValue
 * and retrieveObjectType are working
 * @author allantan
 *
 */
public class UserCriteria extends BaseUser {

	private List<SystemCodes> favorites;
	private List<String> alias;
	
	/* (non-Javadoc)
	 * @see com.ideyatech.core.bean.user.BaseUser#getSearchProperties()
	 */
	@Override
	public List<String> getSearchProperties() {
		List<String> props = new ArrayList<String>();
		props.add("firstName");
		props.add("lastName");
		props.add("emailAddress");
		props.add("favorites.value");
		props.add("credential.username");
		props.add("credential.enabled");
		props.add("credential.id");
		return props;
	}
	
	
	/* (non-Javadoc)
	 * @see org.opentides.bean.BaseEntity#getAuditableFields()
	 */
	@Override
	public List<AuditableField> getAuditableFields() {
		List<AuditableField> fields = new ArrayList<AuditableField>();
		fields.add(new AuditableField("favorites.value","Favorites"));
		return fields;
	}

	/**
	 * Getter method for favorites.
	 *
	 * @return the favorites
	 */
	public final List<SystemCodes> getFavorites() {
		return favorites;
	}
	/**
	 * Setter method for favorites.
	 *
	 * @param favorites the favorites to set
	 */
	public final void setFavorites(List<SystemCodes> favorites) {
		this.favorites = favorites;
	}
	/**
	 * Getter method for alias.
	 *
	 * @return the alias
	 */
	public final List<String> getAlias() {
		return alias;
	}
	/**
	 * Setter method for alias.
	 *
	 * @param alias the alias to set
	 */
	public final void setAlias(List<String> alias) {
		this.alias = alias;
	}
}
