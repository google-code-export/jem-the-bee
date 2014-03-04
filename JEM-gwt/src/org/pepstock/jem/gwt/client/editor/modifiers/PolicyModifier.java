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
package org.pepstock.jem.gwt.client.editor.modifiers;

import org.pepstock.jem.gwt.client.editor.Editor;
import org.pepstock.jem.gwt.client.editor.viewers.PolicyViewer;


/**
 * Component that edits policy, using ACE editor
 * 
 * @author Andrea "Stock" Stocchero
 * @version 1.4	
 *
 */
public class PolicyModifier extends PolicyViewer{
	
	/**
	 *  Constructs syntax highlighter with a new element ID
	 */
    public PolicyModifier() {
	    super();
    }

	/**
	 * Constructs syntax highlighter with a specific element ID 
	 * @param id ID for editor ELEMENT
	 */
    public PolicyModifier(String id) {
	    super(id);
    }

	/* (non-Javadoc)
	 * @see org.pepstock.jem.gwt.client.editor.AbstractSyntaxHighlighter#setEditorAttributes(org.pepstock.jem.gwt.client.editor.Editor)
	 */
    @Override
    public void setEditorAttributes(Editor editor) {
		editor.setMode(getLanguage());
		editor.setReadOnly(false);
		editor.setHighlightActiveLine(true);
    }
}