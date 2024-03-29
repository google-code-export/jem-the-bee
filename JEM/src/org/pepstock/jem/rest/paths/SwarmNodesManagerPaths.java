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
package org.pepstock.jem.rest.paths;

/**
 * Contains all labels for swarm service to use to create REST URL.
 * 
 * @author Andrea "Stock" Stocchero
 * @version 2.2
 */
public final class SwarmNodesManagerPaths {
	
	/**
	 * Key to define the path to bind this services
	 */
	public static final String MAIN = CommonPaths.QUERYSTRING_SEPARATOR +  "swarmNodes";

	/**
	 * Key to define the path to bind get nodes list method
	 */
	public static final String LIST = CommonPaths.QUERYSTRING_SEPARATOR +  "list";

	/**
	 * Key to define the path to bind get nodes list method by filter
	 */
	public static final String LIST_BY_FILTER = CommonPaths.QUERYSTRING_SEPARATOR +  "listByFilter";
	
	/**
	 * Key to define the path to bind start method
	 */
	public static final String START = CommonPaths.QUERYSTRING_SEPARATOR +  "start";
	
	/**
	 * Key to define the path to bind drain method
	 */
	public static final String DRAIN = CommonPaths.QUERYSTRING_SEPARATOR +  "drain";
	
	/**
	 * Key to define the path to bind get status method
	 */
	public static final String STATUS = CommonPaths.QUERYSTRING_SEPARATOR +  "status";
	
	/**
	 * Key to define the path to bind get config method
	 */
	public static final String GET_CONFIG = CommonPaths.QUERYSTRING_SEPARATOR +  "getConfig";
	
	/**
	 * Key to define the path to bind update config method
	 */
	public static final String UPDATE_CONFIG = CommonPaths.QUERYSTRING_SEPARATOR +  "updateConfig";

	/**
	 * To avoid any instantiation
	 */
	private SwarmNodesManagerPaths() {
		
	}

}
