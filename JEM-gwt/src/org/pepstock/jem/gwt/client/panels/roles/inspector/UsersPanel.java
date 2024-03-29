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
package org.pepstock.jem.gwt.client.panels.roles.inspector;

import org.pepstock.jem.gwt.client.ResizeCapable;
import org.pepstock.jem.gwt.client.Sizes;
import org.pepstock.jem.gwt.client.commons.CellTableStyle;
import org.pepstock.jem.gwt.client.commons.Images;
import org.pepstock.jem.gwt.client.commons.InspectListener;
import org.pepstock.jem.gwt.client.commons.Styles;
import org.pepstock.jem.gwt.client.panels.components.RemovePanel;
import org.pepstock.jem.gwt.client.panels.roles.inspector.commons.InputPanel;
import org.pepstock.jem.node.security.Role;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellList.Resources;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
/**
 * The Cell used to render a Permission.
 */

/**
 * Components to add and remove user names or pattern of name to a role. 
 * 
 * @author Andrea "Stock" Stocchero
 *
 */
public final class UsersPanel extends HorizontalPanel implements InspectListener<String>, ClickHandler, ResizeCapable {
	
	// common styles
	static {
		Styles.INSTANCE.common().ensureInjected();
		Styles.INSTANCE.administration().ensureInjected();
	}

	private Role role = null;

	private RemovePanel remove = new RemovePanel();
	
	private InputPanel inputPanel = null;
	
	private CellList<String> cellList = null;
	
	private ScrollPanel scroller = new ScrollPanel();

	private final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();

	/**
	 * Creates all UI usinf info of role passed by argument.
	 * 
	 * @param role role instance to change
	 */
	public UsersPanel(Role role) {
		this.role = role;
		cellList = new CellList<String>(new UserCell(Images.INSTANCE.user()), (Resources) GWT.create(CellTableStyle.class));

		this.inputPanel = new InputPanel("Users pattern");
		inputPanel.setListener(this);
		add(inputPanel);

		// Panel with permissions list
		// Create a cell to render each value.
		cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		// Add a selection model to handle user selection.
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				String selected = selectionModel.getSelectedObject();
				if (selected != null) {
					// when selects a item, remove button will be enabled
					remove.setEnabled(true);
				}
			}
		});
		// Push the data into the widget.
		cellList.setRowCount(role.getUsers().size(), true);
		cellList.setRowData(0, role.getUsers());
		scroller.setWidget(cellList);

		VerticalPanel scrollHolder = new VerticalPanel();
		scrollHolder.setSize(Sizes.HUNDRED_PERCENT, Sizes.HUNDRED_PERCENT);
		scrollHolder.addStyleName(Styles.INSTANCE.administration().nodeList());
		scrollHolder.add(scroller);

		VerticalPanel listContainer = new VerticalPanel();
		listContainer.setSpacing(5);

		Label label = new Label("Users");
		label.addStyleName(Styles.INSTANCE.common().bold());
		label.setHeight(Sizes.toString(InputPanel.LABEL_HEIGHT));
		listContainer.add(label);
		listContainer.add(scrollHolder);

		add(listContainer);

		remove.setClickHandler(this);
		add(remove);

	}
	
	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}
	
	/**
	 * Removes the selected string from container.
	 * 
	 * @param selected item selected of user name or pattern
	 */
	public void remove(String selected){
		if (selected != null){
			// removes the selected item and sets the new row data
			role.getUsers().remove(selected);
			cellList.setRowCount(role.getUsers().size());
			cellList.setRowData(0, role.getUsers());
		}
		// disable always the remove button post press of it
	    remove.setEnabled(false);
	}

	/**
	 * Adds a new item into the list
	 * @param selected new user name or pattern to add
	 */
	public void add(String selected){
		// if name or pattern not in the list, adds
		if (!role.getUsers().contains(selected)){
			// adds to collection inside the role object
			role.getUsers().add(selected);
			// sets row data 
	    	cellList.setRowCount(role.getUsers().size());
	    	cellList.setRowData(0, role.getUsers());
	    	
	    	// clears the text box for new entry
	    	inputPanel.clear();
	    	// if is selected (strange behaviour of celllist)
	    	// sets enable the remove button
	    	if (selectionModel.isSelected(selected)){
	    		remove.setEnabled(true);
	    	}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.pepstock.jem.gwt.client.ResizeCapable#onResize(int, int)
	 */
	@Override
	public void onResize(int availableWidth, int availableHeight) {
		int widthInputPanel = InputPanel.SPACING + InputPanel.WIDTH + InputPanel.SPACING;

		int widthRemovePanel = InputPanel.SPACING + RemovePanel.WIDTH + InputPanel.SPACING;

		int width = availableWidth - widthInputPanel - widthRemovePanel - InputPanel.SPACING - InputPanel.SPACING
				- Sizes.MAIN_TAB_PANEL_BORDER - Sizes.MAIN_TAB_PANEL_BORDER;

		int height = availableHeight - InputPanel.SPACING - InputPanel.SPACING - InputPanel.SPACING -
				InputPanel.LABEL_HEIGHT - Sizes.MAIN_TAB_PANEL_BORDER - Sizes.MAIN_TAB_PANEL_BORDER;

		scroller.setHeight(Sizes.toString(height));
		scroller.setWidth(Sizes.toString(width));
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		String selected = selectionModel.getSelectedObject();
		remove(selected);
	}
	
	/* (non-Javadoc)
	 * @see org.pepstock.jem.gwt.client.commons.InspectListener#inspect(java.lang.Object)
	 */
	@Override
	public void inspect(String object) {
		add(object);
	}
}