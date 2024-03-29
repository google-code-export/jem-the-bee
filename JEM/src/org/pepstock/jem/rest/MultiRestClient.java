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

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.client.apache4.ApacheHttpClient4;

/**
 * Client to access to JEM by REST protocol. To use if you want to have a multi threading client (and HTTP connectors), one for 
 * different thread.
 *  
 * @author Andrea "Stock" Stocchero
 * @version 2.0
 *
 */
public class MultiRestClient extends RestClient{

	private ThreadLocal<ApacheHttpClient4> httpClientLocal = new ThreadLocal<ApacheHttpClient4>(){
		protected ApacheHttpClient4 initialValue() {
			return initialHttpClient();
		}
	};
	
	/**
	 * Creates the object using the base URL of rest
	 * @param uriString URL to access to JEM by HTTP
	 * @throws Exception if any SSL errors occurs
	 */
	public MultiRestClient(String uriString){
		super(uriString);
	}
	
	/* (non-Javadoc)
	 * @see org.pepstock.jem.util.RestClient#getBaseWebResource()
	 */
	@Override
	public WebResource getBaseWebResource(){
		return httpClientLocal.get().resource(getBaseURI());
	}
}