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
package org.pepstock.jem.junit.submitter;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author Simone "Busy" Businaro
 *
 */
public class SubmitterConfiguration {

	private List<Submitter> submitters;

	/**
	 * @return the submitters
	 */
	public List<Submitter> getSubmitters() {
		return submitters;
	}

	/**
	 * @param submitters
	 *            the submitters to set
	 */
	public void setSubmitters(List<Submitter> submitters) {
		this.submitters = submitters;
	}

	/**
	 * 
	 * @param xml
	 *            the xml multicast message
	 * @return the NodeMulticastResponce unmarshall from the xml representation
	 * @throws Exception
	 *             if any exception occurs during the unmarshall process
	 */
	public static SubmitterConfiguration unmarshall(String xml)
			throws Exception {
		XStream xStream = new XStream();
		xStream.alias("submitter-configuration", SubmitterConfiguration.class);
		xStream.alias("submitter", Submitter.class);
		xStream.alias("param", Param.class);
		Object object = xStream.fromXML(xml);
		if (!(object instanceof SubmitterConfiguration)) {
			throw new Exception("Error unmarshall");
		}
		return (SubmitterConfiguration) object;
	}

	/**
	 * 
	 * @param object
	 * @return the xml marshall from the ClientMulticastRequest
	 */
	public static String marshall(SubmitterConfiguration object) {
		XStream xStream = new XStream();
		xStream.alias("submitter-configuration", SubmitterConfiguration.class);
		xStream.alias("submitter", Submitter.class);
		xStream.alias("param", Param.class);
		return xStream.toXML(object);
	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String xml = FileUtils
				.readFileToString(new File(
						"C:/workspace/Test/src/org/pepstock/junit/submitter/SubmitterConfiguration.xml"));
		SubmitterConfiguration.unmarshall(xml);
	}
}
