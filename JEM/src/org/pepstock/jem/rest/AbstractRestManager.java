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
package org.pepstock.jem.rest;

/**
 * Abstract REST manager, which contains th REST client to use on REST calls.
 *  
 * @author Andrea "Stock" Stocchero
 * @version 1.4
 */
public abstract class AbstractRestManager {
	
	private RestClient client = null; 

	/**
 	 * Creates a new REST manager using a RestClient
	 * @param restClient REST client instance
	 */
	public AbstractRestManager(RestClient restClient) {
		this.client = restClient;
	}

	/**
	 * @return the client
	 */
	public RestClient getClient() {
		return client;
	}


}
