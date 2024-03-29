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
package org.pepstock.jem.node.resources.impl.jem;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.xml.bind.JAXBContext;

import org.pepstock.jem.log.JemException;
import org.pepstock.jem.log.LogAppl;
import org.pepstock.jem.node.NodeMessage;
import org.pepstock.jem.node.resources.impl.AbstractObjectFactory;
import org.pepstock.jem.node.resources.impl.CommonKeys;
import org.pepstock.jem.node.security.LoggedUser;
import org.pepstock.jem.node.tasks.jndi.JNDIException;
import org.pepstock.jem.rest.ResourceRestClient;
import org.pepstock.jem.rest.entities.Account;
import org.pepstock.jem.rest.services.LoginManager;

/**
 * JNDI factory to create object for JAVA batches.
 * <br>
 * It returns a RestClient.
 * 
 * @author Andrea "Stock" Stocchero
 * @version 2.2
 * 
 */
public class JemFactory  extends AbstractObjectFactory {

	/* (non-Javadoc)
	 * @see javax.naming.spi.ObjectFactory#getObjectInstance(java.lang.Object, javax.naming.Name, javax.naming.Context, java.util.Hashtable)
	 */
	@Override
	public Object getObjectInstance(Object object, Name name, Context ctx, Hashtable<?, ?> env) throws JNDIException {
		// checks if the instacen is null or not a reference
		if ((object == null) || !(object instanceof Reference)) {
			return null;
		}
		// creates a jem rest client
		return createRestClient(loadProperties(object, JemResourceKeys.PROPERTIES_ALL));
	}

	/**
	 * Creates and configures a RestClient instance based on the
	 * given properties.
	 * 
	 * @param properties the REST client configuration properties
	 * @return a RestClient instance to access to JEM
	 * @throws JNDIException if an error occurs creating the rest client
	 */
	private Object createRestClient(Properties properties) throws JNDIException {
		// gets URL
		// is mandatory
		String urlString = properties.getProperty(CommonKeys.URL);
		if (urlString == null){
			throw new JNDIException(NodeMessage.JEMC136E, CommonKeys.URL);
		}
		// gets USER ID
		// is mandatory
		String username = properties.getProperty(CommonKeys.USERID);
		if (username == null){
			throw new JNDIException(NodeMessage.JEMC136E, CommonKeys.USERID);
		}
		// gets PASSWORD
		// is mandatory
		String password = properties.getProperty(CommonKeys.PASSWORD);
		if (password == null){
			throw new JNDIException(NodeMessage.JEMC136E, CommonKeys.PASSWORD);
		}

		// sets JAXB version 2, otherwise can't find the class
		System.setProperty(JAXBContext.JAXB_CONTEXT_FACTORY, "com.sun.xml.bind.v2.ContextFactory");
		
		// creates a rest client, extend ResourceClient
		DelegateRestClient client = new DelegateRestClient(urlString);
		// creates a login manager
		LoginManager manager = new LoginManager(client);
		client.setManager(manager);
		try {
			// checks if is logged
			LoggedUser user = manager.getUser();
			// if not
			if (user == null) {
				// creates a Account object
				Account account = new Account();
				account.setUserId(username);
				account.setPassword(password);
				// log in
				manager.login(account);
			}
		} catch (JemException e) {
			throw new JNDIException(NodeMessage.JEMC269E, e);
		} 
		// returns the client
		return client;
	}
	
	/**
	 * Delegate REST client, with close connection
	 * @author Andrea "Stock" Stocchero
	 * @version 2.2
	 */
	static final class DelegateRestClient extends ResourceRestClient{

		private LoginManager manager = null;
		
		/**
		 * Creates the Rest client calling super class
		 * @param uriString uri of REST instance
		 */
		public DelegateRestClient(String uriString) {
			super(uriString);
		}

		/**
		 * @return the manager
		 */
		LoginManager getManager() {
			return manager;
		}

		/**
		 * @param manager the manager to set
		 */
		void setManager(LoginManager manager) {
			this.manager = manager;
		}

		/* (non-Javadoc)
		 * @see java.io.Closeable#close()
		 */
		@Override
		public void close() throws IOException {
			// if manager not null
			if (manager != null){
				try {
					// performs logoff
					manager.logoff();
				} catch (JemException e) {
					LogAppl.getInstance().ignore(e.getMessage(), e);
				}
			}
		}
	}
}