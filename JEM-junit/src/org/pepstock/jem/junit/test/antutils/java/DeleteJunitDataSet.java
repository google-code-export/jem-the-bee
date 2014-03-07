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
package org.pepstock.jem.junit.test.antutils.java;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.pepstock.jem.node.configuration.ConfigKeys;

/**
 * This class will delete all the dataset create during the junit test relative
 * to antutils.
 * 
 * @author Simone "busy" Businaro
 * 
 */
public class DeleteJunitDataSet {

	/**
	 * name of the dataset that will contain the JDBC resource information
	 */
	public static final String DATA_SET_JUNIT_FOLDER = "/test_antutils";

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String dataPath = System.getProperty(ConfigKeys.JEM_DATA_PATH_NAME);
		String folderToDelete = dataPath + DATA_SET_JUNIT_FOLDER;
		File dirToDelete = new File(folderToDelete);
		System.out.println("Deliting folder:" + folderToDelete);
		if (dirToDelete.exists()) {
			FileUtils.deleteDirectory(dirToDelete);
			System.out.println("folder:" + folderToDelete + " deleted");
		} else {
			System.out.println("folder:" + folderToDelete + " does not exists");
		}
	}
}
