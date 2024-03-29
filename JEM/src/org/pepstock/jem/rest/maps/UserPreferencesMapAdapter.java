/**
    JEM, the BEE - Job Entry Manager, the Batch Execution Environment
    Copyright (C) 2012-2015  Marco "Fuzzo" Cuccato
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
package org.pepstock.jem.rest.maps;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.pepstock.jem.log.JemException;
import org.pepstock.jem.node.security.UserPreference;

import com.thoughtworks.xstream.XStream;

/**
 * Adapter for a Map object. Map object is not supported by REST for this reason an adapter is needed.
 * 
 * @see UserPreference
 * @author Marco "Fuzzo" Cuccato
 * @version 1.4
 *
 */
public final class UserPreferencesMapAdapter extends XmlAdapter<MapType, Map<String, UserPreference>> {

	private XStream stream = new XStream();

	/* (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public MapType marshal(Map<String, UserPreference> pref) throws JemException {
		// to serialize, uses XStream
		MapType myMapType = new MapType();
		// scans all user preferences 
		for (Entry<String, UserPreference> entry : pref.entrySet()) {
			MapEntryType myMapEntryType = new MapEntryType();
			// uses the key of user preferences as key of the map
			myMapEntryType.key = entry.getKey();
			// serializes the object UserPreference
			// in XML format
			String value = stream.toXML(entry.getValue());
			// sets maptype
			myMapEntryType.value = value;
			// adds maptype to map
			myMapType.getEntry().add(myMapEntryType);
		}
		return myMapType;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 */
	@Override
	public Map<String, UserPreference> unmarshal(MapType type) throws JemException {
		// to deserialize, uses XStream
		Map<String, UserPreference> hashMap = new HashMap<String, UserPreference>();
		// scan all maptype entries
		for (MapEntryType myEntryType : type.getEntry()) {
			// deserializes from XML to the object
			UserPreference up = (UserPreference)stream.fromXML(myEntryType.value);
			// adds to the map
			hashMap.put(myEntryType.key, up);
		}
		return hashMap;
	}
}