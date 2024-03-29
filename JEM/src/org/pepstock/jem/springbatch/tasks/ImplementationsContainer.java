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
package org.pepstock.jem.springbatch.tasks;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pepstock.catalog.DataDescriptionImpl;

/**
 * Is singleton object. Only one instance of this container must instantiated.<br>
 * Contains all data description defined with the goal to implement the dataset
 * referback feature.
 * 
 * @author Andrea "Stock" Stocchero
 * @version 1.4
 *
 */
public class ImplementationsContainer {


	// format to check the reference: step, task and data description
	private static final MessageFormat MESSAGE_FORMAT = new MessageFormat("*.{0}.{1}");

	// format used for searching by target, task  and id
	private static final MessageFormat MESSAGE_FORMAT_SEARCH = new MessageFormat("*.{0}.");

	
	private static ImplementationsContainer INSTANCE = null;
	
	private Map<String, DataDescriptionImpl> mapDataDescription = new HashMap<String, DataDescriptionImpl>();

	/**
	 * Singleton, empty constructor
	 */
	private ImplementationsContainer() {
	}
	
	/**
	 * @return singleton instance
	 */
	public static synchronized ImplementationsContainer getInstance(){
		// if first time,
		// creates a new instance
		if  (INSTANCE == null){
			INSTANCE = new ImplementationsContainer();
		}
		return INSTANCE;
	}

	/**
	 * Returns <code>true</code> if container contains the reference, otherwise
	 * <code>false</code>.
	 * 
	 * @see org.pepstock.jem.springbatch.tasks.DataSet#isReference()
	 * @param reference string reference representation
	 * @return <code>true</code> if container contains the reference
	 */
	boolean hasDataDescription(String reference) {
		return (reference != null) ? mapDataDescription.containsKey(reference.toLowerCase()) : false;	
	}

	/**
	 * Returns data description found by reference, otherwise <code>null</code>.
	 * 
	 * @param reference reference string representation
	 * @return data description instance
	 */
	DataDescriptionImpl getDataDescription(String reference) {
		return (reference != null) ? mapDataDescription.get(reference.toLowerCase()) : null;	
	}

	/**
	 * Returns the list of data description instances defined for passed Task.
	 * 
	 * @param step stepname
	 * @return list of data description instances
	 */
	List<DataDescriptionImpl> getDataDescriptionsByItem(String step) {
		// creates a new list
		List<DataDescriptionImpl> result = new ArrayList<DataDescriptionImpl>();
		// creates a key usinf format for searching (without data description
		// name)
		String keyPattern = createKey(step);
		// scans all keys of map
		for (String key : mapDataDescription.keySet()) {
			// when the key starts with key pattern, matches!!
			// So adds to list to return
			if (key.startsWith(keyPattern)) {
				result.add(mapDataDescription.get(key));
			}
		}
		return result;
	}

	/**
	 * Adds new data description implementation, defined for passed target and
	 * task.
	 * 
	 * @param step step name
	 * @param dd data description implementation
	 */
	void addDataDescription(String step, DataDescriptionImpl dd) {
		// create a key using message format defined for reference
		String key = createKey(step, dd.getName());
		mapDataDescription.put(key, dd);
	}
	/**
	 * Creates a key using the format defined.
	 * 
	 * @param step step name
	 * @param dd data description name
	 * @return the key of map (always lower-case)
	 */
	private String createKey(String step, String dd) {
		String key;
		key = MESSAGE_FORMAT.format(new Object[] { step, dd }, new StringBuffer(), null).toString();
		return key.toLowerCase();
	}

	/**
	 * Creates a key using the format defined for searching, without data
	 * description name.
	 * 
	 * @see ImplementationsContainer#MESSAGE_FORMAT
	 * @param target target name
	 * @param task task name
	 * @return the key of map (always lower-case)
	 */
	private String createKey(String step) {
		String key;
		key = MESSAGE_FORMAT_SEARCH.format(new Object[] { step }, new StringBuffer(), null).toString();
		return key.toLowerCase();
	}

	/**
	 * Returns the string representation of data description container (uses
	 * HaspMap to string method).
	 * 
	 * @see java.util.HashMap#toString()
	 * @return the string representation of data description container
	 */
	@Override
	public String toString() {
		return mapDataDescription.toString();
	}
}