/**
    JEM, the BEE - Job Entry Manager, the Batch Execution Environment
    Copyright (C) 2012-2015   Simone "Busy" Businaro
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

import org.pepstock.jem.PreJob;

/**
 * Manages all SQL statements towards the database to persist the PreJob in CHECKING queues.<br>
 * 
 * @author Simone "Busy" Businaro
 * 
 */
public class PreJobDBManager extends AbstractDBManager<Long, PreJob>{ 

	private static final PreJobDBManager INSTANCE = new PreJobDBManager();
	
	/**
	 * Calls super class to create the connection
	 * 
	 * @throws Exception occurs if an error
	 */
	private PreJobDBManager(){
	}

	/**
	 * Is a static method (typical of a singleton) that returns the unique
	 * instance of JobDBManager.<br>
	 * You must ONLY one instance of this per JVM instance.<br>
	 * 
	 * @return manager instance
	 * @throws Exception
	 */
	public static synchronized PreJobDBManager getInstance() {
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
	public Long getKey(PreJob item) {
		return null;
	}
	

}