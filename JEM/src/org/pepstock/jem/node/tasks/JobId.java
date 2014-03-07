/**
    JEM, the BEE - Job Entry Manager, the Batch Execution Environment
    Copyright (C) 2012-2014   Andrea "Stock" Stocchero
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
package org.pepstock.jem.node.tasks;

import org.pepstock.jem.node.configuration.ConfigKeys;

/**
 * Class which returns the JOB ID inside of job execution, reading the system properties
 * 
 * @author Andrea "Stock" Stocchero
 * @version 1.4
 */
public class JobId {

	/**
	 * JOB id is read from system properties
	 */
	public static final String VALUE = System.getProperty(ConfigKeys.JEM_JOB_ID);
	
	/**
	 * Empty and private constructor. None must instantiate this object
	 */
	private JobId() {
	}

}
