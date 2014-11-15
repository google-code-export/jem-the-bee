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
package org.pepstock.jem.util.rmi;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;
import java.util.Enumeration;

import org.pepstock.jem.log.LogAppl;
import org.pepstock.jem.util.UtilMessage;

/**
 * Custom RMI socket factory to check if the job is aithorized to connected directly the node by RMI protocol.
 * @author Andrea "Stock" Stocchero
 * @version 2.0
 */
public class NodeRmiSocketFactory extends RMISocketFactory {

	/* (non-Javadoc)
	 * @see java.rmi.server.RMISocketFactory#createSocket(java.lang.String, int)
	 */
	@Override
	public Socket createSocket(String host, int port) throws IOException {
		return new Socket(host, port);
	}

	/* (non-Javadoc)
	 * @see java.rmi.server.RMISocketFactory#createServerSocket(int)
	 */
	@Override
	public ServerSocket createServerSocket(int port) throws IOException {
		return new ServerSocket(port){
			@Override
			public Socket accept() throws IOException{
				Socket socket = super.accept();
				String resolved = socket.getInetAddress().getHostAddress();
				if (!isLocalHostAddress(resolved)){
					socket.shutdownInput();
					socket.shutdownOutput();
					LogAppl.getInstance().emit(UtilMessage.JEMB007E, resolved);
				}
				return socket;
			}
		};
	}
	
	/**
	 * Scans all interfaces to check ip address
	 * @param resolved host address of RMI request
	 * @return <code>true</code> if is a local host.
	 */
	private boolean isLocalHostAddress(String resolved){
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface ni = interfaces.nextElement();
				Enumeration<InetAddress> addresses = ni.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress addr = addresses.nextElement();
					String localhost = addr.getHostAddress();
					if (resolved.equalsIgnoreCase(localhost)){
						return true;
					}
				}
			}
		} catch (Exception e) {
			// debug
			LogAppl.getInstance().debug(e.getMessage(), e);
		}
		return false;
	}
}
