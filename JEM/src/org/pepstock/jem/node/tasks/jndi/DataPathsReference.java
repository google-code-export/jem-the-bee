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
package org.pepstock.jem.node.tasks.jndi;

import javax.naming.Reference;

/**
 * Sets constants for JNDI. Both class name and factory name.
 * 
 * @author Andrea "Stock" Stocchero
 * 
 */
public class DataPathsReference extends Reference {

	private static final long serialVersionUID = 1L;

	private static final String FACTORY = DataPathsFactory.class.getName();

	private static final String CLASSNAME = Object.class.getName();

	/**
	 * Empty constructor called by JNDI engine
	 */
	public DataPathsReference() {
		super(CLASSNAME, FACTORY, null);
	}
}