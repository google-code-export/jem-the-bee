/**
    JEM, the BEE - Job Entry Manager, the Batch Execution Environment
    Copyright (C) 2012, 2013   Andrea "Stock" Stocchero
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
package org.pepstock.jem.node.persistence;

import org.pepstock.jem.node.configuration.SwarmConfiguration;

/**
 * Manages all SQL statements towards the database to persist the routingConfs.<br>
 * 
 * @author Andrea "Stock" Stocchero
 * @version 1.0	
 *
 */
public class RoutingConfigDBManager extends AbstractDBManager<String, SwarmConfiguration>{

	private static final RoutingConfigDBManager INSTANCE = new RoutingConfigDBManager();

	/**
	 * 
	 */
	private RoutingConfigDBManager(){
	}

	/**
	 * Is a static method (typical of a singleton) that returns the unique
	 * instance of JobDBManager.<br>
	 * You must ONLY one instance of this per JVM instance.<br>
	 * 
	 * @return manager instance
	 * @throws Exception
	 */
	public static synchronized RoutingConfigDBManager getInstance(){
		return INSTANCE;
	}

	/**
	 * @return <code>true</code> is is instanciated, otherwise <code>false</code>.
	 */
	public static boolean isInstanciated(){
		return INSTANCE != null;
	}

	/* (non-Javadoc)
	 * @see org.pepstock.jem.node.persistence.AbstractDBManager#getKey(java.lang.Object)
	 */
	@Override
	public String getKey(SwarmConfiguration item) {
		return SwarmConfiguration.DEFAULT_NAME;
	}
}