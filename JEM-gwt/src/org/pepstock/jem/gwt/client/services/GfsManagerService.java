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
package org.pepstock.jem.gwt.client.services;

import java.util.Collection;

import org.pepstock.jem.GfsFile;
import org.pepstock.jem.log.JemException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author Andrea "Stock" Stocchero
 * 
 */
@RemoteServiceRelativePath(Services.GFS)
public interface GfsManagerService extends RemoteService {

	/**
	 * @return
	 * @throws JemException
	 */
	Collection<GfsFile> getFilesList(int type, String path) throws JemException;

	/**
	 * 
	 * @param type
	 * @param file
	 * @return
	 * @throws JemException
	 */
	String getFile(int type, String file) throws JemException;

}