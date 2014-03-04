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
package org.pepstock.jem.gwt.client.panels;

import java.util.Collection;

import org.pepstock.jem.gwt.client.commons.InspectListener;
import org.pepstock.jem.gwt.client.commons.Loading;
import org.pepstock.jem.gwt.client.commons.SearchListener;
import org.pepstock.jem.gwt.client.commons.ServiceAsyncCallback;
import org.pepstock.jem.gwt.client.commons.Toast;
import org.pepstock.jem.gwt.client.events.EventBus;
import org.pepstock.jem.gwt.client.events.FilterEvent;
import org.pepstock.jem.gwt.client.events.FilterEventHandler;
import org.pepstock.jem.gwt.client.panels.components.BasePanel;
import org.pepstock.jem.gwt.client.panels.components.CommandPanel;
import org.pepstock.jem.gwt.client.panels.components.TableContainer;
import org.pepstock.jem.gwt.client.panels.resources.AbstractResourceInspector;
import org.pepstock.jem.gwt.client.panels.resources.ExistingResourceInspector;
import org.pepstock.jem.gwt.client.panels.resources.ResourcesActions;
import org.pepstock.jem.gwt.client.panels.resources.ResourcesSearcher;
import org.pepstock.jem.gwt.client.panels.resources.ResourcesTable;
import org.pepstock.jem.gwt.client.security.ClientPermissions;
import org.pepstock.jem.gwt.client.services.Services;
import org.pepstock.jem.log.MessageLevel;
import org.pepstock.jem.node.resources.Resource;
import org.pepstock.jem.node.security.Permissions;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * Main panel of common resources manager. Shows the list of common resources defined with the possibilities to act on them. 
 * Furthermore allows to inspect the common resource to change it.
 * 
 * @author Andrea "Stock" Stocchero
 *
 */
public class CommonResources extends BasePanel<Resource> implements SearchListener, InspectListener<Resource> {
	
	/**
	 * Constructs all UI 
	 */
	public CommonResources() {
		super(new TableContainer<Resource>(new ResourcesTable()),
				new CommandPanel<Resource>(new ResourcesSearcher(), new ResourcesActions()));
		
		// sets itself as listener
		getCommandPanel().getSearcher().setSearchListener(this);
		getCommandPanel().getActions().setUnderlyingTable(getTableContainer().getUnderlyingTable());
		getTableContainer().getUnderlyingTable().setInspectListener(this);

		// subscribe the basic filter event handler to eventbus
		EventBus.INSTANCE.addHandler(FilterEvent.TYPE, (FilterEventHandler)getCommandPanel().getSearcher());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.client.main.JobsSearchListener#search(java.lang.String)
	 */
	@Override
	public void search(final String filter) {
		if (ClientPermissions.isAuthorized(Permissions.RESOURCES, Permissions.RESOURCES_READ)) {
			Loading.startProcessing();
		    Scheduler scheduler = Scheduler.get();
		    scheduler.scheduleDeferred(new ScheduledCommand() {
				
				@Override
				public void execute() {
					// asks for common resources
					Services.COMMON_RESOURCES_MANAGER.getCommonResources(filter, new GetCommonResourcesAsyncCallback());
				}
		    });

		}
	}

	private class GetCommonResourcesAsyncCallback extends ServiceAsyncCallback<Collection<Resource>> {
		@Override
		public void onJemFailure(Throwable caught) {
			new Toast(MessageLevel.ERROR, caught.getMessage(), "Search error!").show();
			getCommandPanel().getSearcher().setFirstSearch(true);
		}

		@Override
		public void onJemSuccess(Collection<Resource> result) {
			// sets data to table to show it
			getTableContainer().getUnderlyingTable().setRowData(result);
			getCommandPanel().getSearcher().setFirstSearch(false);
		}
		
		@Override
        public void onJemExecuted() {
			Loading.stopProcessing();
        }
	}
	
	/* (non-Javadoc)
	 * @see org.pepstock.jem.gwt.client.commons.RoleInspectListener#inspect(org.pepstock.jem.gwt.client.security.Role)
	 */
    @Override
    public void inspect(Resource resource) {
    	if (ClientPermissions.isAuthorized(Permissions.RESOURCES, Permissions.RESOURCES_UPDATE)) {
    		// goes inspect in teh common resource
    		AbstractResourceInspector inspector = new ExistingResourceInspector(resource);
    		inspector.setTitle(resource.getName());
    		inspector.center();

    		// adds itself listener for closing and refreshing the data
    		inspector.addCloseHandler(new CloseHandler<PopupPanel>() {

    			@Override
    			public void onClose(CloseEvent<PopupPanel> event) {
    				getCommandPanel().getSearcher().refresh();
    			}
    		});
    	}
    }

}