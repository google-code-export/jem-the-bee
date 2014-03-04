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
package org.pepstock.jem.gwt.client.panels.jobs.output;

import java.util.Date;

import org.pepstock.jem.Jcl;
import org.pepstock.jem.Job;
import org.pepstock.jem.gwt.client.commons.AbstractTable;
import org.pepstock.jem.gwt.client.commons.AnchorTextColumn;
import org.pepstock.jem.gwt.client.commons.JemConstants;
import org.pepstock.jem.gwt.client.commons.IndexedColumnComparator;
import org.pepstock.jem.gwt.client.commons.TextFilterableHeader;
import org.pepstock.jem.util.filters.fields.JobFilterFields;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.SelectionModel;

/**
 * Creates all columns to show into table, defening teh sorter too.
 * 
 * @author Andrea "Stock" Stocchero
 *
 */
public class OutputTable extends AbstractTable<Job> {	
	
	/**
	 * Builds the output table
	 * @param filterableHeaders <code>true</code> if contains filterable headers
	 */
	public OutputTable(boolean filterableHeaders) {
		super(filterableHeaders);
	}
	
	/**
	 * Adds all columns to table, defining the sort columns too.
	 */
	@Override
	public IndexedColumnComparator<Job> initCellTable(CellTable<Job> table) {
		@SuppressWarnings("unchecked")
		final SelectionModel<Job> selectionModel = (SelectionModel<Job>) table.getSelectionModel();
		Column<Job, Boolean> checkColumn = new Column<Job, Boolean>(
				new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(Job job) {
				return selectionModel.isSelected(job);
			}
		};
		
		// Checkbox
		
		CheckboxCell headerCheckBox = new CheckboxCell(true, false);
		Header<Boolean> checkHeader = new Header<Boolean>(headerCheckBox) {
			// imposta lo stato dell'header!
			@Override
			public Boolean getValue() {
				// se e' vuoto, niente e' selezionato/selezionabile
				if (getTable().getVisibleItems().isEmpty()) {
					return false;
				}
				
				// altrimenti testo
				for (Job j : getTable().getVisibleItems()) {
					// se almeno un elemento non e' selezionato, l'header non deve essere selezionato
					if (!getTable().getSelectionModel().isSelected(j)) {
						return false;
					}
				}
				// altrimenti se arrivo qui, tutti gli elementi sono selezionati
				return true;
			}
		};
		
		// updater che seleziona o deseleziona tutti gli elementi visibili in base al "valore" dell'header
		checkHeader.setUpdater(new ValueUpdater<Boolean>() {
			@Override
			public void update(Boolean value) {
				for (Job j : getTable().getVisibleItems()) {
					getTable().getSelectionModel().setSelected(j, value);
				}
			}
		});
				
		table.setColumnWidth(checkColumn, 23, Unit.PX);

		// Name
	    // construct a column that uses anchorRenderer
	    AnchorTextColumn<Job> name = new AnchorTextColumn<Job>() {
			@Override
			public String getValue(Job job) {
				return job.getName();
			}

			@Override
			public void onClick(int index, Job job, String value) {
				getInspectListener().inspect(job);
			}
			
		};
		name.setSortable(true);
		
		TextColumn<Job> type = new TextColumn<Job>() {
			@Override
			public String getValue(Job job) {
				return job.getJcl().getType();
			}
		};
		type.setSortable(true);

		TextColumn<Job> userid = new TextColumn<Job>() {
			@Override
			public String getValue(Job job) {
				// if surrogates, use ONLY user in JCL definition
				return (job.isUserSurrogated()) ? job.getJcl().getUser() : job.getUser();
			}
		};
		userid.setSortable(true);

		TextColumn<Job> environment = new TextColumn<Job>() {
			@Override
			public String getValue(Job job) {
				Jcl jcl = job.getJcl();
				return jcl.getEnvironment();
			}
		};
		environment.setSortable(true);
		
		TextColumn<Job> routed = new TextColumn<Job>() {
			@Override
			public String getValue(Job job) {
				Date routedTime = job.getRoutingInfo().getRoutedTime();
				return routedTime != null ? JemConstants.YES : "";
			}
		};
		routed.setSortable(true);
		
		TextColumn<Job> domain = new TextColumn<Job>() {
			@Override
			public String getValue(Job job) {
				Jcl jcl = job.getJcl();
				return jcl.getDomain();
			}
		};
		domain.setSortable(true);
		
		TextColumn<Job> affinity = new TextColumn<Job>() {
			@Override
			public String getValue(Job job) {
				Jcl jcl = job.getJcl();
				return jcl.getAffinity();
			}
		};
		affinity.setSortable(true);

		TextColumn<Job> endedDate = new TextColumn<Job>() {
			@Override
			public String getValue(Job job) {
				return JemConstants.DATE_TIME_FULL.format(job.getEndedTime()); 
			}
		};
		endedDate.setSortable(true);

		TextColumn<Job> result = new TextColumn<Job>() {
			@Override
			public String getValue(Job job) {
				return String.valueOf(job.getResult().getReturnCode()); 
			}
		};
		result.setSortable(true);

		TextColumn<Job> hold = new TextColumn<Job>() {
			
			@Override
			public String getValue(Job job) {
				Jcl jcl = job.getJcl();
				return (jcl.isHold()) ? "hold" : "";
			}
		};
		
		TextColumn<Job> member = new TextColumn<Job>() {
			@Override
			public String getValue(Job job) {
				return job.getMemberLabel();
			}
		};
		member.setSortable(true);
		
		
		/*
		 * Add the headers, type based on filterableHeader value
		 */
		table.addColumn(checkColumn, checkHeader);
		if (hasFilterableHeaders()) {
			table.addColumn(name, new TextFilterableHeader("Name", JobFilterFields.NAME.getName()));
			table.addColumn(type, new TextFilterableHeader("Type", JobFilterFields.TYPE.getName()));
			table.addColumn(userid, new TextFilterableHeader("User", JobFilterFields.USER.getName()));	// warning
			table.addColumn(environment, new TextFilterableHeader("Environment", JobFilterFields.ENVIRONMENT.getName()));
			table.addColumn(routed, new TextFilterableHeader("Routed", JobFilterFields.ROUTED.getName(), JobFilterFields.ROUTED.getPattern()));
			table.addColumn(domain, new TextFilterableHeader("Domain", JobFilterFields.DOMAIN.getName()));
			table.addColumn(affinity, new TextFilterableHeader("Affinity", JobFilterFields.AFFINITY.getName()));
			table.addColumn(endedDate, new TextFilterableHeader("Ended time", JobFilterFields.ENDED_TIME.getName(), JobFilterFields.ENDED_TIME.getPattern()));
			table.addColumn(result, new TextFilterableHeader("Return code", JobFilterFields.RETURN_CODE.getName()));
			table.addColumn(hold, "Hold");
			table.addColumn(member, new TextFilterableHeader("Member", JobFilterFields.MEMBER.getName()));
		} else {
			table.addColumn(name, "Name");
			table.addColumn(type, "Type");
			table.addColumn(userid, "User");
			table.addColumn(environment, "Environment");
			table.addColumn(routed, "Routed");
			table.addColumn(domain, "Domain");
			table.addColumn(affinity, "Affinity");
			table.addColumn(endedDate, "Ended time");
			table.addColumn(result, "Return code");
			table.addColumn(hold, "Hold");
			table.addColumn(member, "Member");
		}
        return new OutputJobComparator(1);
	}
}