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
package org.pepstock.jem.node.resources;

import org.pepstock.jem.node.configuration.ConfigKeys;

import com.thoughtworks.xstream.XStream;

/**
 * XML utility to have a XStream object already configured for common resources.
 * <br>
 * With XStream, we can serialize and deserialize the resources correctly
 * with a common configuration.
 * 
 * @author Andrea "Stock" Stocchero
 * @version 1.4	
 *
 */
public final class XmlUtil {
	
	private static XStream xs = null;
	
	/**
	 * To avoid any instantiation
	 */
	private XmlUtil() {
	}

	/**
	 * @return xstream to serialize and deserialize resources
	 */
	public static synchronized XStream getXStream(){
		// checks if XStream is instantiated
		if (xs == null){
			// if not, creates a new instance
			xs = new XStream();
			// creates alias for resources
			xs.alias(ConfigKeys.RESOURCES_ALIAS, Resources.class);
			xs.addImplicitCollection(Resources.class, ConfigKeys.RESOURCES_ALIAS);
			// creates alias for resource
			xs.alias(ConfigKeys.RESOURCE_ALIAS, Resource.class);
			// defines all attributes of resource
			xs.aliasAttribute(Resource.class, ConfigKeys.NAME_FIELD, ConfigKeys.NAME_ATTRIBUTE_ALIAS);
			xs.aliasAttribute(Resource.class, ConfigKeys.TYPE_FIELD, ConfigKeys.TYPE_ATTRIBUTE_ALIAS);
			xs.aliasAttribute(Resource.class, ConfigKeys.USER_FIELD, ConfigKeys.USER_ATTRIBUTE_ALIAS);
			xs.aliasAttribute(Resource.class, ConfigKeys.LAST_MODIFIED_FIELD, ConfigKeys.LAST_MODIFIED_ATTRIBUTE_ALIAS);
			// applies the implicit collections of properties of a resource
			xs.addImplicitMap(Resource.class,  ConfigKeys.PROPERTIES_FIELD, ResourceProperty.class, ConfigKeys.NAME_FIELD);
			xs.alias(ConfigKeys.PROPERTY_ATTRIBUTE_ALIAS, ResourceProperty.class);	
			// adds generic custom properties
			// using a converter so the collection can use <property name="">value</property>
			xs.registerLocalConverter(Resource.class, ConfigKeys.RESOURCE_CUSTOM_PROPERTIES_FIELD, new ResourceCustomPropertyConverter());
			xs.registerConverter(new ResourcePropertyConverter());
		}
		return xs;
	}
}