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
package org.pepstock.jem.ant.tasks.utilities.scripts;


/**
 * Shell script task, which uses PERL shell to execute the content of ANT task element
 * 
 * @author Andrea "Stock" Stocchero
 * @version 1.0	
 *
 */
public class PerlScriptTask extends ScriptLanguageTask {
	
	private static final String SHELL = "perl";
	
	private static final String SUFFIX = ".pl";

	/* (non-Javadoc)
	 * @see org.pepstock.jem.ant.tasks.utilities.scripts.ScriptLanguageTask#getShell()
	 */
	@Override
	public String getShell() {
		return SHELL;
	}

	/* (non-Javadoc)
	 * @see org.pepstock.jem.ant.tasks.utilities.scripts.ScriptLanguageTask#getLanguageSuffix()
	 */
	@Override
	public String getLanguageSuffix() {
		return SUFFIX;
	}
	
}