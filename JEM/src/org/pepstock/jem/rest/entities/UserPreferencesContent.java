/**
    JEM, the BEE - Job Entry Manager, the Batch Execution Environment
    Copyright (C) 2012-2015   Andrea "Stock" Stocchero
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.pepstock.jem.rest.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.pepstock.jem.node.security.UserPreference;
import org.pepstock.jem.rest.maps.UserPreferencesMapAdapter;


/**
 * Entity class which represents the current logged user. It contains all authorizations
 * to use inside the application. This object is created server side.
 * 
 * @author Andrea "Stock" Stocchero
 * @version 2.2
 *
 */
// uses Accessory Type to avoid to have REST error serializing upser preferences
@XmlAccessorType(XmlAccessType.FIELD) 
@XmlRootElement
public class UserPreferencesContent implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// PAY ATTENTION: HashMap are not supported by REST. For this reason there is a specific adapter
	@XmlJavaTypeAdapter(UserPreferencesMapAdapter.class)
	private Map<String, UserPreference> preferences = new HashMap<String, UserPreference>();
	/**
	 * Constructs a empty object
	 */
	public UserPreferencesContent() {
		super();
	}
	
	/**
	 * @return the preferences
	 */
	public Map<String, UserPreference> getPreferences() {
		return preferences;
	}

	/**
	 * @param preferences the preferences to set
	 */
	public void setPreferences(Map<String, UserPreference> preferences) {
		this.preferences = preferences;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserPreferencesContent [preferences=" + preferences + "]";
	}
}